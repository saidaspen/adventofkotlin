package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*
import kotlin.math.max
import kotlin.math.min

fun main() = Day05.run()

object Day05 : Day(2023, 5) {

    override fun part1(): Any {
        val seeds = longs(input.lines()[0])
        val mapraw = input.split("\n\n").drop(1)
        var minLoc = Long.MAX_VALUE
        for (seed in seeds) {
            var curr = seed
            for (map in mapraw) {
                val ranges = map.lines().drop(1).map { longs(it) }
                for (nums in ranges) {
                    if (curr >= nums[1] && curr <= nums[1] + nums[2]) {
                        curr = curr + nums[0] - nums[1]
                        break
                    }
                }
            }
            minLoc = min(minLoc, curr)
        }
        return minLoc
    }

    override fun part2(): Any {
        val seedRanges = longs(input.lines()[0]).windowed(2, 2).map { P(it[0], it[0]+it[1]) }
        val mapraw = input.split("\n\n").drop(1)
        val maps = mutableMapOf<P<String, String>, List<P<P<Long, Long>, P<Long, Long>>>>()
        for (m in mapraw) {
            val lines = m.lines()
            val name = lines[0].replace(" map:", "").split("-")
            val mapname = P(name[0], name[2])
            val map = mutableListOf<P<P<Long, Long>, P<Long, Long>>>()
            for (l in 1 until lines.size) {
                val nums = longs(lines[l])
                val srcrngstart = nums[1]
                val dstrngstart = nums[0]
                val rnglen = nums[2]
                map.add(P(P(srcrngstart, srcrngstart + rnglen), P(dstrngstart, dstrngstart + rnglen)))
            }
            maps[mapname] = map
        }
        var minLoc = Long.MAX_VALUE

        fun map(job: P<P<String, String>, P<Long, Long>>) : List<P<P<String, String>, P<Long, Long>>> {
            val mapper = maps[job.first]!!
            val outputRanges = mutableListOf<P<Long, Long>>()
            var notMappedRanges = mutableListOf(job.second)
            for (range in mapper) {
                val outsideRanges = mutableListOf<P<Long, Long>>()
                for (toMapRange in notMappedRanges) {
                    if (toMapRange.first < range.first.first) {
                        outsideRanges.add(P(toMapRange.first, min(toMapRange.second, range.first.first - 1)))
                    }
                    if (toMapRange.second > range.first.second) {
                        outsideRanges.add(P(max(range.first.second + 1, toMapRange.first), toMapRange.second))
                    }
                    if (toMapRange.first <= range.first.second && toMapRange.second >= range.first.first) {
                        val insideRange = P(max(range.first.first, toMapRange.first), min(range.first.second, toMapRange.second))
                        val translation = range.second.first - range.first.first
                        val translatedRange = P(insideRange.first + translation, insideRange.second + translation)
                        outputRanges.add(translatedRange)
                    }
                }
                notMappedRanges = outsideRanges
            }
            outputRanges.addAll(notMappedRanges)
            var needle = maps.keys.firstOrNull { it.first == job.first.second }
            if (needle == null) {
                needle = Pair("location", "")
            }
            return outputRanges.map { P(needle, it) }
        }
        val jobList = seedRanges.map { P(P("seed", "soil"), it) }.toMutableList()
        while (jobList.isNotEmpty()) {
            val job = jobList.removeFirst()
            if (job.first.second == "") {
                if (job.second.first < minLoc) {
                    minLoc = job.second.first
                }
            } else {
                jobList.addAll(0, map(job))
            }
        }
        return minLoc
    }
}



