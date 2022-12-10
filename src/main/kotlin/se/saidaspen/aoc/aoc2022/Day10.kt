package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() = Day10.run()

object Day10 : Day(2022, 10) {

    override fun part1(): Any {
        var x = 1
        var cycle = 1
        var sum = 0
        fun processCycle() {
            if (cycle % 40 == 20) sum += cycle * x
            cycle++
        }
        input.lines().forEach { line ->
            if (line == "noop") processCycle()
            else {
                processCycle()
                processCycle()
                x += line.substringAfter(" ").toInt()
            }
        }
        return sum
    }

    override fun part2(): Any {
        var x = 1
        var cycle = 1
        val screen = mutableMapOf<P<Int, Int>, Boolean>()

        fun processCycle() {
            val screenX = (cycle - 1) % 40
            if (screenX in (x - 1)..(x + 1)) {
                screen[P(screenX, (cycle - 1) / 40)] = true
            }
            cycle++
        }

        input.lines().forEach { line ->
            if (line == "noop") processCycle()
            else {
                processCycle()
                processCycle()
                x += line.substringAfter(" ").toInt()
            }
        }
        screen.printArea()
        return "EKRHEPUZ"
    }
}








