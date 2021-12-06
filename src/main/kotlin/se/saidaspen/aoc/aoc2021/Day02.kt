package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.ints
import kotlin.math.sign

fun main() = Day02.run()

object Day02 : Day(2021, 2) {

    override fun part1(): Any {
        var z = 0L
        var d = 0L
        input.lines().map { it.split(" ") }.map {
            when (it[0]) {
                "forward" -> z += it[1].toLong()
                "down" -> d += it[1].toLong()
                "up" -> d -= it[1].toLong()
            }
        }
        return z * d
    }

    override fun part2(): Any {
        var z = 0L
        var d = 0L
        var aim = 0L
        input.lines().map { it.split(" ") }.map {
            when (it[0]) {
                "forward" -> {
                    z += it[1].toLong()
                    d += aim * it[1].toLong()
                }
                "down" -> aim += it[1].toLong()
                "up" -> aim -= it[1].toLong()
            }
        }
        return z * d
    }
}
