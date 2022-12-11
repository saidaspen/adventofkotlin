package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() = Day13.run()

object Day13 : Day(2017, 13) {

    override fun part1(): String {
        val ranges = input.lines().map { ints(it) }.map { it[0] to it[1] }.toMap()
        return ranges.map { if (it.key % ((it.value - 1) * 2) == 0) it.key * it.value else 0 }.sum().toString()
    }

    override fun part2(): String {
        val periods = input.lines().map { ints(it) }.map { it[0] to (it[1] - 1) * 2 }.toMap()
        var delay = 0
        while (periods.entries.any { (delay + it.key) % it.value == 0 }) delay++
        return delay.toString()
    }
}