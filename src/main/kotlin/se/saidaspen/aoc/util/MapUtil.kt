@file:Suppress("unused")

package se.saidaspen.aoc.util

import java.util.*
import kotlin.math.max

fun toMapInt(input: String): MutableMap<P<Int, Int>, Int> {
    val lines = input.lines()
    val map = mutableMapOf<P<Int, Int>, Int>()
    for (line in lines.indices) {
        val lineChars = lines[line].toCharArray()
        for (col in lineChars.indices) {
            map[P(col, line)] = lineChars[col].toString().toInt()
        }
    }
    return map
}

fun toMap(input: String): MutableMap<P<Int, Int>, Char> {
    val lines = input.lines()
    val map = mutableMapOf<P<Int, Int>, Char>()
    for (line in lines.indices) {
        val lineChars = lines[line].toCharArray()
        for (col in lineChars.indices) {
            map[P(col, line)] = lineChars[col]
        }
    }
    return map
}

fun neighbors(i: P<Int, Int>) = listOf(
        i + P(-1, -1), i + P(-1, 0), i + P(-1, 1),
        i + P(0, -1), i + P(0, 1),
        i + P(1, -1), i + P(1, 0), i + P(1, 1))

fun neighborsSimple(i: P<Int, Int>) = listOf(
    i + P(-1, 0),
    i + P(0, -1),
    i + P(0, 1),
    i + P(1, 0))

fun printMap(map: Map<P<Int, Int>, Char?>) {
    print("\u001b[2J") // Clear screen
    var largestX = 0
    var largestY = 0
    for (p in map.keys) {
        largestX = max(p.x, largestX)
        largestY = max(p.y, largestY)
    }
    val lines = emptyLines(largestX, largestY)
    for (p in map.keys) lines[p.y][p.x] = map[p]
    render(lines)
}

private fun emptyLines(xMax: Int, yMax: Int): List<MutableList<Char?>> {
    val lines: MutableList<MutableList<Char?>> = ArrayList()
    for (row in 0..yMax) {
        val r: MutableList<Char?> = ArrayList()
        for (col in 0..xMax) r.add(' ')
        lines.add(r)
    }
    return lines
}

private fun render(lines: List<MutableList<Char?>>) {
    for (row in lines) {
        val sb = StringBuilder()
        for (c in row) sb.append(c)
        println(sb)
    }
}
