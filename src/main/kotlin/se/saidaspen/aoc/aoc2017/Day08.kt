package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day08.run()

object Day08 : Day(2017, 8) {

    private var lines: List<String> = input.lines()

    override fun part1(): Any {
        val registers = mutableMapOf<String, Int>()
        for (line in lines) {
            val tokens = line.split("\\s".toRegex())
            val reg = tokens[0]
            val value = tokens[2].toInt()
            val regCmd: (Int) -> Int = when(tokens[1]) {
                "inc" -> { a: Int -> a + value}
                "dec" -> { a: Int -> a - value}
                else -> throw RuntimeException("Unsupported operations")
            }
            val comp: (Int, Int) -> Boolean = when (tokens[5]) {
                "==" -> { a: Int, b: Int -> a == b}
                "!=" -> { a: Int, b: Int -> a != b}
                "<" -> { a: Int, b: Int -> a < b}
                "<=" -> { a: Int, b: Int -> a <= b}
                ">=" -> { a: Int, b: Int -> a >= b}
                ">" -> { a: Int, b: Int -> a > b}
                else -> throw RuntimeException("Unsupported operations")
            }
            if (comp.invoke(registers.getOrDefault(tokens[4], 0), tokens[6].toInt())) {
                registers[reg] = regCmd.invoke(registers.getOrDefault(reg, 0))
            }
        }
        return registers.values.maxOrNull() ?: Int.MIN_VALUE
    }

    override fun part2(): Any {
        val registers = mutableMapOf<String, Int>()
        var max = Int.MIN_VALUE
        for (line in lines) {
            val tokens = line.split("\\s".toRegex())
            val reg = tokens[0]
            val value = tokens[2].toInt()
            val regCmd: (Int) -> Int = when(tokens[1]) {
                "inc" -> { a: Int -> a + value}
                "dec" -> { a: Int -> a - value}
                else -> throw RuntimeException("Unsupported operations")
            }
            val comp: (Int, Int) -> Boolean = when (tokens[5]) {
                "==" -> { a: Int, b: Int -> a == b}
                "!=" -> { a: Int, b: Int -> a != b}
                "<" -> { a: Int, b: Int -> a < b}
                "<=" -> { a: Int, b: Int -> a <= b}
                ">=" -> { a: Int, b: Int -> a >= b}
                ">" -> { a: Int, b: Int -> a > b}
                else -> throw RuntimeException("Unsupported operations")
            }
            if (comp.invoke(registers.getOrDefault(tokens[4], 0), tokens[6].toInt())) {
                registers[reg] = regCmd.invoke(registers.getOrDefault(reg, 0))
            }
            val localMax = registers.values.maxOrNull() ?: Int.MIN_VALUE
            max = if (localMax > max ) localMax else max
        }
        return max
    }
}