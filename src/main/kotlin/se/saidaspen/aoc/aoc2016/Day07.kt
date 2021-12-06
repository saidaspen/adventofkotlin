package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day07.run()

object Day07 : Day(2016, 7) {

    override fun part1() = input.lines().count { supportsTls(it) }
    override fun part2() = input.lines().count { supportSsl(it) }

    private fun supportSsl(s: String): Boolean {
        val candidates = superString(s).windowed(3).filter { it[0] == it[2] && it[0] != it[1] }
        val needles = candidates.map { "" + it[1] + it[0] + it[1] }
        return subStrings(s).any { subStr -> needles.any { subStr.contains(it) } }
    }

    private fun superString(s: String) = """\[[a-z]+]""".toRegex().replace(s, ",").split(",").joinToString("")

    private fun supportsTls(s: String) : Boolean {
        val subStringWAbba = subStrings(s).any { isAbba(it) != null }
        return !subStringWAbba && """\[[a-z]+]""".toRegex().replace(s, ",").split(",").map { isAbba(it) != null }
            .any { it }
    }

    private fun isAbba(s: String) = s.windowed(4).firstOrNull { it[0] != it[1] && it.take(2) == it.takeLast(2).reversed() }

    private fun subStrings(s: String) = """\[[a-z]+]""".toRegex().findAll(s).map { it.value }.map { it.drop(1).dropLast(1) }.toList()


}
