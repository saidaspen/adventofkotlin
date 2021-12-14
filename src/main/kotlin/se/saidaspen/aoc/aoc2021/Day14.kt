package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.printMap
import java.math.BigInteger

fun main() = Day14.run()

object Day14 : Day(2021, 14) {

    override fun part1(): Any {

        var tmp = """NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C""".trimIndent()

//        input = tmp

        var template = input.lines()[0]
        println("template $template")

        val lines = input.lines().drop(2)
        var inserts = lines.map { it.split("->") }.map { it[0].trim() to it[1].trim() }.toMap()
        println(inserts)

        var step = 1
        var curr = template
        while (step <= 10) {
            curr = curr[0] + curr.windowed(2).map {
                if (inserts.containsKey(it)) {
                     inserts[it]!! + it[1]
                } else {
                    it
                }
            }.joinToString("")

            step++
//            println("After step $step: $curr")
        }
        var temp2 = curr.groupingBy { it }.eachCount()

        println("")
        return curr.groupingBy { it }.eachCount().maxOf { it.value } - curr.groupingBy { it }.eachCount().minOf { it.value }
    }

    override fun part2(): Any {

        var tmp = """NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C""".trimIndent()

//        input = tmp

        var template = input.lines()[0]
        println("template $template")

        val lines = input.lines().drop(2)
        var inserts = lines.map { it.split("->") }.map { it[0].trim() to it[1].trim() }.toMap()
        println(inserts)

        var curr = template

        var count = mutableMapOf<String, Long>()
        count["NN"] = 1
        count["NC"] = 1
        count["CB"] = 1
        var step = 1
        while (step <= 40) {
            var oldCount = count.entries.associate { it.key to it.value }
            count = mutableMapOf()
            oldCount.entries.forEach{
                val found = inserts[it.key]!!
                count[it.key[0] + found] = count.getOrDefault(it.key[0] + found, 0) + it.value
                count[found + it.key[1]] = count.getOrDefault(found +it.key[1], 0) + it.value
            }
//            curr = curr[0] + curr.windowed(2).map {
//                if (inserts.containsKey(it)) {
//                    inserts[it]!! + it[1]
//                } else {
//                    it
//                }
//            }.joinToString("")
            step++
//            println("Step: $step , size : " + curr.length)
        }
        var t = mutableMapOf<Char, Long>()

        count.entries.forEach {
            t[it.key[0]] = t.getOrDefault(it.key[0], 0) +it.value
        }
        t[template.last()] = t[template.last()]!! + 1

        return t.values.maxOrNull()!! - t.values.minOrNull()!!
//
//        var charCount = mutableMapOf<Char, BigInteger>()
//        count.forEach{
//            val char1 = it.key[0]
//            val char2 = it.key[1]
//            if (charCount[char1] == null) {
//                charCount[char1] = BigInteger.ZERO
//            }
//            if (charCount[char2] == null) {
//            charCount[char2] = BigInteger.ZERO
//        }
//            charCount[char1] = charCount[char1]!!+ it.value
//            charCount[char2] = charCount[char2]!!+ it.value
//        }
//        println(charCount)
//        return charCount.entries.sortedByDescending { it.value }.maxOf { it.value } -charCount.entries.sortedByDescending { it.value }.minOf { it.value }
    }
}












