package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.e

fun main() = Day18.run()

object Day18 : Day(2016, 18) {

    override fun part1(): Any {
        val map = mutableListOf(input.e())
        repeat(39) {
            val last = map.last()
            val next = last.windowed(3).map { (l, c, r) ->
                when (l.toString() + c + r) {
                    "^^." -> '^'
                    ".^^" -> '^'
                    "..^" -> '^'
                    "^.." -> '^'
                    else -> '.'
                }
            }.toMutableList()
            next.add(0, last[1])
            next.add(last.dropLast(1).last())
            map.add(next)
        }
        return map.flatten().count { it == '.' }
    }

    override fun part2(): Any {
        var row = input.e()
        var safeTiles = row.count { it == '.' }
        repeat(400000-1) {
            val last = row
            val next = last.windowed(3).map { (l, c, r) ->
                when (l.toString() + c + r) {
                    "^^." -> '^'
                    ".^^" -> '^'
                    "..^" -> '^'
                    "^.." -> '^'
                    else -> '.'
                }
            }.toMutableList()
            next.add(0, last[1])
            next.add(last.dropLast(1).last())
            safeTiles += next.count { it == '.' }
            row = next
        }
        return safeTiles
    }
}
