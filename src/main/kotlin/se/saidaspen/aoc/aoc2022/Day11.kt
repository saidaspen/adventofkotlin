package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*
import java.math.BigInteger

fun main() = Day11.run()

data class Monkey(val items: MutableList<BigInteger>, val op: (BigInteger) -> BigInteger, val divisor : BigInteger, val throwTo: (BigInteger) -> Int, var inspections: BigInteger = BigInteger.ZERO)

object Day11 : Day(2022, 11) {

    override fun part1(): Any {
        val monkeys = parseMonkeys()
        for (round in 1..20) {
            for (m in monkeys){
                while (m.items.isNotEmpty()) {
                    m.inspections += BigInteger.ONE
                    val old =  m.items.removeAt(0)
                    val new = m.op(old) / BigInteger.valueOf(3)
                    monkeys[m.throwTo(new)].items.add(new)
                }
            }
        }
        return monkeys.map { it.inspections }.sortedByDescending { it }.take(2).reduce { acc, i -> acc * i }
    }

    override fun part2(): Any {
        val monkeys = parseMonkeys()
        val modBy = monkeys.map { it.divisor }.reduce { a, b -> a * b }
        for (round in 1..10_000) {
            for (m in monkeys){
                while (m.items.isNotEmpty()) {
                    m.inspections += BigInteger.ONE
                    val old =  m.items.removeAt(0)
                    val new = m.op(old) % modBy
                    monkeys[m.throwTo(new)].items.add(new)
                }
            }
        }
        return monkeys.map { it.inspections }.sortedByDescending { it }.take(2).reduce { acc, i -> acc * i }
    }

    private fun parseMonkeys() = input.split("\n\n").map { m -> m.lines() }.map {
        val items = ints(it[1]).map { i -> i.toBigInteger() }.toMutableList()
        val nums = ints(it[2])
        val operand2 = if (nums.isNotEmpty()) nums[0].toBigInteger() else BigInteger.ZERO
        val square = (nums.isEmpty())
        val op = if (square) { i: BigInteger -> i * i } else if (it[2].contains("+")) { i: BigInteger -> i + operand2 } else { i: BigInteger -> i * operand2 }
        val divisor = ints(it[3])[0].toBigInteger()
        val trueTo = ints(it[4])[0]
        val falseTo = ints(it[5])[0]
        val throwTo = { i: BigInteger -> if (i % divisor == BigInteger.ZERO) trueTo else falseTo }
        Monkey(items, op, divisor, throwTo)
    }.toList()
}






