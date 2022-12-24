package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.*

fun main() = Day15.run()

object Day15 : Day(2018, 15) {
    enum class Type { G, E }
    data class Combatant(val id: Char, val type: Type, var pos: Point, var hp: Int = 200)

    override fun part1(): Any {
        val (combatants, fullrounds) = simulateFight(3)
        return combatants.sumOf { it.hp } * fullrounds
    }

    override fun part2(): Any {
        var strenght = 4
        val numElves = input.count { it == 'E' }
        while(true) {
            val (combatants, fullrounds) = simulateFight(strenght)
            val survivedElves = combatants.count { it.type == Type.E }
            if (survivedElves == numElves) {
                return combatants.sumOf { it.hp } * fullrounds
            }
            strenght++
        }
    }

    private fun simulateFight(elfStrength: Int): P<MutableList<Combatant>, Int> {
        val map = toMap(input)
        var combatants = map.entries.filter { it.value == 'E' || it.value == 'G' }.withIndex().map {
            val id = if (it.index < 10) (it.index + 48).toChar() else (55 + it.index).toChar()
            Combatant(id, if (it.value.value == 'E') Type.E else Type.G, it.value.key)
        }.toMutableList()
        combatants.forEach { map[it.pos] = it.id }
        var fullRounds = 0
        while (combatants.map { it.type }.histo().size == 2) {
            combatants = combatants.sortedWith(compareBy<Combatant> { it.pos.second }.thenBy { it.pos.first }).toMutableList()
            val killedThisRound = mutableListOf<Combatant>()
            for (c in combatants) {
                fun getNeighbourEnemies(pos: Point) = c.pos.neighborsSimple()
                    .filter { map.containsKey(it) && map[it] != '.' && map[it] != '#' }
                    .map { npos -> map[npos]!! }
                    .map { combatants.first { e -> e.id == it } }
                    .filter { it.type != c.type }
                    .filter { !killedThisRound.contains(it) }
                if (killedThisRound.contains(c)) continue
                var neighborEnemies = getNeighbourEnemies(c.pos)
                if (neighborEnemies.isEmpty()) {
                    val targets = combatants.filter { !killedThisRound.contains(it) }.filter { it.type != c.type }
                    val inRange = targets.flatMap { neighborsSimple(it.pos) }.filter { map[it] == '.' }.toSet()
                    val next: (Point) -> Iterable<Point> =
                        { it.neighborsSimple().filter { n -> map.containsKey(n) && map[n] == '.' }.sortedWith(compareBy<Point> {p -> p.second }.thenBy {p-> p.first }) }
                    val reachable = inRange.mapNotNull { destination ->
                        bfsPath(
                            c.pos,
                            { it == destination },
                            next
                        )
                    }
                    if (reachable.isEmpty()) continue
                    val minLength = reachable.minOf { it.length }
                    val chosen = reachable.filter { it.length == minLength }.sortedWith(compareBy<BfsResult<Point>> { it.path.first().second }.thenBy { it.path.first().first }).first().path.dropLast(1).last()
                    // Need to change
                    map[c.pos] = '.'
                    c.pos = chosen
                    map[c.pos] = c.id
                    neighborEnemies = getNeighbourEnemies(c.pos)
                }
                if (neighborEnemies.isNotEmpty()) {
                    val attackable = neighborEnemies.sortedWith(compareBy<Combatant> { it.hp }.thenBy { it.pos.second }.thenBy { it.pos.first })
                    val enemyToAttack = attackable.first()
                    val strength = if(c.type == Type.E) elfStrength else 3
                    enemyToAttack.hp = enemyToAttack.hp - strength
                    if (enemyToAttack.hp <= 0 ) {
                        killedThisRound.add(enemyToAttack)
                        map[enemyToAttack.pos] = '.'
                    }
                }
            }
            combatants.removeAll(killedThisRound)
            killedThisRound.clear()
            fullRounds += 1
        }
        return P(combatants, fullRounds - 1)
    }
}
