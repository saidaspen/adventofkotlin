package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day02.run()

object Day02 : Day(2016, 2) {

    override fun part1(): Any {
        return findCode(P(1, 1), input, toMap("123\n456\n789"))
    }

    override fun part2(): Any {
        return findCode(P(0, 2), input, toMap("  1  \n 234 \n56789\n ABC \n  D  \n"))
    }

    private fun findCode(start: P<Int, Int>, input: String, keyPad: Map<P<Int, Int>, Char>): String {
        var pos = start
        var code = ""
        for (line in input.lines().filter { it.isNotEmpty() }) {
            pos = keyPress(line, pos, keyPad)
            code += keyPad[pos]
        }
        return code
    }

    private fun keyPress(instr: String, start: P<Int, Int>, keyPad: Map<P<Int, Int>, Char>): P<Int, Int> {
        var pos = start
        for (c in instr.toCharArray()) {
            val nextPos = when (c) {
                'U' -> pos + P(0, -1)
                'D' -> pos +P(0, 1)
                'L' -> pos +P(-1, 0)
                'R' -> pos +P(1, 0)
                else -> throw RuntimeException("Unsupported move $c")
            }
            if (keyPad[nextPos] != null && keyPad[nextPos] != ' ') pos = nextPos
        }
        return pos
    }
}
