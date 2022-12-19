package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*
import java.lang.Integer.max
import kotlin.math.min

fun main() = Day19.run()

object Day19 : Day(2022, 19) {

    private val bps = input.lines().map { ints(it) }.map {
        Pair(
            it[0],
            listOf(
                listOf(it[1], 0, 0, 0),
                listOf(it[2], 0, 0, 0),
                listOf(it[3], it[4], 0, 0),
                listOf(it[5], 0, it[6], 0)
            )
        )
    }

    override fun part1(): Any {
        return bps.sumOf { it.first * solve(it.second, 24) }
    }

    override fun part2(): Any {
        return bps.subList(0, 3).map { solve(it.second, 32) }.reduce { acc, next -> acc *  next}
    }

    private fun solve(bps: List<List<Int>>, initialTime: Int): Int {
        fun canAfford(bp: List<Int>, inv: List<Int>) = inv.withIndex().all { it.value >= bp[it.index] }
        val start = listOf(0, 0, 0, 0, 1, 0, 0, 0, initialTime)
        val queue = mutableListOf(start)
        val seen = mutableSetOf<List<Int>>()
        var best = 0
        while (queue.isNotEmpty()) {
            val curr = queue.removeFirst()
            if (seen.contains(curr)) continue
            val timeLeft = curr[8]
            if (timeLeft == 0) {
                best = max(best, curr[3])
                continue
            }
            if ((timeLeft * timeLeft - timeLeft) / 2 + curr[7] * timeLeft <= best - curr[3]) continue
            val currentInv = curr.subList(0, 4).toMutableList()
            val currentFactory = curr.subList(4).toMutableList()
            val maxCosts = listOf(bps.map { it[0] }.max(),
                bps.map { it[1] }.max(),
                bps.map { it[2] }.max(),
                bps.map { it[3] }.max()
            )
            // Throw away excess inventory
            currentInv[0] = min(currentInv[0], (timeLeft * maxCosts[0]) - (curr[4] * (timeLeft - 1)))
            currentInv[1] = min(currentInv[1], (timeLeft * maxCosts[1]) - (curr[5] * (timeLeft - 1)))
            currentInv[2] = min(currentInv[2], (timeLeft * maxCosts[2]) - (curr[6] * (timeLeft - 1)))

            val nextInventory = currentInv.elemAdd(currentFactory)

            val nextTime = timeLeft - 1
            if (canAfford(bps[3], currentInv)) {
                val nextFactory = currentFactory.toMutableList()
                nextFactory[3] += 1
                val invJettison = nextInventory.elemSubtract(bps[3])
                queue.add(0, invJettison + nextFactory + nextTime)
                continue
            }
            for (i in 0..2) {
                if (currentFactory[i] < maxCosts[i] && canAfford(bps[i], currentInv)) {
                    val nextFactory = currentFactory.toMutableList()
                    nextFactory[i] += 1
                    val invJettison = nextInventory.elemSubtract(bps[i])
                    queue.add(0, invJettison + nextFactory + nextTime)
                }
            }
            queue.add(0, nextInventory + currentFactory + nextTime)
            seen.add(curr)
        }
        return best
    }
}

private fun List<Int>.elemAdd(other: List<Int>): List<Int> {
    return this.mapIndexed { i, e -> e + other[i] }
}

private fun List<Int>.elemSubtract(other: List<Int>): List<Int> {
    return this.mapIndexed { i, e -> e - other[i] }
}

private fun <E> List<E>.subList(fromIndex: Int): List<E> {
    return this.subList(fromIndex, this.size - 1)
}
