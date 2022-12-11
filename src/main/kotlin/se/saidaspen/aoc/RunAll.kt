package se.saidaspen.aoc

import se.saidaspen.aoc.util.Day
import java.time.Duration
import java.time.temporal.TemporalUnit
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val years = 2015..2017
    val days = 1..25
    for (year in years) {
        for (day in days) {
            val klass = "se.saidaspen.aoc.aoc$year.Day${day.toString().padStart(2, '0')}"
            try {
                val c = Class.forName(klass)
                val d: Day = c.getField("INSTANCE").get(null) as Day
                print("$year ${day.toString().padStart(2, '0')}")
                val prevCorrect1 = d.prevCorrectValue(Day.PART.ONE)
                val prevCorrect2: String? = d.prevCorrectValue(Day.PART.TWO)

                print("\t\tPart 1:")
                val before1 = System.currentTimeMillis()
                val part1Result = (prevCorrect1 == d.part1().toString())
                val after1 = System.currentTimeMillis()
                val logMessagePart1 = if (part1Result) (after1 - before1).milliseconds.toString() else "❌"
                var paddingLeft = " ".repeat(15-logMessagePart1.length)
                print(paddingLeft + logMessagePart1)

                var padding = " ".repeat(30 - logMessagePart1.length - paddingLeft.length)
                print(padding + "Part 2:")
                val before2 = System.currentTimeMillis()
                val part2Result = ((day == 25 && prevCorrect2 == null) || prevCorrect2 == d.part2().toString())
                val after2 = System.currentTimeMillis()
                val logMessagePart2 = if (part2Result) "" + (after2 - before2).milliseconds.toString() else "❌"
                paddingLeft = " ".repeat(15-logMessagePart2.length)
                print(paddingLeft + logMessagePart2)

                print("\n")
            } catch (e: ClassNotFoundException) {
                println("$year ${day.toString().padStart(2, '0')}\t\tPart1: ❌\tPart2: ❌")
            }
        }
    }
}

