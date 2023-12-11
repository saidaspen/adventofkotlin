package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day08.run()

object Day08 : Day(2023, 8) {

    private var map = input.lines().drop(2)
        .map { words(it.replaceAll("(),=", "")) }
        .associate { it[0] to P(it[1], it[2]) }

    private val instr = input.lines()[0].e()

    override fun part1() = solve("AAA", { c -> c == "ZZZ" })
    override fun part2() = map.keys
        .filter { it.endsWith("A") }
        .fold(1L) { acc, op -> lcm(acc, solve(op, { c -> c.endsWith("Z") })) }

    private fun solve(start: String, endCondition: (String) -> Boolean): Long {
        var curr = start
        var i = 0
        while (!endCondition.invoke(curr)) {
            val c = instr[i % instr.size]
            curr = if (c == 'R') map[curr]!!.second else map[curr]!!.first
            i += 1
        }
        return i.toLong()
    }
}

