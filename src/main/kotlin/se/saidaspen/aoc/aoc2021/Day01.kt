package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.*

fun main() = Day01.run()
object Day01 : Day(2021, 1) {
    override fun part1() = ints(input).windowed(2).count{it[1]>it[0]}
    override fun part2() = ints(input).windowed(4).count{it[3]>it[0]}
}
