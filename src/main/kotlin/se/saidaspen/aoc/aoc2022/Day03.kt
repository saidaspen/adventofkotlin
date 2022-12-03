package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day03.run()

object Day03 : Day(2022, 3) {

    private fun toCode(c: Char) = if (c.isUpperCase()) c.code - 'A'.code + 27 else c.code - 'a'.code + 1

    override fun part1(): Any {
        return input.lines()
            .map { it.chunked(it.length / 2)}
            .sumOf { (a, b) -> toCode((a.e() / b.e()).first()) }
    }

    override fun part2(): Any {
        return input.lines()
            .chunked(3)
            .sumOf { (a, b, c) -> toCode((a.e() / b.e() / c.e()).first()) }
    }
}



