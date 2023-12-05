package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day04.run()

object Day04 : Day(2023, 4) {

    private val matchingNumbers = input.lines()
        .map { it.split("|") }
        .map { ints(it[0]).drop(1).intersect(ints(it[1])) }
        .toList()

    override fun part1() = matchingNumbers.filter { it.isNotEmpty() }.sumOf { 2.pow(it.size - 1) }

    override fun part2(): Any {
        val freq = input.lines().indices.associateWith { 1 }.toMutableMap()
        for (i in matchingNumbers.indices) {
            for (w in 1..matchingNumbers[i].size)
                freq[i + w] = freq[i + w]!! + freq[i]!!
        }
        return freq.values.sum()
    }
}



