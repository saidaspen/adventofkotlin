package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day09.run()

object Day09 : Day(2017, 9) {

    override fun part1(): String {
        var score = 0
        var group = 0
        var garbage = false
        val cancelled = false
        var i = 0
        while (i < input.length) {
            val c = input[i]
            if (c == '!' && garbage) {
                i += 2
                continue
            }
            when {
                cancelled -> {
                }
                c == '{' && !garbage -> {
                    group++
                    score += group
                }
                c == '}' && !garbage -> group--
                c == '<' && !garbage -> garbage = true
                c == '>' && garbage -> garbage = false
            }
            i += 1
        }
        return score.toString()
    }

    override fun part2(): String {
        var score = 0
        var group = 0
        var garbage = false
        val cancelled = false
        var i = 0
        while (i < input.length) {
            val c = input[i]
            if (c == '!' && garbage) {
                i += 2
                continue
            }
            when {
                cancelled -> {
                }
                c == '{' && !garbage -> group++
                c == '}' && !garbage -> group--
                c == '<' && !garbage -> garbage = true
                c == '>' && garbage -> garbage = false
                garbage -> score++
            }
            i += 1
        }
        return score.toString()
    }
}