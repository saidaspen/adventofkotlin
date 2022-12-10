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

fun Pair<Int, Int>.neighbors()  = listOf(
    this + P(-1, -1), this + P(-1, 0), this + P(-1, 1),
    this + P(0, -1), this + P(0, 1),
    this + P(1, -1), this + P(1, 0), this + P(1, 1))

@JvmName("neighborsFun")
fun neighbors(i: P<Int, Int>) = i.neighbors()

fun Pair<Int, Int>.neighborsSimple() = listOf(
    this + P(-1, 0),
    this + P(0, -1),
    this + P(0, 1),
    this + P(1, 0))

@JvmName("neighborsSimpleFun")
fun neighborsSimple(i: P<Int, Int>) = i.neighborsSimple()

fun <T> Map<Point, T>.printArea(visualization: (T) -> Char = { it.toString()[0] }) {
    val xRange = keys.minOf { it.x }..keys.maxOf { it.x }
    val yRange = keys.minOf { it.y }..keys.maxOf { it.y }
    for (y in yRange) {
        for (x in xRange) {
            val value = get(Point(x, y))
            if (value != null) {
                print(visualization(value))
            } else {
                print(" ")
            }
        }
        println()
    }
}

@JvmName("printAreaBoolean")
fun Map<Point, Boolean>.printArea() {
    printArea { if (it) '█' else ' ' }
}

@JvmName("printAreaChar")
fun Map<Point, Char>.printArea() {
    printArea { it }
}


