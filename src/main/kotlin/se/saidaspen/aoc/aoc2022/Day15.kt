package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day14.run()

object Day15: Day(2022, 15) {

    private val dirs = mutableListOf(Point(1, 1), Point(-1, 1), Point(-1, -1), Point(1, -1))
    private val beacons = input.lines().map { ints(it) }.map { (_, _, x2, y2) -> P(x2, y2) }.toSet()
    private val pairs = input.lines().map { ints(it) }.map { (x1, y1, x2, y2) -> P(x1 to y1, (x1 to y1).manhattanDistTo(x2 to y2)) }

    override fun part1(): Any {
        val y = 2000000
        val xMax = pairs.map { it.first.x + it.second }.max()
        val xMin = pairs.map { it.first.x - it.second }.min()
        return (xMin..xMax).count { x ->
            !beacons.contains(P(x, y)) && pairs.any {
                it.first.manhattanDistTo(
                    P(
                        x,
                        y
                    )
                ) <= it.second
            }
        }
    }

    override fun part2(): Any {
        fun pointImpossible(p: Point): Boolean {
            if (p.x <= 0 || p.x > 4000000) return true
            if (p.y <= 0 || p.y > 4000000) return true
            if (beacons.contains(p)) return true
            return pairs.any { it.first.manhattanDistTo(p) <= it.second }
        }

        fun scanRing(s: Point, ring: Long): Point? {
            val count = ring + 1
            var cur: Point = s + Point(0, ( -ring - 1).toInt())
            for (dir in dirs) for (step in 0 until count) {
                cur += dir
                if (!pointImpossible(cur)) {
                    return cur
                }
            }
            return null
        }

        val target = pairs.firstNotNullOf { scanRing(it.first, it.second.toLong()) }
        return target.first.toLong() * 4000000L + target.second.toLong()

    }
}
