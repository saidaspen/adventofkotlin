package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*
import java.lang.RuntimeException

fun main() = Day07.run()

object Day07 : Day(2023, 7) {

    override fun part1(): Any {
        return input.lines()
            .sortedWith(handComparator).reversed()
            .mapIndexed{ i, e -> (i+1) * e.split(" ")[1].toLong()}.sum()
    }

    private val handComparator = Comparator<String> { a, b ->
        val aCard = a.split(" ")[0]
        val bCard = b.split(" ")[0]
        val typeA = handType(aCard)
        val typeB = handType(bCard)
        when  {
            typeA > typeB -> -1
            typeB > typeA -> 1
            else ->  strongestCard(aCard, bCard)
         }
    }

    private fun strongestCard(a: String, b: String): Int {
        val ranks = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2"
        for (i in 0..4) {
            if (ranks.indexOf(a.e()[i]) < ranks.indexOf(b.e()[i])) {
                return -1
            } else if (ranks.indexOf(a.e()[i]) > ranks.indexOf(b.e()[i])) {
                return 1
            }
        }
        throw RuntimeException("Exact same value!")
    }
    private fun handType(a: String): Int {
        val histo = a.e().histo()
        return when {
            histo.values.max() == 5 -> 7 // 5 of a kind
            histo.values.max() == 4 -> 6 // four of a kind
            histo.values.max() == 3 &&  histo.containsValue(2) -> 5 // full house
            histo.values.max() == 3 -> 4 // Three of a kind
            histo.filter { it.value == 2 }.count() == 2 -> 3 // Two of a kind
            histo.values.max() == 2 -> 2 // One pair
            histo.size == 5 -> 1 // nothing
            else -> throw RuntimeException("Unknown type")
        }
    }

    private fun handTypeWithJoker(a: String): Int {
        return when {
            a.replace("J", "").e().histo().isEmpty() -> 7
            a.replace("J", "").e().histo().values.max() +  a.e().count { it == 'J' } == 5 -> 7 // 5 of a kind
            a.replace("J", "").e().histo().values.max() +  a.e().count { it == 'J' } == 4 -> 6 // four of a kind
            a.replace("J", "").e().histo().values.max() +  a.e().count { it == 'J' } == 3 && a.replace("J", "").e().histo().size == 2 -> 5 // full house
            a.replace("J", "").e().histo().values.max() +  a.e().count { it == 'J' } == 3 -> 4 // Three of a kind
            a.replace("J", "").e().histo().values.max() +  a.e().count { it == 'J' } == 2 &&  a.replace("J", "").e().histo().size == 3 -> 3 // Two of a kind
            a.replace("J", "").e().histo().values.max() +  a.e().count { it == 'J' } == 2 -> 2 // One pair
            a.e().histo().size == 5 -> 1 // High card
            else -> throw RuntimeException("Unknown type")
        }
    }

    override fun part2(): Any {
        return input.lines()
            .sortedWith(handComparator2).reversed()
            .mapIndexed{ i, e -> (i+1) * e.split(" ")[1].toLong()}.sum()
    }

    private val handComparator2 = Comparator<String> { a, b ->
        val aCard = a.split(" ")[0]
        val bCard = b.split(" ")[0]
        val typeA = handTypeWithJoker(aCard)
        val typeB = handTypeWithJoker(bCard)
        when  {
            typeA > typeB -> -1
            typeB > typeA -> 1
            else ->  strongestCardWithJoker(aCard, bCard)
        }
    }

    private fun strongestCardWithJoker(a: String, b: String): Int {
        val ranks2 = "A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J"
        for (i in 0..4) {
            if (ranks2.indexOf(a.e()[i]) < ranks2.indexOf(b.e()[i])) {
                return -1
            } else if (ranks2.indexOf(a.e()[i]) > ranks2.indexOf(b.e()[i])) {
                return 1
            }
        }
        throw RuntimeException("")
    }
}



