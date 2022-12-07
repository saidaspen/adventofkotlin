package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day01.run()

object Day01 : Day(2016, 1) {

    override fun part1(): Any {
        val walker = Walker(Pair(0, 0), Walker.Dir.N)
        input.split(",")
            .map { it.trim() }
            .map { P(if (it.substring(0, 1) == "R") Turn.R else Turn.L, it.substring(1).toInt()) }
            .forEach {
                walker.turn(it.first)
                walker.move(it.second)
            }
        return walker.distanceTo(P(0, 0))
    }

    override fun part2(): Any {
        val walker = Walker(Pair(0, 0), Walker.Dir.N)
        val visited = hashSetOf(walker.pos)
        val instructions = input.split(",")
            .map { it.trim() }
            .map { P(if (it.substring(0, 1) == "R") Turn.R else Turn.L, it.substring(1).toInt()) }
            .toList()
        outer@ for (instr in instructions) {
            walker.turn(instr.first)
            for (step in 1..instr.second) {
                walker.move(1)
                if (visited.contains(walker.pos)) break@outer
                else visited.add(walker.pos)
            }
        }
        return walker.distanceTo(P(0, 0))
    }

}
