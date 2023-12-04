package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day02.run()

object Day02 : Day(2023, 2) {

    override fun part1() : Any {
        var possible = 0
        for (line in input.lines()) {
            val gameID = words(line)[1].replace(":", "").toInt()
            val game = line.split(":")[1]
            val reds = words(game).windowed(2).filter { it[1].startsWith("red") }.maxOf { it[0].toInt() }
            val greens = words(game).windowed(2).filter { it[1].startsWith("green") }.maxOf { it[0].toInt() }
            val blues = words(game).windowed(2).filter { it[1].startsWith("blue") }.maxOf { it[0].toInt() }
            if (reds <= 12 && greens <= 13 && blues <= 14)
                possible += gameID
        }
        return possible
    }

    override fun part2() : Any {
        var sum = 0
        for (line in input.lines()) {
            val game = line.split(":")[1]
            val reds = words(game).windowed(2).filter { it[1].startsWith("red") }.maxOf { it[0].toInt() }
            val greens = words(game).windowed(2).filter { it[1].startsWith("green") }.maxOf { it[0].toInt() }
            val blues = words(game).windowed(2).filter { it[1].startsWith("blue") }.maxOf { it[0].toInt() }
            sum += reds * greens * blues
        }
        return sum
    }
}



