package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.*

fun main() = Day15.run()

object Day15 : Day(2021, 15) {

    var map = toMap(input).mapValues { it.value.toString().toInt() }.toMutableMap()

    override fun part1(): Any {
        val start = P(0, 0)
        val end = P(map.entries.map { it.key }.maxOfOrNull { it.first }!!, map.entries.map { it.key }.maxOfOrNull { it.second }!!)
        return djikstraL(start, end)
    }

    private fun djikstraL(start: P<Int, Int>, end: P<Int, Int>): Int {
        // Keep track of cheapest cost to move to any given point
        val costs = mutableMapOf<P<Int, Int>, Int>()
        // Stack of points to visit next together with their cost (to keep the cost here is optimization, to avoid having to do some look-ups in costs map)
        var queue = mutableListOf<P<P<Int, Int>, Int>>()
        costs[start] = 0
        queue.add(P(start, 0))
        while (queue.isNotEmpty()) {
            val (pos, cost) = queue.removeAt(0)
            val adjacentPoints = listOf(pos + P(-1, 0), pos + P(0, -1), pos + P(0, 1), pos + P(1, 0)).filter { map.containsKey(it) }
            for (n in adjacentPoints) {
                val newCost = map[n]!! + cost // The cost of getting to n through the path we currently are investigating
                val oldCost = costs[n]        // Previously cheapest cost to get to n
                // If the path we are on, is more expensive than some prior path to this node, then we can just ignore this path, it will never be the quickest to get here.
                if (oldCost != null && oldCost <= newCost) {
                    continue
                } else {
                    costs[n] = newCost
                    queue.add(P(n, newCost))
                }
                // We sort the queue. We always want to investigate the cheapest first.
                queue = queue.sortedBy { it.second }.toMutableList()
            }
        }
        return costs[end]!!
    }

    override fun part2(): Any {
        val width = map.entries.map { it.key }.maxOfOrNull { it.first }!! +1
        val height = map.entries.map { it.key }.maxOfOrNull { it.second }!!+1
        val newMap = mutableMapOf<P<Int, Int>, Int>()
        for (x in 0 until 5) {
            for (y in 0 until 5) {
                newMap.putAll(map.entries
                    .associate { P(it.key.first + (width * x), it.key.second + (height * y)) to if (it.value + (x+y) > 9) it.value + (x+y) - 9 else it.value + (x+y) })
            }
        }
        map = newMap
        val start = P(0, 0)
        val end = P(map.entries.map { it.key }.maxOfOrNull { it.first }!!, map.entries.map { it.key }.maxOfOrNull { it.second }!!)
        return djikstraL(start, end)
    }

}












