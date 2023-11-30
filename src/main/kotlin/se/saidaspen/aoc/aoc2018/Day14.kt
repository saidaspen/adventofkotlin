package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.*

fun main() = Day14.run()

object Day14 : Day(2018, 14) {

    override fun part1(): Any {
        val numRecepies = input.toInt()
        val recipes = mutableListOf(3, 7)
        var elfFirst = 0
        var elfSecond = 1
        while (recipes.size < numRecepies + 10) {
            val elfFirstValue = recipes[elfFirst]
            val elfSecondValue = recipes[elfSecond]
            val newValue = elfFirstValue + elfSecondValue
            if (newValue > 9) {
                recipes.add(1)
            }
            recipes.add(newValue % 10)
            elfFirst = (elfFirst + elfFirstValue + 1) % recipes.size
            elfSecond = (elfSecond + elfSecondValue + 1) % recipes.size
        }
        return recipes.drop(numRecepies).subList(0, 10).joinToString("")
    }

    override fun part2(): Any {
        val recipes = StringBuilder("37")
        var elfFirst = 0
        var elfSecond = 1
        var last12 = ""
        while (!last12.contains(input)) {
            val elfFirstValue = recipes[elfFirst].digitToInt()
            val elfSecondValue = recipes[elfSecond].digitToInt()
            val newValue = elfFirstValue + elfSecondValue
            if (newValue > 9) {
                recipes.append("1")
            }
            recipes.append(newValue % 10)
            elfFirst = (elfFirst + elfFirstValue + 1) % recipes.length
            elfSecond = (elfSecond + elfSecondValue + 1) % recipes.length
            if (recipes.length > 12) {
                last12 = recipes.takeLast(10).toString()
            }
        }
        return recipes.indexOf(input)
    }
}
