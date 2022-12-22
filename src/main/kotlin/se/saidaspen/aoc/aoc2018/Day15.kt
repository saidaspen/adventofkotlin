//package se.saidaspen.aoc.aoc2018
//
//import se.saidaspen.aoc.util.*
//import kotlin.math.sign
//
//fun main() = Day15.run()
//
//object Day15 : Day(2018, 15) {
//    enum class Type { G, E }
//    data class Combatant(val id: Char, val type: Type, var pos: Point, var hp: Int = 200)
//
//    override fun part1(): Any {
//        var input = "#######\n" +
//                "#.G...#\n" +
//                "#...EG#\n" +
//                "#.#.#G#\n" +
//                "#..G#E#\n" +
//                "#.....#\n" +
//                "#######"
//        val map = toMap(input)
//        var combatants = map.entries.filter { it.value == 'E' || it.value == 'G' }.withIndex().map {
//            val id = if (it.index < 10) (it.index + 48).toChar() else (55 + it.index).toChar()
//            Combatant(id, if (it.value.value == 'E') Type.E else Type.G, it.value.key)
//        }.toMutableList()
//        combatants.forEach { map[it.pos] = it.id }
//
//        fun typeOf(c: Char) = combatants.first { it.id == c }.type
//
//        var fullRounds = 0
//        while (combatants.map { it.type }.histo().size == 2) {
//            println("After round: " + fullRounds)
//            map.printArea()
//            println(combatants.map { it.id to it.hp })
//            println("")
//            combatants = combatants.sortedWith(compareBy<Combatant> { it.pos.second }.thenBy { it.pos.first }).toMutableList()
//            val killedThisRound = mutableListOf<Combatant>()
//            for (c in combatants) {
//                if (killedThisRound.contains(c)) continue
//                val neighborEnemies = c.pos.neighborsSimple()
//                    .filter { map.containsKey(it) && map[it] != '.' && map[it] != '#' }
//                    .map { npos -> map[npos]!! }
//                    .map { combatants.first { e -> e.id == it } }
//                    .filter { it.type != c.type }
//                    .filter { !killedThisRound.contains(it) }
//                if (neighborEnemies.isNotEmpty()) {
//                    val enemyToAttack = neighborEnemies.sortedWith(compareBy<Combatant> { it.pos.second }.thenBy { it.pos.first }).first()
//                    enemyToAttack.hp = enemyToAttack.hp - 3
//                    if (enemyToAttack.hp <= 0 ) {
//                        killedThisRound.add(enemyToAttack)
//                        map[enemyToAttack.pos] = '.'
//                    }
//                } else {
//                    val targets = combatants.filter { !killedThisRound.contains(it) }.filter { it.type != c.type }
//                    val inRange = targets.flatMap { neighborsSimple(it.pos) }.filter { map[it] == '.' }.toSet()
//                    val next: (Point) -> Iterable<Point> =
//                        { it.neighborsSimple().filter { n -> map.containsKey(n) && map[n] == '.' }.sortedWith(compareBy<Point> {p -> p.second }.thenBy {p-> p.first }) }
//                    val reachable = inRange.mapNotNull { destination ->
//                        bfsPath(
//                            c.pos,
//                            { it == destination },
//                            next
//                        )
//                    }.groupBy { it.length }.entries.so
//                        .sortedWith(compareBy<BfsResult<Point>> { it.length }.thenBy { it.end.second }.thenBy { it.end.first })
//                    if (reachable.isEmpty()) continue
//                    val chosen = reachable.first()
//                    val moveTo = chosen.path.dropLast(1).last()
//                    println("" + c.pos + " " + moveTo)
//                    // Need to change
//                    map[c.pos] = '.'
//                    c.pos = moveTo
//                    map[c.pos] = c.id
//                }
//            }
//            combatants.removeAll(killedThisRound)
//            killedThisRound.clear()
//            fullRounds += 1
//        }
//        return combatants.sumOf { it.hp } * fullRounds
//    }
//
//    override fun part2(): Any {
//        return ""
//    }
//
//}
