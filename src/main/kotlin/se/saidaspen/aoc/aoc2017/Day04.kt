package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day04.run()

object Day04 : Day(2017, 4) {

    override fun part1() = input.lines().count { it.split(" ").distinct() == it.split(" ") }
    override fun part2() = input.lines().count {
        val words = it.split(" ").map { w -> w.toCharArray().sorted().joinToString("") }
        words.distinct() == words
    }
}