package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.e
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.removeFirst

fun main() = Day06.run()

object Day06 : Day(2022, 6) {

    override fun part1() = input.e().windowed(4).withIndex().first { it.value.distinct().size == 4 }.index + 4
    override fun part2() = input.e().windowed(14).withIndex().first { it.value.distinct().size == 14 }.index + 14

}






