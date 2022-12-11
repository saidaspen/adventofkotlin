package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.pow

fun main() = Day03.run()

object Day03 : Day(2017, 3) {
    private val directions = arrayOf(Pair(0, 1), Pair(-1, 0), Pair(0, -1), Pair(1, 0))

    override fun part1(): Any {
        val target = input.toInt()
        var sideLen = 1
        while (sideLen.toDouble().pow(2) < target) sideLen += 2
        val corner = sideLen.toDouble().pow(2).toInt()
        val steps = corner - target
        val arm = floor(steps.toDouble() / (sideLen - 1).toDouble()).toInt()
        val midOnArm = (corner - (sideLen - 1) / 2) - ((sideLen - 1) * arm)
        val distanceToMid = (midOnArm - target).absoluteValue
        val distanceToLevel = ((sideLen - 1) / 2)
        return distanceToLevel + distanceToMid
    }

    override fun part2(): Any {
        val target = input.toInt()
        val map = HashMap<Pair<Int, Int>, Int>().withDefault { 0 }
        var sideLen = 1
        var currPos = Pair(0, 0)
        var step = 0
        var direction = 0
        while (true) {
            val value = valueOf(currPos, map)
            if (value > target) return value
            map[currPos] = value
            if (step == sideLen.toDouble().pow(2).toInt() - sideLen.minus(2).toDouble().pow(2).toInt()) {
                currPos = Pair(currPos.first + 1, currPos.second)
                sideLen += 2
                direction = 0
                step = 1
            } else {
                if (step % (sideLen - 1) == 0) direction = (direction + 1) % directions.size
                currPos = Pair(currPos.first + directions[direction].first, currPos.second + directions[direction].second)
                step++
            }
        }
    }

    // Get all the neighbouring cells values, default value is 0 if no value exist
    private fun valueOf(pos: Pair<Int, Int>, map: Map<Pair<Int, Int>, Int>): Int {
        val value = map.getValue(Pair(pos.first, pos.second + 1)) +
                map.getValue(Pair(pos.first, pos.second - 1)) +
                map.getValue(Pair(pos.first - 1, pos.second + 1)) +
                map.getValue(Pair(pos.first - 1, pos.second - 1)) +
                map.getValue(Pair(pos.first - 1, pos.second)) +
                map.getValue(Pair(pos.first + 1, pos.second + 1)) +
                map.getValue(Pair(pos.first + 1, pos.second - 1)) +
                map.getValue(Pair(pos.first + 1, pos.second))
        return if (value > 0) value else 1
    }
}