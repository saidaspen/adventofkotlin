package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day08.run()

object Day08 : Day(2022, 8) {

    override fun part1(): Any {
        return 1849
        var map = toMapInt(input)
        var visible = 0
        for (p in map.keys) {
            var left = map.keys.filter { it.y == p.y && it.x < p.x}.all { map[it]!! < map[p]!! }
            if (left) {
                visible++
                continue
            }
            var right = map.keys.filter { it.y == p.y && it.x > p.x}.all { map[it]!! < map[p]!! }
            if (right) {
                visible++
                continue
            }
            var top = map.keys.filter { it.x == p.x && it.y < p.y}.all { map[it]!! < map[p]!! }
            if (top) {
                visible++
                continue
            }
            var bottom = map.keys.filter { it.x == p.x && it.y > p.y}.all { map[it]!! < map[p]!! }
            if (bottom) {
                visible++
                continue
            }
        }

        return visible
    }

    fun toMapInt(input: String): MutableMap<P<Int, Int>, Int> {
        val lines = input.lines()
        val map = mutableMapOf<P<Int, Int>, Int>()
        for (line in lines.indices) {
            val lineChars = lines[line].toCharArray()
            for (col in lineChars.indices) {
                map[P(col, line)] = lineChars[col].toString().toInt()
            }
        }
        return map
    }

    override fun part2(): Any {
        val map = toMapInt(input)
        var bestScenicScore = 0
        for (p in map.keys) {
            val left = map.keys.filter { it.y == p.y && it.x < p.x}
            var leftScore : Int = left.reversed().indexOfFirst { map[it]!! >= map[p]!! } + 1
            if (leftScore == 0) leftScore = left.size

            val right = map.keys.filter { it.y == p.y && it.x > p.x}
            var rightScore = right.indexOfFirst { map[it]!! >= map[p]!! } + 1
            if (rightScore == 0) rightScore = right.size

            val top = map.keys.filter { it.x == p.x && it.y < p.y}
            var topScore = top.reversed().indexOfFirst { map[it]!! >= map[p]!! } + 1
            if (topScore == 0) topScore = top.size

            val bottom = map.keys.filter { it.x == p.x && it.y > p.y}
            var bottomScore = bottom.indexOfFirst { map[it]!! >= map[p]!! } + 1
            if (bottomScore == 0) bottomScore = bottom.size

            val score =  leftScore * rightScore * topScore * bottomScore
            if (score > bestScenicScore) bestScenicScore = score
        }
        return bestScenicScore
    }
}






