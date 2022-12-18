package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*

fun main() = Day16.run()

object Day16: Day(2022, 16) {

    data class Valve(val rate: Int, val valves: List<String>)

    private val state = input.lines().associate { l ->
        val name = l.split(" ")[1]
        val rate = ints(l)[0]
        val valves = l.split(" ").drop(9).map { c -> c.replace(",", "").trim() }.toList()
        name to Valve(rate, valves)
    }

    private var memo1 = mutableMapOf<Triple<Set<String>, Int, String>, Int>()
    private var memo2 = mutableMapOf<Triple<Set<String>, Int, String>, Int>()

    override fun part1(): Any {
        return solve1(setOf(), 30, "AA")
    }

    override fun part2(): Any {
        return solve2(setOf(), 26, "AA")
    }

    private fun solve1(opened: Set<String>, minutes : Int, curr: String): Int {
        val cached = memo1[Triple(opened, minutes, curr)]
        if (cached != null) return cached
        if (minutes <= 0) return 0
        var currentBest = 0
        val s = state[curr]!!
        for (valve in s.valves) {
            // Just moving to the next valve directly, might be better than staying.
            val bestOneMoveAway = solve1(opened, minutes - 1, valve)
            currentBest = Integer.max(currentBest, bestOneMoveAway)
        }
        if (curr notin opened && s.rate > 0) {
            val minutesNext = minutes - 1
            for (valve in s.valves) {
                val bestAfterTurningOnThenMove = minutesNext * s.rate + solve1(opened + curr, minutesNext - 1, valve)
                currentBest = Integer.max(currentBest, bestAfterTurningOnThenMove)
            }
        }
        memo1[Triple(opened, minutes, curr)] = currentBest
        return currentBest
    }

    private fun solve2(opened: Set<String>, minutes : Int, curr: String): Int {
        val cached = memo2[Triple(opened, minutes, curr)]
        if (cached != null) return cached
        if (minutes <= 0) return solve1(opened, 26, "AA")
        var best = 0
        val s = state[curr]!!
        for (valve in s.valves) {
            best = Integer.max(best, solve2(opened, minutes - 1, valve))
        }
        if (curr notin opened && s.rate > 0) {
            val minutesNext = minutes -1
            for (valve in s.valves) {
                val bestAfterTurningOnThenMove = minutesNext * s.rate + solve2(opened + curr, minutesNext - 1, valve)
                best = Integer.max(best, bestAfterTurningOnThenMove)
            }
        }
        memo2[Triple(opened, minutes, curr)] = best
        return best
    }
}