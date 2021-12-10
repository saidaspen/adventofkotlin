package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.*

fun main() = Day10.run()

object Day10 : Day(2021, 10) {

    private var chunkDelims = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')

    override fun part1() =  input.lines().map { check(it) }.sumOf { scoreErrorChar(it.first) }
    override fun part2(): Any {
        val sortedScores = input.lines()
            .map { check(it) }
            .filter { it.first == null }
            .map { scoreOfEnding(it.second) }
            .sortedDescending()
        return sortedScores[sortedScores.size/2]
    }

    private fun scoreErrorChar(c : Char?): Int {
        return when (c) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    private fun scoreClosingChar(seChar: Char): Int {
        return when (seChar) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }
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

    private fun scoreOfEnding(delims: List<Char>) =  delims.fold(0L) { acc, e -> acc * 5 + scoreClosingChar(e) }
}












