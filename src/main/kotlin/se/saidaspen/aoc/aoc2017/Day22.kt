package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.plus

fun main() = Day22.run()

object Day22 : Day(2017, 22) {

    // Dirs UP, RIGHT, DOWN, LEFT
    private var dirs = arrayOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0));

    override fun part1(): Int {
        val map = mutableMapOf<Pair<Int, Int>, Int>().withDefault { 0 }

        // Square input which is center of the total grid
        val lines = input.lines()
        val mid = lines.size / 2
        for (row in lines.indices) {
            for (col in lines.indices) {
                map[Pair(-mid + col, mid - row)] = if (lines[row][col] == '#') 2 else 0
            }
        }
        val bursts = 10000
        var currPos = Pair(0, 0)
        var currDir = 0
        var burstsOfInfection = 0
        for (burst in 0 until bursts) {
            val state = map.getValue(currPos) == 2
            currDir = if (state) (currDir + 1) % dirs.size else (currDir + 3) % dirs.size
            map[currPos] = if (state) 0 else 2
            burstsOfInfection += if (!state) 1 else 0
            currPos += dirs[currDir]
        }
        return burstsOfInfection
    }

    override fun part2(): Int {
        val bursts = 10000000
        val map = mutableMapOf<Pair<Int, Int>, Int>().withDefault { 0 }

        // Square input which is center of the total grid
        val lines = input.lines()
        val mid = lines.size / 2
        for (row in lines.indices) {
            for (col in lines.indices) {
                map[Pair(-mid + col, mid - row)] = if (lines[row][col] == '#') 2 else 0
            }
        }
        var currPos = Pair(0, 0)
        var currDir = 0
        var burstsOfInfection = 0
        for (burst in 0 until bursts) {
            val state = map.getValue(currPos)
            currDir = when (state) {
                0 -> (currDir + 3) % dirs.size
                2 -> (currDir + 1) % dirs.size
                3 -> (currDir + 2) % dirs.size
                else -> currDir
            }
            map[currPos] = (state + 1) % 4
            burstsOfInfection += if (state == 1) 1 else 0
            currPos += dirs[currDir]
        }
        return burstsOfInfection
    }
}