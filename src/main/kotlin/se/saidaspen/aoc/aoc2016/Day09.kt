package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day09.run()

object Day09 : Day(2016, 9) {

    override fun part1(): Any {
        return decompress(input, 1)
    }

    override fun part2(): Any {
        return decompress(input, 2)
    }

    private fun decompress(inp: String, version: Int): Long {
        var length = 0L
        var i = 0
        while (i < inp.length) {
            when (inp[i]) {
                '(' -> {
                    val endIdx = i + inp.drop(i).indexOf(')') + 1
                    val marker = inp.substring(i, endIdx)
                    val repeatChars = ints(marker)[0]
                    val repeats = ints(marker)[1]
                    val repeat = inp.substring(endIdx, endIdx + repeatChars)
                    if (version == 1) {
                        length += repeat.length * repeats
                    } else {
                        length += decompress(repeat, version) * repeats
                    }
                    i = endIdx + repeatChars
                }
                else -> {
                    length+=1
                    i++
                }
            }
        }
        return length
    }

}
