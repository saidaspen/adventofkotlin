package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.*

fun main() {
    Day07.run()
}

object Day07 : Day(2018, 7) {

    override fun part1(): Any {
        val dependencies = input.lines().filter { it.isNotBlank() }.map { P(it.split(" ")[1], it.split(" ")[7]) }.toList()
        val map = dependencies.map { mutableListOf(it.first, it.second) }.flatten().distinct().map { P(it, mutableListOf<String>()) }.toMap().toMutableMap()
        val visited = mutableListOf<String>()
        for (d in dependencies) {
            val children = map.getOrPut(d.first) {mutableListOf()}
            children.add(d.second)
        }

        while(map.isNotEmpty()) {
            val available = map.keys.filter { !map.values.flatten().contains(it) }.sorted()
            val visit = available.first()
            visited.add(visit)
            map.remove(visit)
        }
        return visited.joinToString("")
    }

    override fun part2(): Any {
        val dependencies = input.lines().filter { it.isNotBlank() }.map { P(it.split(" ")[1], it.split(" ")[7]) }.toList()
        val map = dependencies.map { mutableListOf(it.first, it.second) }.flatten().distinct().map { P(it, mutableListOf<String>()) }.toMap().toMutableMap()
        for (d in dependencies) {
            val children = map.getOrPut(d.first) {mutableListOf()}
            children.add(d.second)
        }
        var t = -1
        val workers = mutableMapOf<String, Int>()
        while(map.isNotEmpty() || workers.isNotEmpty()) {
            t++
            val finished = workers.toList().filter { it.second == t }.map { it.first }
            finished.forEach{
                workers.remove(it)
                map.remove(it)
            }
            val available = map.keys.filter { !map.values.flatten().contains(it) }.sorted().toMutableList()
            workers.toList().map { it.first }.forEach{available.remove(it)}
            val availableWorkers = 5 - workers.size
            val visit = available.take(availableWorkers)
            for (n in visit) {
                workers[n] = t + (n.toCharArray().first() - 'A') + 1 + 60
            }
        }
        return t
    }

}
