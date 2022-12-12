package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.*

fun main() = Day12.run()

object Day12 : Day(2018, 12) {

    override fun part1(): Any {
        input = "initial state: #..#.#..##......###...###\n" +
                "\n" +
                "...## => #\n" +
                "..#.. => #\n" +
                ".#... => #\n" +
                ".#.#. => #\n" +
                ".#.## => #\n" +
                ".##.. => #\n" +
                ".#### => #\n" +
                "#.#.# => #\n" +
                "#.### => #\n" +
                "##.#. => #\n" +
                "##.## => #\n" +
                "###.. => #\n" +
                "###.# => #\n" +
                "####. => #"
        println(input)
        var initial = input.lines().first().split(" ")[2]
        var rules = input.lines().drop(2).map { it.split(" => ")[0] to it.split(" => ")[1]}.toMap()
        var current = initial
        repeat(20) {
            println(current)
            var tmp = "..$current.."

            current = ".." + (2..tmp.length - 3).map {
                val prev = tmp.substring(it - 2, it + 3)
                if (rules.containsKey(prev)) {
                   rules[prev]
                } else {
                    "."
//                    throw RuntimeException("Unsupported")
                }
            }.joinToString("") + ".."
        }
        return ""
    }

    override fun part2(): Any {
        return ""
    }
}
