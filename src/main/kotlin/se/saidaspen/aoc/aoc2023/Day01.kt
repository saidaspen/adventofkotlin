package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day01.run()

object Day01 : Day(2023, 1) {

    fun digits(input: String) = input.filter { it.isDigit() }.map { it.toString().toInt() }.toList()

    override fun part1() = input.lines().sumOf { digits(it)[0] * 10 + digits(it)[digits(it).size - 1] }
    override fun part2() = input.lines().sumOf { firstAndLastDigit(it) }

    private fun firstAndLastDigit(s: String): Int {
        val toInt = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        val firstDigit = toInt.map { P(it, s.indexOf(it)) }.filter { it.second >= 0 }.minBy { it.second }.first
        val lastDigit = toInt.map { P(it, s.lastIndexOf(it)) }.maxBy { it.second }.first
        return (toInt.indexOf(firstDigit) % 9 + 1) * 10 + (toInt.indexOf(lastDigit) % 9 + 1)
    }
}



