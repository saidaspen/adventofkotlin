package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day23.run()

object Day23 : Day(2017, 23) {

    override fun part1(): String {
        val commands = input.lines().map { it.split(" ") }.toList()
        var pc = 0
        val  reg = mutableMapOf<String, Long>().withDefault { 0 }
        var mulInvoked = 0L
        loop@ while (true) {
            if (pc >= commands.size) {
                break@loop
            }
            val cmd = commands[pc][0]
            val op = commands[pc][1]
            when (cmd) {
                "set" -> {
                    reg[op] = deref(commands[pc][2], reg)
                }
                "sub" -> reg[op] = reg.getValue(op) - deref(commands[pc][2], reg)
                "mul" -> {
                    mulInvoked++
                    reg[op] = reg.getValue(op) * deref(commands[pc][2], reg)
                }
                "jnz" -> {
                    if (deref(commands[pc][1], reg) != 0L) {
                        pc += deref(commands[pc][2], reg).toInt()
                        continue@loop
                    }
                }
                else -> throw RuntimeException("Unsupported command $cmd")
            }
            pc += 1
        }
        return mulInvoked.toString()
    }

    /**
     * Simplified, or decompiled, something like this:
    for (b = 105700; b != 122700; b + 17) {
    f = 1
    d = 2
    for (d = 2; d < b; d++) {
    for (e = 2; e < b; e++) {
    if (d * e == b) f = 0
    }
    }
    if f == 0 h++
    }
     */
    override fun part2(): Int {
        var h = 0
        for (b in 105700 until 122700+1 step 17) {
            for (d in 2 until b/2) {
                if (b % d == 0) {
                    h++
                    break
                }
            }
        }
        return h
    }

    private fun deref(op: String, reg: MutableMap<String, Long>): Long {
        return if (op.toLongOrNull() == null) reg.getValue(op) else op.toLong()
    }
}