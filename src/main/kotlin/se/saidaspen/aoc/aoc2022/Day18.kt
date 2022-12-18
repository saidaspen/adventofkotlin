package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day18.run()

object Day18 : Day(2022, 18) {

    private val points = input.lines().map { ints(it) }.map { Point3D.of(it) }

    override fun part1(): Any {
        return points.sumOf { it.hexNeightbours().count { n -> n !in points } }
    }

    override fun part2(): Any {
        val min = points.minBy { it.x + it.y + it.z}
        val firstAir = min.hexNeightbours().first { it !in points }
        val queue = mutableListOf(firstAir)
        val airBlocks = mutableSetOf<Point3D>()
        while (queue.isNotEmpty()) {
            val cur = queue.removeLast()
            airBlocks.add(cur)
            for (n in cur.hexNeightbours()) {
                if (airBlocks.contains(n) || points.contains(n) || queue.contains(n)) continue
                if (points.map { it.dist(n) }.min() > 2) continue // Too far from anything
                queue.add(n)
            }
        }

        var surfaceArea = 0
        for (c in airBlocks) {
            val nc = c.hexNeightbours()
            for (p in nc) {
                if (points.contains(p)){
                    surfaceArea += 1
                }
            }
        }
        return surfaceArea
    }
}