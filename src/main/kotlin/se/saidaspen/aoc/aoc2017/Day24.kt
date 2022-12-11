package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day24.run()

data class Port(val inPins: Int, val outPins: Int)

object Day24 : Day(2017, 24) {

    private val ports: List<Port> = input.lines().map { it.split("/") }.map { Port(it[0].toInt(), it[1].toInt()) }

    override fun part1(): Int {
        return findStrongest(ports, 0)
    }

    private fun findStrongest(ports: List<Port>, numPorts: Int): Int {
        val candidates = ports.filter { it.inPins == numPorts || it.outPins == numPorts }.toList()
        var strongest = 0
        for (candidate in candidates) {
            val thisPort = if (candidate.inPins == numPorts) candidate.inPins else candidate.outPins
            val nextPorts = if (candidate.inPins == numPorts) candidate.outPins else candidate.inPins
            val cand = thisPort + nextPorts + findStrongest(ports.minus(candidate), nextPorts)
            strongest = if (strongest < cand) cand else strongest
        }
        return strongest
    }

    override fun part2(): Int {
        return findLongest(ports, 0, 0).second
    }

    private fun findLongest(ports: List<Port>, numPorts: Int, level: Int): Pair<Int, Int> {
        val candidates = ports.filter { it.inPins == numPorts || it.outPins == numPorts }.toList()
        var best = Pair(0, 0)
        for (candidate in candidates) {
            val thisPort = if (candidate.inPins == numPorts) candidate.inPins else candidate.outPins
            val nextPorts = if (candidate.inPins == numPorts) candidate.outPins else candidate.inPins
            val longestSub = findLongest(ports.minus(candidate), nextPorts, level + 1)
            val cand = Pair(longestSub.first + level, longestSub.second + thisPort + nextPorts)
            best = if (cand.first > best.first || (cand.first == best.first && cand.second > best.second)) cand else best
        }
        return best
    }
}