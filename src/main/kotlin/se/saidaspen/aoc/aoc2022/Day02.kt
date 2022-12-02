package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day02.run()

object Day02 : Day(2022, 2) {
    override fun part1() : Any {
           return input.lines()
                .map { it.split(" ") }
                .map{(a,x) -> when(a to x) {
                    "A" to "X" -> 3 + 1
                    "A" to "Y" -> 6 + 2
                    "A" to "Z" -> 0 + 3
                    "B" to "X" -> 0 + 1
                    "B" to "Y" -> 3 + 2
                    "B" to "Z" -> 6 + 3
                    "C" to "X" -> 6 + 1
                    "C" to "Y" -> 0 + 2
                    "C" to "Z" -> 3 + 3
                    else -> error(a to x)
                } }.sum()
    }

    override fun part2() : Any {
        return input.lines()
            .map{it.split(" ")}.map{(a,x) -> when(a to x) {
                "A" to "X" -> 0 + 3
                "A" to "Y" -> 3 + 1
                "A" to "Z" -> 6 + 2
                "B" to "X" -> 0 + 1
                "B" to "Y" -> 3 + 2
                "B" to "Z" -> 6 + 3
                "C" to "X" -> 0 + 2
                "C" to "Y" -> 3 + 3
                "C" to "Z" -> 6 + 1
                else -> error(a to x)
            } }.sum()
    }
}



