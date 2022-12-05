package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*


fun main() = Day04.run()

object Day04 : Day(2022, 4) {

    override fun part1() : Any {
        return input.lines()
            .map { positives(it) }.map { P((it[0]..it[1]), (it[2]..it[3])) }
            .count { it.x.isSubset(it.y) || it.y.isSubset(it.x) }
    }

    override fun part2() = input.lines()
        .map { positives(it) }.map { P((it[0]..it[1]), (it[2]..it[3])) }
        .count { it.x.intersect(it.y).isNotEmpty() }
}






