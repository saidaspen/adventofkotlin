package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.*

fun main() = Day10.run()

object Day10 : Day(2018, 10) {

    override fun part1(): Any {
        val points = input.lines().map {
            val p = P(ints(it)[0], ints(it)[1])
            val v = P(ints(it)[2], ints(it)[3])
            P(p, v)
        }.toMutableList()
        while (true){
            val minY = points.minOf { it.first.second }
            val maxY = points.maxOf { it.first.second }
            val height = maxY - minY
            if (height == 9){ // I ran it once first to see which was the minimum spanning height of the "picture", I found 9, that must be the message.
                printSky(points)
                println(height)
                break
            }
            moveAll(points)
        }
        return "PPNJEENH"
    }

    private fun printSky(points: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>) {
        val minX = points.minOf { it.first.first }
        val maxX = points.maxOf { it.first.first }
        val minY = points.minOf { it.first.second }
        val maxY = points.maxOf { it.first.second }
        for (r in minY..maxY) {
            var line = ""
            for (c in minX..maxX) {
                line += if (points.any { it.first.first == c && it.first.second == r }) "#" else " "
            }
            println(line)
        }
    }

    private fun moveAll(points: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>) {
        for (i in points.indices){
            var p = points.removeAt(i)
            p = P(P(p.first.first + p.second.first, p.first.second + p.second.second), p.second)
            points.add(i, p)
        }
    }

    override fun part2(): Any {
        val points = input.lines().map {
            val p = P(ints(it)[0], ints(it)[1])
            val v = P(ints(it)[2], ints(it)[3])
            P(p, v)
        }.toMutableList()
        var t = 0
        while (true){
            val minY = points.minOf { it.first.second }
            val maxY = points.maxOf { it.first.second }
            val height = maxY - minY
            if (height == 9){
                return t
            }
            t++
            moveAll(points)
        }
    }
}
