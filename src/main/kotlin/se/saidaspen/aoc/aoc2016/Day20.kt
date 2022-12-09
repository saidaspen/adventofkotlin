package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day

fun main() = Day20.run()

object Day20 : Day(2016, 20) {

    override fun part1(): Any {
        var available = listOf(Pair(0L, 4294967295L))
        for (l in input.lines()) {
            val (a, b) = l.split("-")
            val block = Pair(a.toLong(), b.toLong())
            available = available.flatMap { split(it, block) }
        }
        return available.map { it.first }.minOfOrNull { it }!!
    }

    override fun part2(): Any {
        var available = listOf(Pair(0L, 4294967295L))
        for (l in input.lines()) {
            val (a, b) = l.split("-")
            val block = Pair(a.toLong(), b.toLong())
            available = available.flatMap { split(it, block) }
        }
        return available.sumOf { it.second - it.first + 1 }
    }

    fun split(available: Pair<Long, Long>, blocked: Pair<Long, Long>): List<Pair<Long, Long>> {
        val result = mutableListOf<Pair<Long, Long>>()
        if (blocked.first > available.second || blocked.second < available.first) { // None blocked
            result.add(available)
        } else if (blocked.first <= available.first && blocked.second >= available.second) { // All blocked!
        } else if (blocked.first <= available.first) { // Blocks lower range
            result.add(Pair(blocked.second + 1, available.second))
        } else if (blocked.second >= available.second) { // Blocks higher range
            result.add(Pair(available.first, blocked.first - 1))
        } else { // Blocks middle
            result.add(Pair(available.first, blocked.first - 1)) // Lower range
            result.add(Pair(blocked.second + 1, available.second)) // Upper range
        }
        return result
    }
}
