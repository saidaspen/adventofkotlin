package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day17.run()

object Day17 : Day(2017, 17) {

    private val steps = 335

    override fun part1(): Int {
        val buff = mutableListOf(0)
        var currPos = 0
        for (i in 1 until 2018) {
            val nextPos = (currPos + steps) % i
            buff.add((nextPos + 1), i)
            currPos = nextPos + 1
        }
        return buff[(currPos + 1) % buff.size]
    }

    override fun part2(): Int {
        var currPos = 0
        var currValAfterZero = 0
        for (i in 1 until 50_000_001) {
            val nextPos = (currPos + steps) % i
            if (nextPos == 0) currValAfterZero = i
            currPos = nextPos + 1
        }
        return currValAfterZero
    }
}