package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day04.run()

object Day04 : Day(2023, 4) {

    private val cards = input.lines()
        .map { it.split("|") }
        .map { P(ints(it[0]).drop(1), ints(it[1])) }
        .toList()

    override fun part1() = cards
        .map { it.first.intersect(it.second) }
        .filter { it.isNotEmpty() }
        .sumOf { 2.pow(it.size - 1) }

    override fun part2(): Any {
        val freq = (0..<input.lines().size).associateWith { 1 }.toMutableMap()
        for (i in cards.indices) {
            val wins = cards[i].first.intersect(cards[i].second).size
            for (w in 1..wins) {
                freq[i + w] = freq[i + w]!! + freq[i]!!
            }
        }
        return freq.values.sum()
    }
}



