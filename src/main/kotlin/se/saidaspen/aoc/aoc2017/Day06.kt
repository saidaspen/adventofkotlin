package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() = Day06.run()

object Day06 : Day(2017, 6) {

    override fun part1(): Int {
        val register = ints(input).toMutableList()
        val visitedConfigs = HashSet<String>()
        while (true) {
            visitedConfigs.add(register.toString())
            redistribute(register)
            if (visitedConfigs.contains(register.toString())) break
        }
        return visitedConfigs.size
    }

    private fun redistribute(register: MutableList<Int>) {
        var pos = register.withIndex().maxBy { (_, v) -> v }.index
        var bucket = register[pos]
        register[pos] = 0
        while (bucket > 0) {
            pos = (pos + 1) % register.size
            register[pos]++
            bucket--
        }
    }

    override fun part2(): Int {
        val register = ints(input).toMutableList()
        val visitedConfigs = HashSet<String>()
        while (true) {
            visitedConfigs.add(register.toString())
            redistribute(register)
            if (visitedConfigs.contains(register.toString())) break
        }
        var iterations = 0
        val loopStart = register.toString()
        do {
            redistribute(register)
            iterations++
        } while (loopStart != register.toString())
        return iterations
    }
}