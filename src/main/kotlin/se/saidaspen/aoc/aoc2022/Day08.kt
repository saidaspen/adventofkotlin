package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day08.run()

object Day08 : Day(2022, 8) {

    override fun part1(): Any {
        val map = toMapInt(input)
        var visible = 0
        for (p in map.keys) {
            if (map.keys.filter { it.y == p.y && it.x < p.x }.any { map[it]!! >= map[p]!! }) {
                visible++
                continue
            }
            if (map.keys.filter { it.y == p.y && it.x > p.x }.all { map[it]!! < map[p]!! }) {
                visible++
                continue
            }
            if (map.keys.filter { it.x == p.x && it.y < p.y }.all { map[it]!! < map[p]!! }) {
                visible++
                continue
            }
            if (map.keys.filter { it.x == p.x && it.y > p.y }.all { map[it]!! < map[p]!! }) {
                visible++
                continue
            }
        }
        return visible
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






