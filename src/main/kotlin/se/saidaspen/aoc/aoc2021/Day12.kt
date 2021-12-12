package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day

fun main() = Day12.run()

private const val VST = "visitedSmallCaveOnce"

object Day12 : Day(2021, 12) {

    override fun part1(): Any {
        val paths = input.lines()
            .flatMap { listOf(it.split("-")[0] to it.split("-")[1], it.split("-")[1] to it.split("-")[0]) }
            .groupBy { it.first }
            .mapValues { it.value.map { li -> li.second } }
            .toMap()

        val queue = mutableListOf<List<String>>()
        queue.add(listOf("start"))
        var fullPaths = 0
        while (queue.isNotEmpty()) {
            val curr = queue.removeAt(0)
            if (curr.last() == "end") {
                fullPaths++
                continue
            }
            val candidates =
                paths[curr.last()]!!.filter { it == it.toUpperCase() || !curr.contains(it) }.toMutableList()
            queue.addAll(candidates.map {
                val neighbour = curr.toMutableList()
                neighbour.add(it)
                neighbour
            })
        }
        return fullPaths
    }

    override fun part2(): Any {
        val paths = input.lines()
            .flatMap { listOf(it.split("-")[0] to it.split("-")[1], it.split("-")[1] to it.split("-")[0]) }
            .groupBy { it.first }
            .mapValues { it.value.map { li -> li.second } }
            .toMap()

        val queue = mutableListOf<List<String>>()
        queue.add(listOf("start"))

        var fullPaths = 0

        while (queue.isNotEmpty()) {
            val curr = queue.removeAt(0)
            if (curr.last() == "end") {
                fullPaths++
                continue
            }
            val candidates = paths[curr.last()]!!.filter { it != "start" }.filter {
                it == it.toUpperCase()
                        || (!curr.contains(it))
                        || (curr.count { n -> n == it } == 1 && curr[0] == "start")
            }
            val neighbours = candidates.map {
                val neighbour = curr.toMutableList()
                neighbour.add(it)
                if (neighbour[0] == "start" && neighbour.filter { n -> n != n.toUpperCase() }.groupingBy { n -> n }
                        .eachCount()
                        .map { n -> n.value }.any { n -> n == 2 }) {
                    neighbour.add(0, VST)
                }
                neighbour
            }
            queue.addAll(neighbours)
        }
        return fullPaths
    }
}












