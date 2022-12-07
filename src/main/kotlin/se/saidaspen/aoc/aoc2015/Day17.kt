package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() = Day17.run()

object Day17 : Day(2015, 17) {

    override fun part1(): Any {
        val containers = ints(input)
        var ways = mapOf(150 to 1)
        for (container in containers) {
            val n = ways.toMutableMap()
            for ((left, v) in ways) {
                if (left >= container) {
                    n[left - container] = n.getOrDefault(left - container, 0) + v
                }
            }
            ways = n
        }
        return ways[0]!!
    }

    override fun part2(): Any {
        val containers = ints(input)
        var ways = mutableMapOf(150 to mutableMapOf(0 to 1))
        for (container in containers) {
            val n = ways.mapValues { (_, v) -> v.toMutableMap() }.toMutableMap()
            for ((left, v) in ways) {
                if (left >= container) {
                    for ((i, j) in v) {
                        val m = n.getOrPut(left - container, ::mutableMapOf)
                        m[i + 1] = m.getOrDefault(i + 1, 0) + j
                    }
                }
            }
            ways = n
        }
        return ways[0]!!.entries.minBy { it.key }.value
    }
}

