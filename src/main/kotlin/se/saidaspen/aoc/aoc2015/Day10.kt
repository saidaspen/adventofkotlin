package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day

fun main() = Day10.run()

object Day10 : Day(2015, 10) {

    override fun part1()= process(40, input).length

    override fun part2()= process(50, input).length

    fun consecutiveGroups(value: String): List<String> {
        val groups = mutableListOf<String>()
        var curr = ""
        for (c in value.toCharArray()) {
            if (curr.isEmpty() || c == curr.last()) curr += c
            else {
                groups.add(curr)
                curr = c.toString()
            }
        }
        if (curr.isNotEmpty()) groups.add(curr)
        return groups
    }

    private fun process(iterations: Int, inp : String) : String {
        var curr = inp
        for (i in 1..iterations) {
            val builder = StringBuilder()
            consecutiveGroups(curr).map { it.length.toString() + it[0] }.forEach { builder.append(it) }
            curr = builder.toString()
        }
        return curr
    }
}


