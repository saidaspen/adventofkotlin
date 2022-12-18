import se.saidaspen.aoc.util.*
import java.math.BigInteger
import kotlin.math.absoluteValue

fun main() = Dayxx.run()

object Dayxx : Day(2022, 17) {

    private var dash = toMap("####").map { it.key }.toSet()
    var plus = toMap(".#.\n###\n.#.").filter { it.value == '#' }.map { it.key }.toSet()
    private var lThing = toMap(
        "..#\n" +
                "..#\n" +
                "###"
    ).filter { it.value == '#' }.map { it.key }.toSet()
    private var iThing = toMap(
        "#\n" +
                "#\n" +
                "#\n" +
                "#"
    ).filter { it.value == '#' }.map { it.key }.toSet()
    private var square = toMap(
        "##\n" +
                "##"
    ).filter { it.value == '#' }.map { it.key }.toSet()
    val rocks = listOf(dash, plus, lThing, iThing, square)

    var up = P(0, -1)
    var down = P(0, 1)
    var left = P(-1, 0)
    var right = P(1, 0)
    var leftWallX = -3
    var rightWallX = 5
    var bottom = 4

    override fun part1(): Any {
//        input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"
        println(input)
        var vinds = input.e()
        var vindI = 0
        val map = mutableMapOf<Point, Char>()
        for (x in leftWallX..rightWallX) {
            map[P(x, bottom)] = '-'
        }
        var top = bottom
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
                if (afterMoving.map { it.x }.max() >= rightWallX) {

                } else if (afterMoving.map { it.x }.min() <= leftWallX) {

                } else if (afterMoving.any { map.containsKey(it) }) {

                } else {
                    movingRock = afterMoving
                }
            } else {
                val afterMoving = move(movingRock, 1, down)
                if (afterMoving.any { map.containsKey(it) }) {
                    movingRock.forEach { map[it] = '#' }
                    val rockType = nextRock
                    nextRock = (nextRock + 1) % 5
//                    map.printArea()
//                    println("\n\n\n\n")
                    val top = map.keys.map { it.second }.min()
                    movingRock = getRock(rockType, top)
                    stoppedRocks += 1

                } else {
                    movingRock = afterMoving
                }
            }
            doVind = !doVind
        }
        val topOfMap: Int = map.keys.map { it.second }.min() * -1 + 2
        return topOfMap
    }

    private fun getRock(t: Int, bottom: Int): Set<P<Int, Int>> {
        val rockType = t % 5
        var rock = rocks[rockType]
        val yMax = rock.map { it.second }.max()
        var bottomOfRockShouldBe = bottom - 4
        val distanceToFloor = (yMax - bottomOfRockShouldBe).absoluteValue
        rock = if (bottomOfRockShouldBe < yMax) {
            move(rock, distanceToFloor, up)
        } else {
            move(rock, distanceToFloor, down)
        }
        return rock
    }

    private fun move(rock: Set<Pair<Int, Int>>, distance: Int, dir: Pair<Int, Int>): Set<P<Int, Int>> {
        return rock.map { it + (dir * distance) }.toSet()
    }


    override fun part2(): Any {
//        input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"
        var vinds = input.e()
        var vindI = 0
        val map = mutableMapOf<Point, Char>()
        for (x in leftWallX..rightWallX) {
            map[P(x, bottom)] = '-'
        }
        var top = bottom
        var movingRock: Set<Point> = getRock(0, bottom)
        var nextRock = 1
        var doVind = true
        var heightDiffs = "0"
        while (true) {
            if (doVind) {
                val vind = vinds[vindI]
                vindI = (vindI + 1) % vinds.size
                val afterMoving = if (vind == '>') {
                    move(movingRock, 1, right)
                } else {
                    move(movingRock, 1, left)
                }
                if (afterMoving.map { it.x }.max() >= rightWallX) {

                } else if (afterMoving.map { it.x }.min() <= leftWallX) {

                } else if (afterMoving.any { map.containsKey(it) }) {

                } else {
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
                    var heightDiff = (top - prevTop).absoluteValue
                    heightDiffs += heightDiff
                    if (heightDiffs.length == 5_001) {
                        var newHeightDiffs = heightDiffs.drop(1000)
                        var found = false
                        var len = 20
                        var cycle = ""
                        while (!found) {
                            var needle = newHeightDiffs.substring(0, len)
                            if (newHeightDiffs.substring(len).startsWith(needle)) {
                                found = true
                                cycle = needle
                                continue
                            }
                            len += 1
                        }
                        var lastIndex = heightDiffs.lastIndexOf(cycle)
                        println("Lastindex " + lastIndex)
                        println("heightDiffs.length " + heightDiffs.length)
                        var whereInCycle = heightDiffs.length - (cycle.length + lastIndex)

                        println("whereInCycle " + whereInCycle)
                        println("Found cycle $len")
                        var heightAt5000 = heightDiffs.substring(1, 5000 + 1).e().map { it.digitToInt() }.sum()
                        var heightIncreaseOneCycle = cycle.e().map { it.digitToInt() }.sum().toLong()
                        println("Size after 5000 rocks is " + heightAt5000)
                        println("One cycle increases " + heightIncreaseOneCycle)
                        var cycleB = BigInteger.valueOf(len.toLong())
                        var times = (BigInteger.valueOf(1000000000000L).minus(BigInteger.valueOf(5000))).divide(cycleB)
                        var getsUsTo = BigInteger.valueOf(5000).add(times.multiply(cycleB))
                        var left = BigInteger.valueOf(1000000000000L).minus(getsUsTo)
                        var currHeight = heightAt5000.toLong().toBigInteger()
                            .add(heightIncreaseOneCycle.toBigInteger().multiply(times))
                        println("Gets us to $getsUsTo, with $left left")
                        var heightAfter =
                            (cycle + cycle).substring(whereInCycle, whereInCycle + left.toInt()).map { it.digitToInt() }
                                .sum()
                        var totalHeight = currHeight + heightAfter.toLong().toBigInteger()
                        println("currHeight+heightAfter=totalHeight\t$currHeight+$heightAfter=$totalHeight")
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

private operator fun Pair<Int, Int>.times(fact: Int): Pair<Int, Int> {
    return P(this.x * fact, this.y * fact)
}
