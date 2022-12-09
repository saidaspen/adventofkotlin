package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.*

fun main() = Day09.run()

object Day09 : Day(2021, 9) {

    override fun part1() : Any {
        val map = toMap(input)
        val lowpoints = map.keys.filter { p ->  (p.neighborsSimple().mapNotNull { map[it] }.all { it.toString().toInt() > map[p].toString().toInt() })}
        return lowpoints.sumOf {map[it].toString().toInt() + 1  }
    }

    override fun part2() : Any {
        val map = toMap(input)
        val flowsMap = map.keys.map { P(it, flowsTo2(it, map)) }.filter { it.second != null }.toMap()
        val toLowPoints = flowsMap.keys.map { findLp(it, flowsMap) }
        val basins = toLowPoints.groupBy { it.second }
        val topThree = basins.entries.map { it.value.size }.sortedByDescending { it }.take(3)
        return topThree[0] * topThree[1] * topThree[2]
    }

    private fun findLp(it: P<Int, Int>, flowsMap: Map<P<Int, Int>, Pair<Int, Int>?>): P<P<Int, Int>, P<Int, Int>> {
        var p = it
        while (flowsMap.containsKey(p)) {
            val n = flowsMap[p]!!
            if (p == n) {
                return P(it, p)
            }
            p = n
        }
        return P(it, p)
    }

    private fun flowsTo2(p: Pair<Int, Int>, map: MutableMap<Pair<Int, Int>, Char>): Pair<Int, Int>? {
        if (map[p].toString().toInt() == 9) {
            return null
        }
        val lowestN =  p.neighborsSimple()
            .filter { map[it] != null }
            .filter { it.first >= 0 && it.second >= 0 }
            .filter {  map[it].toString().toInt() != 9 }
            .minByOrNull { map[it].toString().toInt() }
        return if (map[lowestN].toString().toInt() < map[p].toString().toInt()) lowestN else p
    }
}
