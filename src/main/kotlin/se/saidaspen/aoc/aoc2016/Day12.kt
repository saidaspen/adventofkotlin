package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.bfs
import se.saidaspen.aoc.util.pairWise

fun main() = Day12.run()

object Day12 : Day(2016, 12) {

    private class Computer {
        var pc = 0
        val regs = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
        fun valueOf(operand: String) = if (operand.toIntOrNull() != null) operand.toInt() else regs[operand]!!
        val program = input.lines()
        fun run(): Any {
            while (pc < program.size) {
                val tokens = program[pc].split(" ")
                val op = tokens[0]
                val operand1 = tokens[1]
                val operand2 = if (tokens.size>2) tokens[2] else null
                when(op) {
                    "cpy" -> {
                        regs[operand2!!] = valueOf(operand1)
                    }
                    "jnz" -> {
                        if (valueOf(operand1) != 0) {
                            pc += valueOf(operand2!!) - 1
                        }
                    }
                    "inc" -> {
                        regs[operand1] = regs[operand1]!! + 1
                    }
                    "dec" -> {
                        regs[operand1] = regs[operand1]!! - 1
                    }
                    else -> throw RuntimeException("Unsupported")
                }
                pc ++
            }
            return regs["a"]!!
        }
    }

    override fun part1(): Any {
        return Computer().run()
    }

    override fun part2(): Any {
        val c = Computer()
        c.regs["c"] = 1
        return c.run()
    }
}
