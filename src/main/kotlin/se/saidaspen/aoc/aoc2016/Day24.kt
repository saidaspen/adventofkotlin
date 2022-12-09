package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day24.run()

object Day24 : Day(2016, 24) {

    data class State(val pos : P<Int, Int>, val visited: Set<Int>)

    override fun part1(): Any {
        val map = toMap(input)
        val goals = map.values.filter { it.digitToIntOrNull() != null && it != '0' }.toSet()
        return bfs(
            State(map.entries.firstOrNull { it.value == '0' }!!.key, setOf()),
            { it.visited.size == goals.size },
            {
                it.pos.neighborsSimple()
                    .filter { n -> map.containsKey(n) && map[n] != '#' }
                    .map { n ->
                        val newVisited = it.visited.toMutableSet()
                        if (map[n]!!.digitToIntOrNull() != null && map[n] != '0') {
                            newVisited.add(map[n]!!.digitToInt())
                        }
                        State(n, newVisited)
                    }
            }
        ).second
    }

    override fun part2(): Any {
        val map = toMap(input)
        val goals = map.values.filter { it.digitToIntOrNull() != null && it != '0' }.toSet()
        val startPos = map.entries.firstOrNull { it.value == '0' }!!.key
        return bfs(
            State(startPos, setOf()),
            { it.visited.size == goals.size && it.pos == startPos},
            {
                it.pos.neighborsSimple()
                    .filter { n -> map.containsKey(n) && map[n] != '#' }
                    .map { n ->
                        val newVisited = it.visited.toMutableSet()
                        if (map[n]!!.digitToIntOrNull() != null && map[n] != '0') {
                            newVisited.add(map[n]!!.digitToInt())
                        }
                        State(n, newVisited)
                    }
            }
        ).second
    }
}
