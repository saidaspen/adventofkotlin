package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.permutations

fun main() = Day13.run()

object Day13 : Day(2015, 13) {

    private val inpMap = input.lines().associate {
        val split = it.split(" ")
        val points = if (split[2] == "gain") split[3].toInt() else -split[3].toInt()
        val to = split[10].replace(".", "")
        P(split[0] + "-" + to, points)
    }

    override fun part1(): Any {
        val participants = inpMap.flatMap { it.key.split("-").toList() }.distinct().toList()
        return participants.permutations().map { toHappiness(it) }.maxOrNull()!!
    }

    override fun part2(): Any {
        val participants = inpMap.flatMap { it.key.split("-").toList() }.distinct().toMutableList()
        participants.add("self")
        return participants.permutations().map { toHappiness(it) }.maxOrNull()!!
    }

    private fun toHappiness(conf: List<String>) : Int {
        var sum = 0
        for (i in conf.indices){
            val left =  conf[i] + "-" + conf[(i - 1).mod(conf.size)]
            val right = conf[i] + "-" + conf[(i + 1).mod(conf.size)]
            sum += if(left.contains("self")) 0 else inpMap[left]!!
            sum += if(right.contains("self")) 0 else  inpMap[right]!!
        }
        return sum
    }
}


