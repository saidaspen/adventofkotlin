package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day20.run()

object Day20 : Day(2022, 20) {
    override fun part1() = solve(1, 1)
    override fun part2() = solve(811589153, 10)

    private fun solve(key: Long, times: Int): Long {
        // Changes 1, 2, 3 to (0, 1), (1, 2), (2, 3) to keep the original positions while moving things around
        val wPos = longs(input).withIndex().map { P(it.index, it.value * key) }.toMutableList()
        repeat(times) {
            for (i in 0 until wPos.size) {
                // Idx is the current index, elem.first is the original index, elem.second is the value
                val (currIdx, elem) = wPos.withIndex().first { it.value.first == i }
                wPos.removeAt(currIdx)
                // FYI: Size has decreased by one, as we removed a value.
                val newIdx = (currIdx + elem.second).mod(wPos.size)
                wPos.add(newIdx, elem)
            }
        }
        val indexOfZero = wPos.indexOfFirst { it.second == 0L }
        return listOf(1000, 2000, 3000).sumOf { wPos[(indexOfZero + it).mod(wPos.size)].second }
    }
}
