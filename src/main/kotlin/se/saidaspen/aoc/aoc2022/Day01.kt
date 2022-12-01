package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day01.run()

object Day01 : Day(2022, 1) {
    override fun part1() = input.split("\n\n").map { ints(it).sum() }.maxOf { it }
    override fun part2() = input.split("\n\n").map { ints(it).sum() }.sortedDescending().take(3).sum()
}


