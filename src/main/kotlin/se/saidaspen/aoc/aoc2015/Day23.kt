package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.words

fun main() {
    Day23.run()
}

object Day23 : Day(2015, 23) {

    class Computer {
        var registers = mutableMapOf<String, Int>()
        private var pc = 0

        fun run(program: List<Op>) {
            while (pc < program.size) {
                val op = program[pc]
                when (op.type) {
                    "hlf" -> {
                        registers[op.arg1] = registers[op.arg1]!! / 2
                        pc++
                    }
                    "tpl" -> {
                        registers[op.arg1] = registers[op.arg1]!! * 3
                        pc++
                    }
                    "inc" -> {
                        registers[op.arg1] = registers[op.arg1]!! + 1
                        pc++
                    }
                    "jmp" -> {
                        if (op.arg1[0] == '+') pc += op.arg1.drop(1).toInt()
                        else pc -= op.arg1.drop(1).toInt()
                    }
                    "jie" -> {
                        if (registers[op.arg1]!! % 2 == 0) {
                            if (op.arg2[0] == '+') pc += op.arg2.drop(1).toInt()
                            else pc -= op.arg2.drop(1).toInt()
                        } else {
                            pc++
                        }
                    }
                    "jio" -> {
                        if (registers[op.arg1]!! == 1) {
                            if (op.arg2[0] == '+') pc += op.arg2.drop(1).toInt()
                            else pc -= op.arg2.drop(1).toInt()
                        } else {
                            pc++
                        }
                    }
                }
            }

        }
    }

    data class Op(val type: String, val arg1: String, val arg2: String)

    override fun part1(): Any {
        val program = input.lines().map { it.replace(",", "") }.map { words(it) }
            .map { Op(it[0], it[1], if (it.size > 2) it[2] else "") }.toList()
        val cpu = Computer()
        cpu.registers["a"] = 0
        cpu.registers["b"] = 0
        cpu.run(program)
        return cpu.registers["b"]!!
    }

    override fun part2(): Any {
        val program = input.lines().map { it.replace(",", "") }.map { words(it) }
            .map { Op(it[0], it[1], if (it.size > 2) it[2] else "") }.toList()
        val cpu = Computer()
        cpu.registers["a"] = 1
        cpu.registers["b"] = 0
        cpu.run(program)
        return cpu.registers["b"]!!
    }

}
