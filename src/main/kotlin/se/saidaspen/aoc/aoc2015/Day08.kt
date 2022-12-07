package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day

fun main() = Day08.run()

object Day08 : Day(2015, 8) {

    override fun part1(): Any {
        return input.lines().sumOf {
            var memChars = 0
            val chars = it.toCharArray()
            var idx = 1 // Skip first char
            while (idx < chars.size - 1) { // Skip last char
                idx += if (chars[idx] == '\\') if (chars[idx + 1] == 'x') 4 else 2 else 1
                memChars++
            }
            it.length - memChars
        }
    }

    override fun part2(): Any {
        return input.lines()
            .sumOf { it.toCharArray().map { c -> if (c == '\\' || c == '"') 2 else 1 }.sum() + 2 - it.length }
    }
}


