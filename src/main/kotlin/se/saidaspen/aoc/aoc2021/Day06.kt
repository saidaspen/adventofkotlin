package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.rotate
import java.util.*
import java.util.Collections.rotate

fun main() = Day06.run()

object Day06 : Day(2021, 6) {

    private val fish = ints(input).toMutableList()
    override fun part1() = simulateFish2(fish, 80)
    override fun part2() = simulateFish2(fish, 256)

    private fun simulateFish2(fish: MutableList<Int>, days: Int): Long {
        val list = MutableList<Long>(9){0}
        fish.forEach { list[it]++ }
        repeat(days){
            list.rotate(-1)
            list[6] += list[8]
        }
        return list.sum()
    }


    // Original solution
    private fun simulateFish(
        fish: MutableList<Int>,
        days: Int
    ): Long {
        var fishCount = fish.groupingBy { it }.eachCount().map { it.key to it.value.toLong() }.toMap().toMutableMap()
        for (d in 0 until days) {
            val newFish = mutableMapOf<Int, Long>().withDefault { 0 }
            for ((age, cnt) in fishCount.entries) {
                if (age == 0) {
                    newFish[6] = newFish.getValue(6) + cnt
                    newFish[8] = newFish.getValue(8) + cnt
                } else {
                    newFish[age - 1] = newFish.getValue(age - 1) + cnt
                }
            }
            fishCount = newFish
        }
        return fishCount.values.sum()
    }
}




