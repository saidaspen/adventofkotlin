package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() = Day06.run()

object Day06 : Day(2021, 6) {

    override fun part1() = simulateFish(ints(input).toMutableList(), 80).values.sum()
    override fun part2() = simulateFish(ints(input).toMutableList(), 256).values.sum()

    private fun simulateFish(
        fish: MutableList<Int>,
        days: Int
    ): MutableMap<Int, Long> {
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
        return fishCount
    }
}
