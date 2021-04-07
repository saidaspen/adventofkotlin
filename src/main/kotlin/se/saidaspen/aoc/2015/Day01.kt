package se.saidaspen.aoc.`2015`

import se.saidaspen.aoc.util.Day
import java.lang.RuntimeException

fun main() {
    Day01.run()
}

object Day01 : Day(2015, 1) {

    override fun part1() = input.toCharArray().map { if (it == '(') 1 else -1 }.sum()

    override fun part2(): Any {
        var floor = 0
        var pos = 1
        for (c in input.toCharArray()) {
            if (c == '(') floor++
            else floor--
            if (floor == -1) return pos
            pos++
        }
        throw RuntimeException("Santa never reaches floor -1")
    }
}
