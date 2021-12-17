package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.*

fun main() = Day17.run()

object Day17 : Day(2021, 17) {

    var ints = ints(input)
    private var xRange = IntRange(ints[0], ints[1])
    private var yRange = IntRange(ints[2], ints[3])

    override fun part1() =  (0..250).flatMap { x -> (-250..500).map { y-> P(x, y) }}.map { shoot(it) }.filter { it.first }.maxOf { it.second }
    override fun part2()=  (0..250).flatMap { x -> (-250..500).map { y-> P(x, y) }}.map { shoot(it) }.count { it.first }

    private fun shoot(vStart: Pair<Int, Int>) : Pair<Boolean, Int> {
        var pos = P(0,0)
        var v = vStart
        var maxHeight = Int.MIN_VALUE
        val yMin = yRange.minOrNull()!!
        while(pos.y > yMin) {
            pos += v
            maxHeight = maxHeight.coerceAtLeast(pos.y)
            if (pos.first in xRange && pos.second in yRange) {
                return P(true, maxHeight)
            }
            v = P(if (v.first > 0) v.first-1 else if (v.first< 0) v.first+1 else 0, v.second-1)
        }
        return P(false, maxHeight)
    }
}





