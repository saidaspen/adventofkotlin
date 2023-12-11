package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day10.run()

object Day10 : Day(2023, 10) {

    private val map : MutableMap<P<Int, Int>, Char> = toMap(input)
    private val startPos = map.entries.first { it.value == 'S' }.key

    private var neighbours = mapOf(
        '|' to listOf(P(0, 1), P(0, -1)),
        '-' to listOf(P(1, 0), P(-1, 0)),
        'F' to listOf(P(1, 0), P(0, 1)),
        '7' to listOf(P(-1, 0), P(0, 1)),
        'L' to listOf(P(1, 0), P(0, -1)),
        'J' to listOf(P(-1, 0), P(0, -1)),
    )

    override fun part1(): Any {
        // Hardcoded start position
        map[startPos] = '7'
        return findEdges().size / 2
    }

    override fun part2(): Any {
        val verticalCrossings = listOf('|', 'J', 'L')
        val edges = findEdges()
        val notInLoop = map.entries.filter { !edges.contains(it.key) }
        notInLoop.map { it.key }.forEach{ map[it] = '.'}
        return notInLoop
            // Map to the number of vertical pipes to the left
            .map { e -> (0 until e.key.first).count { verticalCrossings.contains(map[P(it, e.key.second)]) } }
            // If it is odd, it must be inside, I think!
            .count { it % 2 != 0 }
    }

    private fun findEdges(): Set<P<Int, Int>> {
        data class State(val path: P<P<Int, Int>, P<Int, Int>>, val dist: Int)
        var curr = State(P(startPos, startPos + P(0, 1)), 1)
        val edges = mutableSetOf<P<Int, Int>>()
        edges.add(curr.path.first)
        while (curr.path.second != startPos) {
            edges.add(curr.path.second)
            val next = neighbours[map[curr.path.second]]!!
                .map { it + curr.path.second }
                .filter { map.containsKey(it) }
                .first { it != curr.path.first }
            curr = State(P(curr.path.second, next), curr.dist + 1)
        }
        return edges
    }
}
