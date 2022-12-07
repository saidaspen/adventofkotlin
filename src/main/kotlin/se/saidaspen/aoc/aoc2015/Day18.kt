package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.*

fun main() = Day18.run()

object Day18 : Day(2015, 18) {

    override fun part1(): Any {
        var lMap = toMap(input)
        val alwaysOn = listOf<P<Int, Int>>()
        repeat(100) {lMap = switchLights(lMap, alwaysOn)}
        return lMap.values.count { it == '#' }
    }

    override fun part2(): Any {
        var lMap = toMap(input)
        val alwaysOn = listOf(P(0,0), P(0,99), P(99,99), P(99,0))
        repeat(100) {lMap = switchLights(lMap, alwaysOn)}
        return lMap.values.count { it == '#' }
    }

    private fun switchLights(lmap: MutableMap<Pair<Int, Int>, Char>, alwaysOn: List<P<Int, Int>>): MutableMap<Pair<Int, Int>, Char> {
        val map = lmap.toMutableMap()
        val maxVal = 99
        for (col in 0..maxVal) {
            for (row in 0..maxVal) {
                val thisLight = P(col, row)
                if (alwaysOn.contains(thisLight)){
                    map[thisLight] = '#'
                    continue
                }
                val litNeighbors = neighbors(thisLight).filter { it.first in 0..maxVal && it.second in 0..maxVal }.count { lmap[it]!! == '#' }
                if (lmap[thisLight] == '#') {
                    if (litNeighbors == 2 || litNeighbors == 3) {
                        map[thisLight] = '#'
                    } else map[thisLight] = '.'
                } else {
                    if (litNeighbors == 3) {
                        map[thisLight] = '#'
                    }
                }
            }
        }
        return map
    }
}


