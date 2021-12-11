package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.neighbors

fun main() = Day11.run()

object Day11 : Day(2021, 11) {

    override fun part1(): Any {
        val lines = input.lines()
        val octs = mutableMapOf<P<Int, Int>, Int>()
        for (line in lines.indices) {
            val lineChars = lines[line].toCharArray()
            for (col in lineChars.indices) {
                octs[P(col, line)] = lineChars[col].toString().toInt()
            }
        }
        var flashes = 0
        var step = 1
        while (step <= 100) {
            octs.keys.forEach { octs.merge(it, 1, Int::plus) }
            // flashes
            val hasFlashed = mutableListOf<Pair<Int, Int>>()
            var toFlash: List<Pair<Int, Int>>
            flashes@ do {
                toFlash = octs.entries
                    .filter { it.value > 9 }
                    .map { it.key }
                    .filter { octs.containsKey(it) }
                    .filter { !hasFlashed.contains(it) }
                    .distinct()
                hasFlashed.addAll(toFlash)
                flashes += toFlash.size
                toFlash.flatMap { neighbors(it) }.filter { octs.containsKey(it) }
                    .forEach { octs.merge(it, 1, Int::plus) }
            } while (toFlash.isNotEmpty())
            // reset
            hasFlashed.forEach { octs[it] = 0 }
            step++
        }
        return flashes
    }

    override fun part2(): Any {
        val lines = input.lines()
        val octs = mutableMapOf<P<Int, Int>, Int>()
        for (line in lines.indices) {
            val lineChars = lines[line].toCharArray()
            for (col in lineChars.indices) {
                octs[P(col, line)] = lineChars[col].toString().toInt()
            }
        }
        var flashes = 0
        var step = 1
        while (true) {
            octs.keys.forEach { octs.merge(it, 1, Int::plus) }
            // flashes
            val hasFlashed = mutableListOf<Pair<Int, Int>>()
            var toFlash: List<Pair<Int, Int>>
            flashes@ do {
                toFlash = octs.entries
                    .filter { it.value > 9 }
                    .map { it.key }
                    .filter { octs.containsKey(it) }
                    .filter { !hasFlashed.contains(it) }
                    .distinct()
                hasFlashed.addAll(toFlash)
                flashes += toFlash.size
                toFlash.flatMap { neighbors(it) }.filter { octs.containsKey(it) }
                    .forEach { octs.merge(it, 1, Int::plus) }
            } while (toFlash.isNotEmpty())
            // reset
            hasFlashed.forEach { octs[it] = 0 }
            if (hasFlashed.size == octs.size) {
                return step
            }
            step++
        }
    }
}












