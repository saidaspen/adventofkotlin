package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day09.run()

object Day09 : Day(2023, 9) {
    override fun part1(): Any {
        return input.lines().map { longs(it) }.sumOf { l ->
            val lasts = mutableListOf<Long>()
            var lst = l
            while (!lst.all { it == 0L }) {
                lasts.add(lst.last())
                lst = lst.windowed(2).map { it[1] - it[0] }
            }
            lasts.sum()
        }
    }

    override fun part2(): Any {
        return input.lines().map { longs(it) }.sumOf { l->
            val firsts = mutableListOf<Long>()
            var lst = l
            while (!lst.all { it == 0L }) {
                firsts.add(lst.first())
                lst = lst.windowed(2).map { it[1] - it[0] }
            }
            firsts.reversed().fold(0L) {acc, e -> e - acc}
        }
    }

}

