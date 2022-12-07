package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.ints

fun main() = Day14.run()

object Day14 : Day(2015, 14) {

    data class Reindeer(val name: String, val maxSpeed: Int, val stamina: Int, val rest: Int) {
        fun distAt(i: Int): Int {
            val sprints = i / (stamina + rest)
            val rem = i.mod(stamina + rest)
            val stub = if (rem < stamina) rem else stamina
            return ((sprints * stamina) + stub) * maxSpeed
        }
    }

    private val inp = input.lines().map {
        val name = it.split(" ")[0]
        val nums = ints(it)
        Reindeer(name, nums[0], nums[1], nums[2])
    }.toList()

    override fun part1(): Any {
        return inp.maxOf { it.distAt(2503) }
    }

    override fun part2(): Any {
        val points = mutableMapOf<String, Int>()
        for (t in 0 until 2503) {
            val winner = inp.map { P(it.name, it.distAt(t)) }.maxByOrNull { it.second }!!.first
            points.compute(winner) { _, v -> if (v == null) 0 else v + 1 }
        }
        return points.values.maxOrNull()!!
    }
}


