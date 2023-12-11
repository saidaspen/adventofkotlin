package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*
import kotlin.math.abs

fun main() = Day11.run()

object Day11 : Day(2023, 11) {

    private val map = toMap(input)
    private val emptyRows = input.lines().indices.filter { r ->  map.filter { it.key.second == r }.all { it.value == '.' } }.toList()
    private val emptyCols = input.lines()[0].indices.filter { c ->  map.filter { it.key.first == c }.all { it.value == '.' } }.toList()
    private val galaxies = map.filter { it.value == '#' }.keys.toList()
    private val pairs = combinations(galaxies.toTypedArray(), 2).toList()

    private fun dist(a: P<Int, Int>, b: P<Int, Int>, exp: Long): Long {
        val timesY = emptyRows.count { it < b.second && it > a.second }.toLong()
        val timesX = emptyCols.count { it < b.first && it > a.first || it > b.first && it < a.first }.toLong()
        return abs(a.x - b.x).toLong() + timesX * exp + abs(a.y - b.y).toLong() + timesY * exp
    }

    override fun part1() = pairs.sumOf { dist(it[0], it[1], 1) }
    override fun part2() =  pairs.sumOf { dist(it[0], it[1], 999999) }
}




