package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.removeFirst

fun main() = Day05.run()

object Day05 : Day(2022, 5) {

    override fun part1(): Any {
        val stacks = mutableMapOf<Int, ArrayDeque<Char>>().withDefault { ArrayDeque() }
        input.split("\n\n")[0].dropLast(1).lines().forEach {
            var i = 0
            while (i * 4 + 1 < it.length) {
                val stack = stacks.getValue(i + 1)
                val c = it[1 + i * 4]
                if (c.isLetter()) stack.addLast(c)
                stacks[i + 1] = stack
                i += 1
            }
        }
        val instructions = input.split("\n\n")[1].lines()
        for (l in instructions) {
            val (n, f, t) = ints(l)
            repeat(n) {
                val elem = stacks[f]!!.removeFirst()
                stacks[t]!!.addFirst(elem)
            }
        }
        return stacks.values.map { it.first() }.joinToString("")
    }

    override fun part2(): Any {
        val stacks = mutableMapOf<Int, ArrayDeque<Char>>().withDefault { ArrayDeque() }
        input.split("\n\n")[0].dropLast(1).lines().forEach {
            var i = 0
            while (i * 4 + 1 < it.length) {
                val stack = stacks.getValue(i + 1)
                val c = it[1 + i * 4]
                if (c.isLetter()) stack.addLast(c)
                stacks[i + 1] = stack
                i += 1
            }
        }
        val instructions = input.split("\n\n")[1].lines()
        for (l in instructions) {
            val (n, f, t) = ints(l)
            val moved = stacks[f]!!.removeFirst(n).reversed()
            for (elem in moved) {
                stacks[t]!!.addFirst(elem)
            }

        }
        return stacks.values.map { it.first() }.joinToString("")
    }
}






