package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*
import kotlin.math.abs

fun main() = Day23.run()

object Day23 : Day(2016, 23) {

    private class Computer {
        var pc = 0
        val regs = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
        fun valueOf(operand: String) = if (operand.toIntOrNull() != null) operand.toInt() else regs[operand]!!
        val program = input.lines().toMutableList()
        fun run(): Any {
            while (pc < program.size) {
                val tokens = program[pc].split(" ")
                val op = tokens[0]
                val operand1 = tokens[1]
                val operand2 = if (tokens.size>2) tokens[2] else null
                when(op) {
                    "tgl" -> {
                        val instrToToggle = valueOf(operand1)
                        toggle(pc+instrToToggle)
                    }
                    "cpy" -> {
                        //Hack optimization
                        // Finding the looping instructions:
                        //4 cpy b c
                        //5 inc a
                        //6 dec c
                        //7 jnz c -2
                        //8 dec d
                        //9 jnz d -5
                        // Replace them with mult
                        if (pc == 4) {
                            val increment = regs["d"]!! * regs["b"]!!
                            regs["a"] = regs["a"]!! + increment
                            regs["d"] = 0
                            regs["c"] = 0
                            pc = 10
                            continue
                        } else {
                            if (regs.keys.contains(operand2))
                                regs[operand2!!] = valueOf(operand1)
                        }
                    }
                    "jnz" -> {
                        if (valueOf(operand1) != 0) {
                            pc += valueOf(operand2!!) - 1
                        }
                    }
                    "inc" -> {
                        if (regs.keys.contains(operand1))
                            regs[operand1] = regs[operand1]!! + 1
                    }
                    "dec" -> {
                        if (regs.keys.contains(operand1))
                            regs[operand1] = regs[operand1]!! - 1
                    }
                    else -> throw RuntimeException("Unsupported")
                }
                pc ++
            }
            return regs["a"]!!
        }

        private fun toggle(i: Int) {
            if (i >= program.size) return
            val instr = program[i]
            val args = instr.split(" ").toMutableList()
            if (args.size == 2) {
                if (args[0] == "inc") {
                    args[0] = "dec"
                } else {
                    args[0] = "inc"
                }
                program[i] = args.joinToString(" ")
            } else {
                if (args[0] == "jnz") {
                    args[0] = "cpy"
                } else {
                    args[0] = "jnz"
                }
                program[i] = args.joinToString(" ")
            }

        }
    }

    override fun part1(): Any {
        val cpu = Computer()
        cpu.regs["a"] = 7
        cpu.run()
        return cpu.regs["a"]!!
    }

    override fun part2(): Any {
        val cpu = Computer()
        cpu.regs["a"] =12
        cpu.run()
        return cpu.regs["a"]!!
    }
}
