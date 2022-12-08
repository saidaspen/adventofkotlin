package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() = Day15.run()

object Day15 : Day(2016, 15) {

    override fun part1(): Any {
        val data = input.lines().map { ints(it) }
        var t = 0
        while (true) {
            if (data.all { (a, b, _, c) -> (a + t + c) % b == 0 }) {
                return t
            }
            t += 1
        }
    }

    override fun part2(): Any {
        val data = input.lines().map { ints(it) }.toMutableList()
        data.add(mutableListOf(data.size + 1, 11, 0, 0))
        var t = 0
        while (true) {
            if (data.all { (a, b, _, c) -> (a + t + c) % b == 0 }) {
                return t
            }
            t += 1
        }
    }
}