package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*
import java.lang.StringBuilder

fun main() = Day10.run()

object Day10 : Day(2016, 10) {

    data class Bot(val nr: Int, var low: Bot?, var high: Bot?) {
        val chips: MutableList<Int> = mutableListOf()

        fun receive(value: Int) {
            chips.add(value)
        }
    }

    override fun part1(): Any {
        val bots = mutableMapOf<Int, Bot>()
        val outputs = mutableMapOf<Int, Bot>()
        input.lines()
            .flatMap {
                    l -> """bot (\d+)""".toRegex().findAll(l)
                            .map { it.groupValues[1] }
                            .distinct()
                            .map { it.toInt() }
            }
            .distinct()
            .map {
                Bot(it, null, null)
            }
            .forEach {
                bots[it.nr] = it
            }
        input.lines().flatMap { l -> """output (\d+)""".toRegex().findAll(l).map { it.groupValues[1] }.distinct().map { it.toInt() } }.distinct().map { Bot(it, null, null) }.forEach { outputs[it.nr] = it }

        input.lines().filter { !it.contains("goes to") }.forEach {
            val bot = ints(it)[0]
            val lowType = it.split(" ")[5]
            val lowTarget = ints(it)[1]
            val lowReceiver = if (lowType == "bot") bots[lowTarget] else outputs[lowTarget]!!
            val highType = it.split(" ")[10]
            val highTarget = ints(it)[2]
            val highReceiver = if (highType == "bot") bots[highTarget] else outputs[highTarget]!!
            bots[bot]!!.low = lowReceiver
            bots[bot]!!.high = highReceiver
        }

        input.lines().filter { it.contains("goes to") }.forEach {
            val value = ints(it)[0]
            val bot = ints(it)[1]
            bots[bot]!!.receive(value)
        }

        while(bots.values.any { it.chips.size == 2 }) {
            val giver = bots.values.first{ it.chips.size == 2}
            val lowVal = giver.chips.minOrNull()!!
            val highVal = giver.chips.maxOrNull()!!
            if (lowVal == 17 && highVal == 61) {
                println("Bot ${giver.nr} is responsible for comparing value-61 microchips with value-17 microchips")
                return giver.nr
            }
            giver.low!!.receive(lowVal)
            giver.high!!.receive(highVal)
            giver.chips.clear()
        }
        return ""
    }

    override fun part2(): Any {
        val bots = mutableMapOf<Int, Bot>()
        val outputs = mutableMapOf<Int, Bot>()
        input.lines().flatMap { l -> """bot (\d+)""".toRegex().findAll(l).map { it.groupValues[1] }.distinct().map { it.toInt() } }.distinct().map { Bot(it, null, null) }.forEach { bots[it.nr] = it }
        input.lines().flatMap { l -> """output (\d+)""".toRegex().findAll(l).map { it.groupValues[1] }.distinct().map { it.toInt() } }.distinct().map { Bot(it, null, null) }.forEach { outputs[it.nr] = it }

        input.lines().filter { !it.contains("goes to") }.forEach {
            val bot = ints(it)[0]
            val lowType = it.split(" ")[5]
            val lowTarget = ints(it)[1]
            val lowReceiver = if (lowType == "bot") bots[lowTarget] else outputs[lowTarget]!!
            val highType = it.split(" ")[10]
            val highTarget = ints(it)[2]
            val highReceiver = if (highType == "bot") bots[highTarget] else outputs[highTarget]!!
            bots[bot]!!.low = lowReceiver
            bots[bot]!!.high = highReceiver
        }

        input.lines().filter { it.contains("goes to") }.forEach {
            val value = ints(it)[0]
            val bot = ints(it)[1]
            bots[bot]!!.receive(value)
        }

        while(bots.values.any { it.chips.size == 2 }) {
            val giver = bots.values.first{ it.chips.size == 2}
            val lowVal = giver.chips.minOrNull()!!
            val highVal = giver.chips.maxOrNull()!!
            giver.low!!.receive(lowVal)
            giver.high!!.receive(highVal)
            giver.chips.clear()
        }

        return outputs[0]!!.chips[0] * outputs[1]!!.chips[0] * outputs[2]!!.chips[0]
    }
}
