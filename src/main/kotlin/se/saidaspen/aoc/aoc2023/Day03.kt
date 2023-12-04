package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day03.run()

object Day03 : Day(2023, 3) {
    private val map = toMap(input)
    private val ymax = map.keys.map { it.second }.max()
    private val xmax = map.keys.map { it.first }.max()

    override fun part1(): Any {
        val partIds = mutableListOf<Int>()
        for (y in 0..ymax) {
            var num = ""
            var isPart = false
            for (x in 0..xmax) {
                val p = P(x, y)
                val character = map[p]!!
                if (character.isDigit()) {
                    num += character
                    if (p.neighbors().filter { map[it] != null }
                            .any { map[it]!! != '.' && !map[it]!!.isLetterOrDigit() }) {
                        isPart = true
                    }
                }
                if (!character.isLetterOrDigit() || x == xmax) {
                    if (isPart && num != "") {
                        partIds.add(num.toInt())
                    }
                    num = ""
                    isPart = false
                }
            }
        }
        return partIds.sum()
    }

    override fun part2(): Any {
        val partIds = mutableListOf<Int>()
        val partsLocations = mutableMapOf<P<Int, Int>, Int>()
        for (y in 0..ymax) {
            var num = ""
            var isPart = false
            for (x in 0..xmax) {
                val p = P(x, y)
                val character = map[p]!!
                if (character.isDigit()) {
                    num += character
                    if (p.neighbors().filter { map[it] != null }.any { map[it]!! != '.' && !map[it]!!.isLetterOrDigit() }) {
                        isPart = true
                    }
                }
                if (!character.isLetterOrDigit() || x == xmax) {
                    if (isPart && num != "") {
                        partIds.add(num.toInt())
                        val partNumRangeShift = if (!character.isLetterOrDigit()) (1.. num.length)  else num.indices
                        for (d in partNumRangeShift) {
                            partsLocations[p - P(d, 0)] = num.toInt()
                        }
                    }
                    num = ""
                    isPart = false
                }

            }
        }
        return map.filter { it.value == '*' }
            .map {
                neighbors(it.key).filter { n -> partsLocations.contains(n) }.map { n -> partsLocations[n]!! }.distinct()
            }.filter { it.size == 2 }
            .sumOf { it[0] * it[1] }
    }
}



