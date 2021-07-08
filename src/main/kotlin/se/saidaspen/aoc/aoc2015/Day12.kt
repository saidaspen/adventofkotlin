package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() {
    Day12.run()
}

object Day12 : Day(2015, 12) {
    override fun part1(): Any {
        return ints(input).sum()
    }

    override fun part2(): Any {
        var buffer = input
        while (buffer.contains("red")) {
            var redIdx = buffer.indexOf("red")
            var curlyStack = 0
            var brackStack = 0
            var brackLeft = Int.MIN_VALUE
            var curlyLeft = Int.MIN_VALUE
            for (i in redIdx downTo 0) {
                if (buffer[i] == '}') curlyStack++
                if (buffer[i] == ']') brackStack++
                if (buffer[i] == '[' && brackStack == 0 && brackLeft == Int.MIN_VALUE) {
                    brackLeft = i
                }
                if (buffer[i] == '[') brackStack--
                if (buffer[i] == '{' && curlyStack == 0 && curlyLeft == Int.MIN_VALUE) {
                    curlyLeft = i
                }
                if (buffer[i] == '{') curlyStack--
            }

            curlyStack = 0
            brackStack = 0
            var brackRight = Int.MAX_VALUE
            var curlyRight = Int.MAX_VALUE
            for (i in redIdx until buffer.length) {
                if (buffer[i] == '{') curlyStack++
                if (buffer[i] == '[') brackStack++
                if (buffer[i] == ']' && brackStack == 0 && brackRight == Int.MAX_VALUE) {
                    brackRight = i
                }
                if (buffer[i] == ']') brackStack--
                if (buffer[i] == '}' && curlyStack == 0 && curlyRight == Int.MAX_VALUE) {
                    curlyRight = i
                }
                if (buffer[i] == '}') curlyStack--
            }

            if (curlyRight < brackRight) {
                buffer = buffer.substring(0, curlyLeft) + "---" +  buffer.substring(curlyRight+ 1)
            } else {
                buffer = buffer.substring(0, redIdx) + "---" + buffer.substring(redIdx + 4)
            }
        }
        return ints(buffer).sum()
    }


}
