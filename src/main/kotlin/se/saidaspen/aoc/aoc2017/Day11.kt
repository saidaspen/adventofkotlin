package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.HexGridTile
import kotlin.math.max

fun main() = Day11.run()

object Day11 : Day(2017, 11) {

    override fun part1(): String {
        val steps = input.split(",").toList()
        val origin = HexGridTile(0, 0, 0)
        var currPos = origin
        for (step in steps) currPos = currPos.move(step)
        return origin.distance(currPos).toString()
    }

    override fun part2(): String {
        val steps = input.split(",").toList()
        val origin = HexGridTile(0, 0, 0)
        var currPos = origin
        var max = 0
        for (step in steps) {
            currPos = currPos.move(step)
            max = max(max, origin.distance(currPos))
        }
        return max.toString()
    }
}