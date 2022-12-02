package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.rectangle

fun main() = Day06.run()

object Day06 : Day(2015, 6) {
    override fun part1(): Any {
        val map = mutableMapOf<P<Int, Int>, Boolean>()
        input.lines()
            .map { it.substring(0, 7) to ints(it) }
            .forEach {
                val (x1, y1, x2, y2) = it.second
                val rect = rectangle(P(x1, y1), P(x2, y2))
                for (p in rect) {
                    when (it.first) {
                        "turn on" -> map[p] = true
                        "turn of" -> map[p] = false
                        "toggle " -> map[p] = map.getOrDefault(p, false).not()
                        else -> throw RuntimeException("Unsupported action '" + it.first + "'")
                    }
                }
            }
        return map.count { it.value }
    }

    override fun part2(): Any {
        val map = mutableMapOf<P<Int, Int>, Int>()
        input.lines()
            .map { it.substring(0, 7) to ints(it) }
            .forEach {
                val (x1, y1, x2, y2) = it.second
                val rect = rectangle(P(x1, y1), P(x2, y2))
                for (p in rect) {
                    when (it.first) {
                        "turn on" -> map[p] = map.getOrDefault(p, 0) + 1
                        "turn of" -> map[p] = map.getOrDefault(p, 0) - 1
                        "toggle " -> map[p] = map.getOrDefault(p, 0) + 2
                        else -> throw RuntimeException("Unsupported action '" + it.first + "'")
                    }
                    if (map[p]!! < 0 ) map[p] = 0
                }
            }
        return map.values.sum()
    }
}




