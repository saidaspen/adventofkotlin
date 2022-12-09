package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*
import kotlin.math.abs

fun main() = Day22.run()

object Day22 : Day(2016, 22) {

    override fun part1(): Any {
        val disk = mutableMapOf<P<Int, Int>, P<Int, Int>>()
        input.lines().drop(2).map {
            val (x, y, _, used, avail) = ints(it)
            disk[P(x, y)] = P(used, avail)
        }
        var viablePairs = 0
        val keys = disk.keys.toList()
        for (i in keys.indices) {
            val a = disk[keys[i]]!!
            for (k in keys.indices) {
                if (i == k) continue
                val b = disk[keys[k]]!!
                if (a.first <= b.second && a.first > 0)
                    viablePairs += 1
            }
        }
        return viablePairs
    }

    override fun part2(): Any {
        val disk = mutableMapOf<P<Int, Int>, P<Int, Int>>()
        input.lines().drop(2).map {
            val (x, y, _, used, avail) = ints(it)
            disk[P(x, y)] = P(used, avail)
        }
        val xMax = disk.keys.maxBy { it.x }.x
        val wall = disk.entries.filter { it.value.first > 250 }.minBy { it.key.x }
        val emptyNode = disk.entries.first { it.value.first == 0 }
        var result = abs(emptyNode.key.x - wall.key.x) + 1 // Empty around wall X.
        result += emptyNode.key.y // Empty to top
        result += (xMax - wall.key.x) // Empty over next to goal
        result += (5 * xMax.dec()) + 1 // Goal back to start
        return result
    }
}
