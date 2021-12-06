package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.ints
import kotlin.math.sign

fun main() = Day05.run()

object Day05 : Day(2021, 5) {

    val lines = input.lines().map { ints(it) }.map { P(P(it[0], it[1]), P(it[2], it[3])) }

    override fun part1() =
        countCrosses(lines.filter { it.first.first == it.second.first || it.first.second == it.second.second })

    override fun part2() = countCrosses(lines)

    private fun countCrosses(lines: List<P<P<Int, Int>, P<Int, Int>>>) =
        lines.flatMap { toPoints(it) }.groupingBy { it }.eachCount().count { it.value >= 2 }

    private fun toPoints(l: Pair<Pair<Int, Int>, Pair<Int, Int>>): List<P<Int, Int>> {
        val list = mutableListOf<P<Int, Int>>()
        var curr = l.first
        val dx = (l.second.first - l.first.first).sign
        val dy = (l.second.second - l.first.second).sign
        while (curr != l.second) {
            list.add(curr)
            curr = P(curr.first + dx, curr.second + dy)
        }
        list.add(l.second)
        return list
    }
}
