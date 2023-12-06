package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day06.run()

object Day06 : Day(2023, 6) {

    override fun part1(): Any {
        val races = ints(input).chunked(4)[0].zip(ints(input).chunked(4)[1])
        return races.map { race ->
            (0..race.first).mapNotNull {
                t -> if ((race.first - t) * t > race.second) 1 else 0 }.sum()
        }.reduce { acc, i -> acc * i }
    }

    override fun part2(): Any {
        val raceTime = ints(input.lines().first()).joinToString("").toLong()
        val dist = ints(input.lines().drop(1).first()).joinToString("").toLong()
        return (0..raceTime).mapNotNull { t -> if ((raceTime - t) * t > dist) 1 else 0 }.sum()
    }
}



