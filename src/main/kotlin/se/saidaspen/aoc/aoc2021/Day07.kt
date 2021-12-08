package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints
import kotlin.math.abs

fun main() = Day07.run()

object Day07 : Day(2021, 7) {
    override fun part1(): Any {
        val startPositions = ints(input)
        return (startPositions.minOrNull()!!..startPositions.maxOrNull()!!).minOf { sp ->
            startPositions.sumBy { abs(it - sp) }
        }
    }

    override fun part2(): Any {
        val startPos = ints(input)
        return (startPos.minOrNull()!!..startPos.maxOrNull()!!).minOf { p ->
            startPos.sumBy { (abs(it - p) + 1) * abs(it - p) / 2 }
        }
    }
}




