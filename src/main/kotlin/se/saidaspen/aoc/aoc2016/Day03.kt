package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day03.run()

object Day03 : Day(2016, 3) {

    override fun part1(): Any {
        return input.lines().map { ints(it) }.count { isValid(it) }
    }

    override fun part2(): Any {
        return input.lines().asSequence().windowed(3, 3).map {
            val vals = ints(it.joinToString())
            listOf(listOf(vals[0], vals[3], vals[6]), listOf(vals[1], vals[4], vals[7]), listOf(vals[2], vals[5], vals[8]))
        }.flatten().filter { isValid(it) }.count()
    }

    private fun isValid(t: List<Int>): Boolean = !IntRange(0, 2).any { t[it] >= t[(it + 1) % 3] + t[(it + 2) % 3] }

}

