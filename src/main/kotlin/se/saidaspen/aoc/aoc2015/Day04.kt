package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.*

fun main() = Day04.run()

object Day04 : Day(2015, 4) {

    override fun part1(): Any {
        var x = 0
        while (!md5(input + x).startsWith("00000")) x+=1
        return x
    }

    override fun part2(): Any {
        var x = 0
        while (!md5(input + x).startsWith("000000")) x+=1
        return x
    }
}
