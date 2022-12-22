package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.words
import java.math.BigDecimal
import java.math.BigDecimal.ONE

fun main() = Day21.run()

object Day21 : Day(2022, 21) {
    private var monkeys = input.lines().map { it.split(":").map { t -> t.trim() } }.associate { it[0] to it[1] }.toMutableMap()

    override fun part1() =  getValue("root")

    override fun part2(): Any {
        var currentGuess = ONE
        var switch = false
        var lastDiff = BigDecimal.ZERO
        val (op1, _, op2) = words(monkeys["root"]!!)
        while (true) {
            monkeys["humn"] = currentGuess.toString()
            val dist = getValue(op1) - getValue(op2)
            // Change direction if we are going in the wrong direction
            if (dist.abs() > lastDiff.abs() && currentGuess != BigDecimal.ZERO) switch = !switch
            if (dist.abs() < BigDecimal.valueOf(0.1)) return currentGuess
            var change = (dist.abs() / BigDecimal.valueOf(2)).coerceAtLeast(ONE)
            change *= if (dist < ONE) -ONE else ONE
            change *= if (switch) -ONE else ONE
            currentGuess += change
            lastDiff = dist
        }
    }
    private fun getValue(m: String): BigDecimal {
        val monkeyDo = monkeys[m]!!
        return if (monkeyDo.toBigDecimalOrNull() != null) {
            monkeyDo.toBigDecimal()
        } else {
            val (op1, oper, op2) = words(monkeyDo)
            when (oper) {
                "+" -> getValue(op1) + getValue(op2)
                "-" -> getValue(op1) - getValue(op2)
                "*" -> getValue(op1) * getValue(op2)
                "/" -> getValue(op1) / getValue(op2)
                else -> throw RuntimeException("Unsupported op")
            }
        }
    }
}