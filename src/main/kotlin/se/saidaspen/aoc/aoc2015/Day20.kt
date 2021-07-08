package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day

fun main() {
    Day20.run()
}

object Day20 : Day(2015, 20) {

    override fun part1(): Any {
        val target = input.toInt()
        val packages = mutableMapOf<Int, Int>()
        for (i in 1.. target / 10) {
            (i until target / 10 step i).forEach {
                packages[it] = packages.getOrDefault(it, 0) + i * 10 }
        }
        return packages.filter { it.value > target }.entries.minByOrNull { it.key }!!.key
    }

    override fun part2(): Any {
        val target = input.toInt()
        val packages = mutableMapOf<Int, Int>()
        for (i in 1.. target / 11) {
            for (it in (i until target / 11 step i).take(50)) {
                packages[it] = packages.getOrDefault(it, 0) + i * 11
            }
        }
        return packages.filter { it.value > target }.entries.minByOrNull { it.key }!!.key    }
}
