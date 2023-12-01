package se.saidaspen.aoc.aoc2022
import se.saidaspen.aoc.util.*

fun main() = Day24.run()

object Day24 : Day(2022, 24) {

    val map = toMap(input).entries.associate { it.key to it.value.toString() }

    private val yMax = map.entries.maxOf { it.key.y }
    private val xMax = map.entries.maxOf { it.key.x }
    private val startPos = map.entries.first { it.key.y == 0 && it.value == "." }.key
    private val goal = map.entries.first { it.key.y == yMax && it.value == "." }.key

    private var memo = mutableMapOf(0 to map)

    override fun part1(): Any {
        data class State(val pos: Point, val time: Int)
        val next: (State) -> List<State> = { (pos, t) ->
            val next = mutableListOf<State>()
            val nextTime = t + 1
            memo.computeIfAbsent(nextTime) { step(memo[t]!!) }
            val newWorld = memo[nextTime]!!
            val candidates = pos.neighborsSimple().filter { newWorld[it] != null && newWorld[it]!! == "." }
            candidates.forEach { next.add(State(it, nextTime)) }
            if (newWorld[pos]!! == ".") {
                next.add(State(pos, nextTime))
            }
            next
        }
        return bfs(State(startPos, 0), { it.pos == goal }, next).second
    }

    private fun step(map: Map<Point, String>): Map<Point, String> {
        val blizzards = map.filter { it.value != "." && it.value != "#" }
            .flatMap { p -> p.value.e().map { p.key to it } }
            .map { (p, v) ->
                val nextP = when (v) {
                    '<' -> P(if (p.x == 1) xMax - 1 else p.x - 1, p.y)
                    '>' -> P(if (p.x == xMax - 1) 1 else p.x + 1, p.y)
                    'v' -> P(p.x, if (p.y == yMax - 1) 1 else p.y + 1)
                    '^' -> P(p.x, if (p.y == 1) yMax - 1 else p.y - 1)
                    else -> throw RuntimeException("Unsupported dir")
                }
                nextP to v.toString()
            }.groupBy { it.first }
            .entries.associate { it.key to it.value.joinToString("") { b -> b.second } }
        return map.entries.associate {
            var newVal = "."
            if (it.value == "#")
                newVal = "#"
            else if (it.key in blizzards) {
                newVal = blizzards[it.key]!!
            }
            it.key to newVal
        }
    }

    override fun part2(): Any {
        // State = 0 not found goal yet
        // State = 1 has found goal
        // State = 2 has gotten candy
        data class State(val pos: Point, val time: Int, val state: Int)

        // start: State, isEnd: (State) -> Boolean, next: (State) -> Iterable<State>
        fun nextS(s: Int, p: Point): Int {
            return if (s == 0 && p == goal) {
                1
            } else if (s == 1 && p == startPos) {
                2
            } else {
                s
            }
        }
        val next: (State) -> List<State> = { (pos, t, s) ->
            val next = mutableListOf<State>()
            val nextTime = t + 1
            memo.computeIfAbsent(nextTime) { step(memo[t]!!) }
            val newWorld = memo[nextTime]!!
            val candidates = pos.neighborsSimple().filter { newWorld[it] != null && newWorld[it]!! == "." }
            candidates.forEach { next.add(State(it, nextTime, nextS(s, it))) }
            if (newWorld[pos]!! == ".") {
                next.add(State(pos, nextTime, nextS(s, pos)))
            }
            next
        }
        return bfs(State(startPos, 0, 0), { it.pos == goal && it.state == 2 }, next).second
    }
}

