package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.*

fun main() = Day08.run()

object Day08 : Day(2021, 8) {

    private var normalDigits = mapOf(
        "abcefg" to 0,
        "cf" to 1,
        "acdeg" to 2,
        "acdfg" to 3,
        "bcdf" to 4,
        "abdfg" to 5,
        "abdefg" to 6,
        "acf" to 7,
        "abcdefg" to 8,
        "abcdfg" to 9,
    )

    private var permutations = "abcdefg".toList().permutations().map { it.joinToString("") }

    override fun part1() : Any {
        val outputs = input.lines()
            .map { P(it.split("|")[0], it.split("|")[1]) } // Split into input, output digits
            .map { translate(solve(it.first), it.second) }
        return outputs.sumBy { it.toCharArray().filter { c -> c == '1' || c == '4' || c == '7' || c == '8' }.size }
    }
    override fun part2() : Any {
        val outputs = input.lines()
            .map { P(it.split("|")[0], it.split("|")[1]) } // Split into input, output digits
            .map { translate(solve(it.first), it.second) }
        return outputs.sumOf { it.toInt() }
    }

    private fun translate(t: String, inp: String): String {
        return inp.split(" ")
            .filter { it.trim() != "" }
            .map { it.tr(t, "abcdefg").sortChars() }
            .joinToString("") { normalDigits[it].toString() }
    }

    private fun solve(inp: String): String {
        val digitsInp = inp.split(" ").map { it.toCharArray().sorted().joinToString("") }.filter { it.trim() != "" }
        return  permutations.first { cand ->
            digitsInp.mapNotNull { normalDigits[it.tr(cand, "abcdefg").toList().sorted().joinToString("")] }.size == 10
        }
    }
}










