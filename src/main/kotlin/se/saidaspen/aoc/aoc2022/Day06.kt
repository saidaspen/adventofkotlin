package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.e
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.removeFirst

fun main() = Day06.run()

object Day06 : Day(2022, 6) {
    private fun startMarker(len: Int) = input.e().windowed(len).withIndex().first { it.value.distinct().size == len }.index + len
    override fun part1() = startMarker(4)
    override fun part2() = startMarker(14)
}






