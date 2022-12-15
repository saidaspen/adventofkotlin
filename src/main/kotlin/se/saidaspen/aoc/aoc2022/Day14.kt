package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day14.run()

object Day14 : Day(2022, 14) {
    private val source = P(500, 0)
    private var linesPoints = input.lines().flatMap { ints(it).windowed(4, 2).flatMap { (x1, y1, x2, y2) -> (x1 to y1).lineTo(x2 to y2) } }
    private var maxY = linesPoints.map { it.second }.max()
    private val directions = mutableListOf(0 to 1, -1 to 1, 1 to 1)

    override fun part1(): Any {
        val map = linesPoints.associateWith { '#' }.toMutableMap()
        var currSand = source
        var cnt = 0
        while (currSand.second < maxY) {
            val dir = directions.firstOrNull { !map.containsKey(currSand + it) }
            currSand = if (dir != null) currSand + dir else {
                cnt += 1
                map[currSand] = 'o'
                source
            }
        }
        return cnt
    }

    override fun part2(): Any {
        val map = linesPoints.associateWith { '#' }.toMutableMap()
        var currSand = source
        var cnt = 0
        val floor = maxY + 2
        while (currSand != source || !directions.all { map.containsKey(currSand + it) }) {
            val dir = directions.firstOrNull { !map.containsKey(currSand + it) && currSand.second + 1 < floor }
            currSand = if (dir != null) currSand + dir else {
                cnt += 1
                map[currSand] = 'o'
                source
            }
        }
        return cnt + 1
    }
}
