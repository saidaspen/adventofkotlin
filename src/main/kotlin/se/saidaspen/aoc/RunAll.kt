package se.saidaspen.aoc

import se.saidaspen.aoc.util.Day
import java.util.*

fun main() {
    val years = 2015..2016
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

                print("\t\tPart 1\t")
                val before1 = System.currentTimeMillis()
                val part1Result = (prevCorrect1 == d.part1().toString())
                val after1 = System.currentTimeMillis();
                print(if (part1Result) formatDurationTime(after1 - before1) else "❌")

                print("\t\tPart 2\t")
                val before2 = System.currentTimeMillis()
                val part2Result = ((day == 25 && prevCorrect2 == null) || prevCorrect2 == d.part2().toString())
                val after2 = System.currentTimeMillis()
                print(if (part2Result) formatDurationTime(after2 - before2) else "❌")

                print("\n")
            } catch (e: ClassNotFoundException) {
                println("$year ${day.toString().padStart(2, '0')}\t\tPart1: ❌\tPart2: ❌")
            }
        }
    }
}

fun formatDurationTime(durationSeconds: Long): String {
    var hours = 0L
    var minutes = 0L
    var seconds = durationSeconds
    if (seconds >= 3600) {
        hours = seconds / 3600
        seconds -= hours * 3600
    }
    if (seconds >= 60) {
        minutes = seconds / 60
        seconds -= minutes * 60
    }
    return Formatter().format("%1\$02d:%2\$02d:%3\$02d", hours, minutes, seconds).toString()
}
