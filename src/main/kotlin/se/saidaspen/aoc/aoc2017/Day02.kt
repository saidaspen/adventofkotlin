package se.saidaspen.aoc.aoc2017


import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() = Day02.run()

object Day02 : Day(2017, 2) {

    private val rows = input.lines().map { ints(it) }
    override fun part1() = rows.sumOf { it.max().minus(it.min()) }
    override fun part2() = rows.sumOf { rowCheckSum(it) }

    private fun rowCheckSum(it: List<Int>): Int {
        val tmp = it.sorted()
        tmp.indices.forEach { i ->
            (i + 1 until tmp.size).forEach { j ->
                if (tmp[j] % tmp[i] == 0) {
                    return tmp[j] / tmp[i]
                }
            }
        }
        throw RuntimeException("Something is wrong with input")
    }
}