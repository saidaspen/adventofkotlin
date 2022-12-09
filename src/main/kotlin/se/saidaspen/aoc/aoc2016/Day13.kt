package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day13.run()

object Day13 : Day(2016, 13) {

    private val magicNumber = input.toInt()
    private val destination = P(31, 39)

    override fun part1(): Any {
        val next: (P<Int, Int>) -> Iterable<P<Int, Int>> = {
            val neighbors = it.neighborsSimple()
            val next = neighbors.filter { n -> n.x >= 0 && n.y >= 0 }.filter { n -> !isWall(n) }
            next
        }
        val result = bfs(P(1, 1), { it == destination }, next)
        return if (result.first != null) result.second else "N/A"
    }

    private fun isWall(e: Pair<Int, Int>): Boolean {
        val num = e.x * e.x + 3 * e.x + 2 * e.x * e.y + e.y + e.y * e.y + magicNumber
        return num.toString(2).count { it == '1' } % 2 != 0
    }

    override fun part2(): Any {
        val all = mutableSetOf(P(1, 1))
        var cur = setOf(P(1, 1))
        repeat(50) {
            cur = cur.flatMap {
                it.neighborsSimple()
                    .filter { n -> n.x >= 0 && n.y >= 0 }
                    .filter { n -> !isWall(n) } }
                .toSet()
            all += cur
        }
        return all.size
    }
}