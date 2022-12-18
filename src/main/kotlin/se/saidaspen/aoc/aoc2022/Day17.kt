package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.*
import kotlin.math.absoluteValue

fun main() = Day17.run()

object Day17 : Day(2022, 17) {

    private var dash = "####"
    private var plus = ".#.\n###\n.#."
    private var lThing = "..#\n..#\n###"
    private var iThing = "#\n#\n#\n#"
    private var square = "##\n##"
    private val rocks = listOf(dash, plus, lThing, iThing, square).map { block ->  toMap(block).filter { it.value == '#' }.map { it.key }.toSet()}
    private val vinds = input.e()

    private val up = P(0, -1)
    private val down = P(0, 1)
    private val left = P(-1, 0)
    private val right = P(1, 0)
    private const val leftWallX = -3
    private const val rightWallX = 5
    private const val bottom = 4

    override fun part1(): Any {
        var vindI = 0
        val map = mutableMapOf<Point, Char>()
        for (x in leftWallX..rightWallX) {
            map[P(x, bottom)] = '-'
        }
        var movingRock: Set<Point> = getRock(0, bottom)
        var nextRock = 1
        var doVind = true
        var stoppedRocks = 0
        while (stoppedRocks < 2023) {
            if (doVind) {
                val vind = vinds[vindI]
                vindI = (vindI + 1) % vinds.size
                val afterMoving = if (vind == '>') {
                    move(movingRock, 1, right)
                } else {
                    move(movingRock, 1, left)
                }
                if (afterMoving.map { it.x }.max() < rightWallX
                    && afterMoving.map { it.x }.min() > leftWallX
                    && !afterMoving.any { map.containsKey(it) }
                ) {
                    movingRock = afterMoving
                }
            } else {
                val afterMoving = move(movingRock, 1, down)
                if (afterMoving.any { map.containsKey(it) }) {
                    movingRock.forEach { map[it] = '#' }
                    val rockType = nextRock
                    nextRock = (nextRock + 1) % 5
                    val top = map.keys.map { it.second }.min()
                    movingRock = getRock(rockType, top)
                    stoppedRocks += 1
                } else {
                    movingRock = afterMoving
                }
            }
            doVind = !doVind
        }
        return map.keys.map { it.second }.min() * -1 + 2
    }

    private fun getRock(t: Int, bottom: Int): Set<P<Int, Int>> {
        var rock = rocks[t % 5]
        val yMax = rock.map { it.second }.max()
        val bottomOfRockShouldBe = bottom - 4
        val distanceToFloor = (yMax - bottomOfRockShouldBe).absoluteValue
        rock = if (bottomOfRockShouldBe < yMax) { move(rock, distanceToFloor, up) } else { move(rock, distanceToFloor, down)}
        return rock
    }

    private fun move(rock: Set<Pair<Int, Int>>, distance: Int, dir: Pair<Int, Int>): Set<P<Int, Int>> {
        return rock.map { it + (dir * distance) }.toSet()
    }

    override fun part2(): Any {
        val vinds = input.e()
        var vindI = 0
        val map = mutableMapOf<Point, Char>()
        for (x in leftWallX..rightWallX) {
            map[P(x, bottom)] = '-'
        }
        var movingRock: Set<Point> = getRock(0, bottom)
        var nextRock = 1
        var doVind = true
        var heightDiffs = "0"
        while (true) {
            if (doVind) {
                val vind = vinds[vindI]
                vindI = (vindI + 1) % Day17.vinds.size
                val afterMoving = if (vind == '>') {
                    move(movingRock, 1, right)
                } else {
                    move(movingRock, 1, left)
                }
                if (afterMoving.map { it.x }.max() < rightWallX
                    && afterMoving.map { it.x }.min() > leftWallX
                    && !afterMoving.any { map.containsKey(it) }
                ) {
                    movingRock = afterMoving
                }
            } else {
                val afterMoving = move(movingRock, 1, down)
                if (afterMoving.any { map.containsKey(it) }) {
                    val prevTop = map.keys.map { it.second }.min()
                    movingRock.forEach { map[it] = '#' }
                    val rockType = nextRock
                    nextRock = (nextRock + 1) % 5
                    val top = map.keys.map { it.second }.min()
                    val heightDiff = (top - prevTop).absoluteValue
                    heightDiffs += heightDiff
                    if (heightDiffs.length == 5001) {
                        val newHeightDiffs = heightDiffs.drop(1000)
                        var found = false
                        var len = 20
                        var cycle = ""
                        while (!found) {
                            val needle = newHeightDiffs.substring(0, len)
                            if (newHeightDiffs.substring(len).startsWith(needle)) {
                                found = true
                                cycle = needle
                                continue
                            }
                            len += 1
                        }
                        val lastIndex = heightDiffs.lastIndexOf(cycle)
                        val whereInCycle = heightDiffs.length - (cycle.length + lastIndex)
                        val heightAt5000 = heightDiffs.substring(1, 5000 + 1).e().sumOf { it.digitToInt() }.toLong()
                        val heightIncreaseOneCycle = cycle.e().sumOf { it.digitToInt() }.toLong()
                        val cycleB = len.toLong()
                        val times = (1000000000000L - 5000L) / cycleB
                        val getsUsTo = 5000L + times * cycleB
                        val left = 1000000000000L - getsUsTo
                        val currHeight = heightAt5000 + heightIncreaseOneCycle * times
                        val heightAfter = (cycle + cycle).substring(whereInCycle, whereInCycle + left.toInt()).map { it.digitToInt() }.sum()
                        val totalHeight = currHeight + heightAfter
                        return totalHeight.toString()
                    }
                    movingRock = getRock(rockType, top)
                } else {
                    movingRock = afterMoving
                }
            }
            doVind = !doVind
        }
    }
}
