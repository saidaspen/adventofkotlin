package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.e

fun main() = Day11.run()

object Day11 : Day(2015, 11) {

    override fun part1() = nextValid(input)
    override fun part2() = nextValid(nextValid(input))

    private fun nextValid(current: String): String {
        var pwd = increment(current)
        while (!isValid(pwd)) pwd = increment(pwd)
        return pwd
    }

    private fun isValid(pwd: String): Boolean {
        return hasIncreasing(pwd) && !pwd.contains("[iol]".toRegex()) && getPairs(pwd) >= 2
    }

    private fun getPairs(pwd: String): Int {
        var pairs = 0
        var i = 0
        while (i < pwd.length -1) {
            if (pwd[i] == pwd[i + 1]) {
                pairs += 1
                i++
            }
            i++
        }
        return pairs
    }

    private fun hasIncreasing(pwd: String) = pwd.e().windowed(3).any {
        val (a, b, c) = it.toCharArray()
        a.code + 1 == b.code && b.code + 1 == c.code
    }

    private fun increment(inp: String): String {
        val chars = inp.toCharArray().toMutableList()
        var i = inp.length - 1
        while (i != -1) {
            if (chars[i] == 'z') {
                chars[i] = 'a'
                i--
            } else {
                chars[i] = inp[i] + 1
                break
            }
        }
        norm(chars)
        return chars.joinToString("")
    }

    private fun norm(chars: MutableList<Char>) {
        val idx = chars.indexOfFirst { it == 'i' || it == 'l' || it == 'o' }
        if (idx != -1) {
            chars[idx] = chars[idx] + 1
            for (i in idx + 1 until chars.size) chars[i] = 'a'
        }
    }
}


