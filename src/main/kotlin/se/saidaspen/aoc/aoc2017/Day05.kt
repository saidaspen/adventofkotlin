package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() = Day05.run()

object Day05 : Day(2017, 5) {

    override fun part1(): Int {
        val instructions = ints(input).toMutableList()
        var pc = 0
        var steps = 0
        while (pc < instructions.size) {
            val lastPc = pc
            pc += instructions[pc]
            instructions[lastPc] = instructions[lastPc] + 1
            steps++
        }
        return steps
    }

    override fun part2(): Int {
        val instrs = ints(input).toMutableList()
        var pc = 0
        var steps = 0
        while (pc < instrs.size) {
            val lastPc = pc
            val offSet = instrs[pc]
            pc += offSet
            instrs[lastPc] = if (offSet >= 3) instrs[lastPc] - 1 else instrs[lastPc] + 1
            steps++
        }
        return steps
    }
}