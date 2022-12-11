package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.digits

fun main() = Day01.run()

object Day01 : Day(2017, 1) {
    private var digits =  digits(input)
    override fun part1() =  digits.filterIndexed{ i, e -> e == digits[(i + 1) % digits.size] }.sum()
    override fun part2() = digits.filterIndexed{ i, e -> e == digits[(i + digits.size/2) % digits.size] }.sum()
}