package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day25.run()

object Day25 : Day(2016, 25) {

    override fun part1(): Any {
        var i = 0
        while(true) {
            if (i % 1000 == 0){
                print("Testing $i. ")
            }
            val cpu = Computer()
            cpu.regs["a"] = i
            val result = cpu.run()
            if (result) return i
            i++
        }
    }

    override fun part2(): Any {
        return ""
    }

    private class Computer {
        var pc = 0
        val regs = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
        fun valueOf(operand: String) = if (operand.toIntOrNull() != null) operand.toInt() else regs[operand]!!
        val program = input.lines().toMutableList()
        fun run(): Boolean {
            var outs = 0
            var start = 0
            while (pc < program.size) {
                val tokens = program[pc].split(" ")
                val op = tokens[0]
                val operand1 = tokens[1]
                val operand2 = if (tokens.size>2) tokens[2] else null
                when(op) {
                    "out" -> {
                        val v = valueOf(operand1)
                        if (v != 0 && v != 1) return false
                        if (outs == 0) {
                            start = v
                        } else if ((outs + start) % 2 != v) {
                            return false
                        }
                        outs ++
                        if (outs > 10000) {
                            return true
                        }
                    }
                    "cpy" -> {
                            if (regs.keys.contains(operand2))
                                regs[operand2!!] = valueOf(operand1)
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
            return true
        }
    }
}
