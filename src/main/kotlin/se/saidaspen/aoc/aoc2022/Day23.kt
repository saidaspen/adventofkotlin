package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*
import se.saidaspen.aoc.util.COMPASS.*
import kotlin.math.absoluteValue

fun main() = Day23.run()

object Day23 : Day(2022, 23) {

    class World(val map: MutableMap<P<Int, Int>, Char>, var hasMoved: Boolean = false) {
        private var considerDir = listOf(listOf(N, NE, NW), listOf(S, SE, SW), listOf(W, NW, SW), listOf(E, NE, SE))
        private var plan = 0
        val elvesCount : Int by lazy  { map.entries.count { it.value == '#' }}

        fun step() {
            val elves = map.entries.filter { it.value == '#' }.map { it.key }.toList()
            val planElves = elves.filter { neighbors(it).any { n -> map[n] == '#' } }
            val plannedMoves = mutableMapOf<Point, Point>()
            for (elf in planElves) {
                var isValid = false
                var toTest = plan
                var tested = 0
                while (!isValid && tested < 4) {
                    if (!considerDir[toTest].any { map[elf + it] == '#' }) {
                        isValid = true
                        plannedMoves[elf] = elf + considerDir[toTest][0]
                    }
                    tested += 1
                    toTest = (toTest + 1) % 4
                }
            }
            // Filter
            val positionsDoublePlanned = plannedMoves.map { it.value }.histo().filter { it.value > 1 }.map { it.key }
            val movesToExec = plannedMoves.entries.filter { !positionsDoublePlanned.contains(it.value) }
            // Move
            for (move in movesToExec) {
                map[move.key] = '.'
                map[move.value] = '#'
            }
            hasMoved = movesToExec.isNotEmpty()
            plan = (plan + 1) % 4
        }
    }

    override fun part1(): Any {
        val world = World(toMap(input))
        repeat(10) { world.step() }
        val elves = world.map.filter { it.value == '#' }
        val width = (elves.maxOf { it.key.first } - elves.minOf { it.key.first }).absoluteValue + 1
        val height = (elves.maxOf { it.key.second } - elves.minOf { it.key.second }).absoluteValue + 1
        return width * height - world.elvesCount
    }

    override fun part2(): Any {
        val world = World(toMap(input))
        var round = 1
        while (true) {
            world.step()
            if (!world.hasMoved) return round
            round += 1
        }
    }
}
