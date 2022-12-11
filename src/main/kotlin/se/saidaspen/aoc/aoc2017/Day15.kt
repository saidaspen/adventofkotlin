package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day15.run()

object Day15 : Day(2017, 15) {

    private val startA = 703
    private val startB = 516

    override fun part1(): String {
        val genA = Generator((1).toULong(), startA.toULong(), (16807).toULong())
        val genB = Generator((1).toULong(), startB.toULong(), (48271).toULong())
        var cnt = 0
        for (i in 0 until 40_000_000) {
            if (genA.value().and((0b11111111_11111111).toULong()) ==
                genB.value().and((0b11111111_11111111).toULong())) {
                cnt++
            }
        }
        return cnt.toString()
    }

    override fun part2(): String {
        val genA = Generator((4).toULong(), startA.toULong(), (16807).toULong())
        val genB = Generator((8).toULong(), startB.toULong(), (48271).toULong())
        var cnt = 0
        for (i in 0 until 5_000_000) {
            if (genA.value().and((0b11111111_11111111).toULong()) ==
                genB.value().and((0b11111111_11111111).toULong())) {
                cnt++
            }
        }
        return cnt.toString()
    }


    class Generator(var mult: ULong, var num: ULong, val factor: ULong) {
        private val ZERO = (0).toULong()

        fun value(): ULong {
            do {
                num = (num * factor) % (2147483647).toULong() // This is INT_MAX!
            } while (num % mult != ZERO)
            return num
        }
    }
}