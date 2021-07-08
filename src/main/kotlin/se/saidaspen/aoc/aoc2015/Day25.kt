package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.*

fun main() {
    Day25.run()
}

object Day25 : Day(2015, 25) {

    override fun part1(): Any {
        // Enter the code at row 2981, column 3075.
        var prevTopRowValue = 1
        for (i in 2 until 3075 + 2981) prevTopRowValue = i * i - prevTopRowValue
        val codeNum = prevTopRowValue - 2981 + 1
        var code = 20151125L
        for (i in 2..codeNum) code = (code * 252533) % 33554393
        return code
    }

    override fun part2(): Any {
        return ""
    }

}
