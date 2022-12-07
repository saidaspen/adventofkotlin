package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day

fun main() = Day19.run()

object Day19 : Day(2015, 19) {

    private var mol = input.lines().last()
    val repls = input.lines().map { it.split(" => ") }.dropLast(2)

    override fun part1(): Any {
        val possibles = mutableListOf<String>()
        for (repl in repls) {
            var position = 0
            while (position >= 0) {
                position = mol.indexOf(repl[0], position)
                if (position > 0) {
                    possibles.add(replace(mol, repl[0], repl[1], position)!!)
                    position += repl[0].length
                }
            }
        }
        return possibles.distinct().count()
    }

    override fun part2(): Any {
        var steps = 0
        while (mol.length > 1) {
            for (repl in repls) {
                if (mol.contains(repl[1])) {
                    mol = replace(mol, repl[1], repl[0], mol.lastIndexOf(repl[1]))!!
                    steps++
                }
            }
        }
        return steps
    }

    private fun replace(s: String, from: String, to: String, position: Int): String? {
        return s.substring(0, position) + to + s.substring(position + from.length)
    }
}


