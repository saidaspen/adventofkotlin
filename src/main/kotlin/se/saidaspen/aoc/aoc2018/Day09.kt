package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.*

fun main() = Day09.run()

object Day09 : Day(2018, 9) {
    override fun part1() : Any = play(ints(input)[0] * 1L, ints(input)[1] * 1L)
    override fun part2(): Any  = play(ints(input)[0] * 1L, ints(input)[1] * 100L)

    private fun play(numPlayers: Long, highestNum: Long): Long {
        val marbles = mutableMapOf<Long, P<Long, Long>?>()
        val scores = mutableMapOf<Long, Long>().withDefault { 0 }
        var current = 1L
        var nextMarble = 1L
        var currentPlayer = 1L
        marbles[0] = null
        while (true) {
            when {
                nextMarble == 1L -> {
                    marbles[0] = P(1, 1)
                    marbles[1] = P(0, 0)
                    current = nextMarble
                }
                nextMarble % 23L == 0L -> {
                    val fiveCCw = moveCCw(marbles, current, 5)
                    val sixCCw = moveCCw(marbles, current, 6)
                    val sevenCcw = moveCCw(marbles, current, 7)
                    val eightCcw = moveCCw(marbles, current, 8)
                    scores[currentPlayer] = scores.getValue(currentPlayer) + nextMarble + sevenCcw
                    marbles[eightCcw] = P(marbles[eightCcw]!!.first, sixCCw)
                    marbles[sixCCw] = P(eightCcw, fiveCCw)
                    current = sixCCw
                }
                else -> {
                    val oneCw = moveCw(marbles, current, 1)
                    val twoCw = moveCw(marbles, current, 2)
                    val threeCw = moveCw(marbles, current, 3)
                    marbles[oneCw] = P(moveCCw(marbles, oneCw, 1), nextMarble)
                    marbles[twoCw] = P(nextMarble, threeCw)
                    marbles[nextMarble] = P(oneCw, twoCw)
                    current = nextMarble
                }
            }
            nextMarble++
            currentPlayer = if (currentPlayer == numPlayers) 1 else currentPlayer + 1
            if (nextMarble > highestNum) break
        }
        return scores.values.maxOrNull()!!
    }

    private fun moveCw(marbles: MutableMap<Long, P<Long, Long>?>, current: Long, steps : Long): Long {
        var curr = current
        for (i in 0 until steps) curr = marbles[curr]!!.second
        return curr
    }

    private fun moveCCw(marbles: MutableMap<Long, P<Long, Long>?>, current: Long, steps : Long): Long {
        var curr = current
        for (i in 0 until steps) curr = marbles[curr]!!.first
        return curr
    }
}
