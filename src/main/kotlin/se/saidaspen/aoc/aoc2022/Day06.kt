package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.e
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.removeFirst

fun main() = Day06.run()

object Day06 : Day(2022, 6) {

    override fun part1(): Any {
        val size = 4
        for (win in input.e().windowed(size).withIndex() ) {
            if (win.value.distinct().size == size && win.index >= size) return win.index + size
        }
        return ""
    }

    override fun part2(): Any {
        val size = 14
        for (win in input.e().windowed(size).withIndex() ) {
            if (win.value.distinct().size == size && win.index >= size) return win.index + size
        }
        return ""
    }
}






