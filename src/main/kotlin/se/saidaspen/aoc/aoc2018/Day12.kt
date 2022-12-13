package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.e
import java.lang.RuntimeException

fun main() = Day12.run()

object Day12 : Day(2018, 12) {


    private val rules: Set<String> = parseRules(input.lines())
    private val initialState: String = input.lines().first().substring(15)

    private fun parseRules(input: List<String>): Set<String> =
        input
            .drop(2)
            .filter { it.endsWith("#") }
            .map { it.take(5) }
            .toSet()

    override fun part1(): Any {
        return updatePlants().drop(19).first().second
    }

    override fun part2(): Any {
        val targetGeneration: Long = 50_000_000_000
        var previousDiff = 0L
        var previousSize = 0L
        var generationNumber = 0

        // Go through the sequence until we find one that grows the same one as its previous generation
        updatePlants().dropWhile { thisGen ->
            val thisDiff = thisGen.second - previousSize // Our diff to last generation
            if (thisDiff != previousDiff) {
                // Still changing
                previousDiff = thisDiff
                previousSize = thisGen.second
                generationNumber += 1
                true
            } else {
                // We've found it, stop dropping.
                false
            }
        }.first() // Consume first because sequences are lazy and it won't start otherwise.
        return previousSize + (previousDiff * (targetGeneration - generationNumber))
    }

    private fun updatePlants(state: String = initialState): Sequence<Pair<String, Long>> = sequence {
        var zeroIndex = 0
        var currentState = state
        while (true) {
            while (!currentState.startsWith(".....")) {
                currentState = ".$currentState"
                zeroIndex++
            }
            while (!currentState.endsWith(".....")) {
                currentState = "$currentState."
            }

            currentState = currentState
                .toList()
                .windowed(5, 1)
                .map { it.joinToString(separator = "") }
                .map { if (it in rules) '#' else '.' }
                .joinToString(separator = "")

            zeroIndex -= 2 // Because there are two positions to the left of the first real center and were not evaluated
            yield(Pair(currentState, currentState.sumIndexesFrom(zeroIndex)))
        }
    }

    private fun String.sumIndexesFrom(zero: Int): Long =
        this.mapIndexed { idx, c -> if (c == '#') idx.toLong() - zero else 0 }.sum()
}
