package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.*
import kotlin.math.abs

fun main() {
    Day06.run()
}

object Day06 : Day(2018, 6) {

    override fun part1(): Any {
        val grid = Grid(".")
        val coords = input.lines().map { ints(it) }.map { P(it[0], it[1]) }.toList()
        for ((i, p) in coords.withIndex()) {
            grid[p] = i.toString()
        }
        for (r in 0 until grid.height){
            for (c in 0 until grid.width) {
                val distances = coords.map { P(it, manhattanDist(c, r, it.first, it.second)) }.sortedBy { it.second }
                val closest = if (distances[0].second == distances[1].second) "." else coords.indexOf(distances[0].first).toString()
                grid[c, r] = closest
            }
        }
        val largestArea = (coords.indices).maxOf { areaOf(it, grid) }
        for ((i, p) in coords.withIndex()) {
            grid[p] = i.toString()
        }
        return largestArea
    }

    override fun part2(): Any {
        val grid = Grid(".")
        val coords = input.lines().map { ints(it) }.map { P(it[0], it[1]) }.toList()
        for ((i, p) in coords.withIndex()) {
            grid[p] = i.toString()
        }
        var sum = 0
        for (r in 0 until grid.height){
            for (c in 0 until grid.width) {
                val distances = coords.sumOf { manhattanDist(c, r, it.first, it.second) }
                if (distances < 10_000){
                    sum++
                }
            }
        }
        return sum
    }

    private fun areaOf(v: Int, grid: Grid<String>): Int {
        val points = grid.points().filter { it.second == v.toString() }
        val coords = points.map { it.first }
        return if (coords.map { it.first }.any { it == 0 || it == grid.height -1 } || coords.map { it.second }.any { it == 0 || it == grid.width -1 })
            0
        else points.size
    }

    private fun manhattanDist(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        return abs(x1-x2) + abs(y1-y2)
    }

}
