package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.pow

fun main() = Day19.run()

object Day19 : Day(2016, 19) {

    override fun part1(): Any {
        val data = input.lines().first().toInt().toString(2)
        val l = data.drop(1) + data[0]
        return l.toInt(2)
    }

    override fun part2(): Any {
        val data = input.toInt()
        val look = mutableMapOf(1 to 0)
        var x = 2
        while (x <= data) {
            val kill = x / 2
            var win = look[x-1]!! + 1
            if (win >= kill) win++
            if (win >= x) win -= x
            look[x] = win
            x++
        }

        return (look[data]!! + 1)
    }
}
