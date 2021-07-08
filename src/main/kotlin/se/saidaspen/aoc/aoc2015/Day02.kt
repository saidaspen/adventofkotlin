package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() {
    Day02.run()
}

object Day02 : Day(2015, 2) {

    override fun part1() = input.lines().map { ints(it) }.sumBy { surfaceArea(it) + extra(it) }
    private fun extra(inp: List<Int>) = (inp[0] * inp[1]).coerceAtMost((inp[1] * inp[2]).coerceAtMost(inp[0] * inp[2]))
    private fun surfaceArea(inp: List<Int>) = 2 * inp[0] * inp[1] + 2 * inp[0] * inp[2] + 2 * inp[1] * inp[2]
    override fun part2() = input.lines().map { ints(it) }.sumBy { ribbon(it) + bow(it) }
    private fun bow(inp: List<Int>) = inp[0] * inp[1] * inp[2]
    private fun ribbon(inp: List<Int>) =
        (2 * inp[0] + 2 * inp[1]).coerceAtMost((2 * inp[1] + 2 * inp[2]).coerceAtMost(2 * inp[0] + 2 * inp[2]))

}
