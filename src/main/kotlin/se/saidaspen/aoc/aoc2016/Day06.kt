package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day06.run()

object Day06 : Day(2016, 6) {

    override fun part1(): Any {
        val histo = MutableList(input.lines().get(0).length){mutableMapOf<String, Int>()}
        input.lines().forEach{it.toCharArray().forEachIndexed{i, c -> histo[i][c.toString()] = histo[i].getOrDefault(c.toString(), 0) + 1 }}
        return histo.map { it.entries.maxByOrNull { e -> e.value }!! }.joinToString("") { it.key }
    }

    override fun part2(): Any {
        val histo = MutableList(input.lines().get(0).length){mutableMapOf<String, Int>()}
        input.lines().forEach{it.toCharArray().forEachIndexed{i, c -> histo[i][c.toString()] = histo[i].getOrDefault(c.toString(), 0) + 1 }}
        return histo.map { it.entries.minByOrNull { e -> e.value }!! }.joinToString("") { it.key }
    }
}
