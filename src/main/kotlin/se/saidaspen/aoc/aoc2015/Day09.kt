package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.words

fun main() = Day09.run()

object Day09 : Day(2015, 9) {

    private val graph = mutableMapOf<P<String, String>, Int>()
    private val cities = mutableListOf<String>()

    init {
        input.lines().map {
            val (f, _, t, _, dist) = words(it)
            graph[P(f, t)] = dist.toInt()
            graph[P(t, f)] = dist.toInt()
        }
        cities.addAll(graph.keys.flatMap { mutableListOf(it.first, it.second) }.distinct())
    }


    override fun part1(): Any {
        return cities.minOfOrNull { minDistance(graph, mutableListOf(), it) }!!
    }

    override fun part2(): Any {
        return cities.maxOfOrNull { maxDistance(graph, mutableListOf(), it) }!!
    }

    private fun minDistance(graph: MutableMap<Pair<String, String>, Int>, visited : List<String>, current: String): Int {
        val nextVisited = mutableListOf(current)
        nextVisited.addAll(visited)
        return if (nextVisited.size == cities.size) 0
        else {
            val toVisit = graph.filter { it.key.first == current }.filter { !visited.contains(it.key.second) }
            if (toVisit.isEmpty()) Int.MAX_VALUE
            else {
                toVisit.map {
                    val dist = minDistance(graph, nextVisited, it.key.second)
                    if (dist == Int.MAX_VALUE) Int.MAX_VALUE else dist + it.value
                }.min()
            }
        }
    }

    private fun maxDistance(graph: MutableMap<Pair<String, String>, Int>, visited : List<String>, current: String): Int {
        val nextVisited = mutableListOf(current)
        nextVisited.addAll(visited)
        return if (nextVisited.size == cities.size) 0
        else {
            val toVisit = graph.filter { it.key.first == current }.filter { !visited.contains(it.key.second) }
            if (toVisit.isEmpty()) Int.MIN_VALUE
            else {
                toVisit.map {
                    val dist = maxDistance(graph, nextVisited, it.key.second)
                    if (dist == Int.MIN_VALUE) Int.MIN_VALUE else dist + it.value
                }.max()
            }
        }
    }
}


