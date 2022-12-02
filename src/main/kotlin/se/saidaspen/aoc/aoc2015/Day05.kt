package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.*

fun main() = Day05.run()

object Day05 : Day(2015, 5) {
    override fun part1(): Any {
        return input.lines().map {
            digits(it.tr("aeiou", "11111")).sum() >= 3
                    && it.e().windowed(2).any { (a, b) -> a == b }
                    && !it.contains("ab|cd|pq|xy".toRegex())
        }.count { it }
    }

    override fun part2(): Any {
        return input.lines().map {
            it.e().windowed(3, 2).map { t -> t.windowed(2).map { d -> d.joinToString("") }.distinct() }.flatten()
                .histo().containsValue(2)
                    && it.e().windowed(3).any { (a, _, c) -> a == c }
        }.count { it }
    }
}
