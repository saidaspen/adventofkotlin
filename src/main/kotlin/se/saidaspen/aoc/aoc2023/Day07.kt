package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*
import java.lang.RuntimeException

fun main() = Day07.run()

object Day07 : Day(2023, 7) {

    private const val RANKS = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2, *"

    override fun part1() = input.lines()
        .sortedWith(handComparator).reversed()
        .mapIndexed { i, e -> (i + 1) * e.split(" ")[1].toLong() }
        .sum()

    override fun part2() = input.lines()
        .map { it.replace("J", "*") } // J is actually Joker!
        .sortedWith(handComparator).reversed()
        .mapIndexed { i, e -> (i + 1) * e.split(" ")[1].toLong() }
        .sum()

    private val handComparator = Comparator<String> { a, b ->
        val typeA = handType(a.split(" ")[0])
        val typeB = handType(b.split(" ")[0])
        when {
            typeA > typeB -> -1
            typeB > typeA -> 1
            else -> a.e().zip(b.e()).filter { it.first != it.second }.map {
                if (RANKS.indexOf(it.first) < RANKS.indexOf(it.second)) -1 else 1
            }.first()
        }
    }

    private fun handType(a: String): Int {
        val occurences = a.replace("*", "").e().histo().values
        val jokers = a.e().count { it == '*' }
        return when {
            jokers == 5 -> 7
            jokers + occurences.max() == 5 -> 7 // Five of a kind
            jokers + occurences.max() == 4 -> 6 // Four of a kind
            occurences.max() == 3 && occurences.filter { it == 2 }.size == 1 -> 5 // Full house
            occurences.max() == 2 && jokers + occurences.filter { it == 2 }.size == 3 -> 5 // Full house
            jokers + occurences.max() == 3 -> 4 // Three of a kind
            jokers + occurences.max() == 2 && occurences.size == 3 -> 3 // Two pairs
            jokers + occurences.max() == 2 -> 2 // One pair
            occurences.size == 5 -> 1 // High card
            else -> throw RuntimeException("Unknown type")
        }
    }
}



