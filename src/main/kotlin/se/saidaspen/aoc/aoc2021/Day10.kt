package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.*

fun main() = Day10.run()

object Day10 : Day(2021, 10) {

    private var chunkDelims = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')
    private var scoreErrorChar = mapOf(')' to 3, '}' to 1197, ']' to 57, '>' to 25137)
    private var scoreClosings = mapOf(')' to 1, '}' to 3, ']' to 2, '>' to 4)

    override fun part1() =  input.lines().map { check(it) }.sumOf { scoreErrorChar.getOrDefault(it.first, 0) }
    override fun part2(): Any {
        val sortedScores = input.lines()
            .map { check(it) }
            .filter { it.first == null }
            .map { scoreOfEnding(it.second) }
            .sortedDescending()
        return sortedScores[sortedScores.size/2]
    }

    private fun check(s: String): P<Char?, List<Char>> {
        val chunkOrder = mutableListOf<Char>()
        for (c in s.toCharArray().toList()) {
            if (chunkDelims.keys.contains(c)) {
                chunkOrder.add(c)
            } else {
                val lastChunkOpen = chunkOrder.removeLast()
                if (c != chunkDelims[lastChunkOpen]) {
                    return P(c, chunkOrder.map { chunkDelims[it]!! }.reversed())
                }
            }
        }
        return P(null, chunkOrder.map { chunkDelims[it]!! }.reversed())
    }

    private fun scoreOfEnding(delims: List<Char>) =  delims.fold(0L) { acc, e -> acc * 5 + scoreClosings.getOrDefault(e, 0) }
}












