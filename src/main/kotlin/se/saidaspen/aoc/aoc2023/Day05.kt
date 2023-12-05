package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*
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
        val seedRanges = longs(input.lines()[0]).chunked(2).map { it[0] .. it[0]+it[1] }
        val maps = input.split("\n\n").drop(1).map { section ->
            section.lines().drop(1)
                .associate { longs(it).let { (dst, src, len) -> src..(src + len) to dst..(dst + len) } }
        }
        return seedRanges.flatMap {
            seed -> maps.fold(listOf(seed)) { acc, map -> acc.flatMap { translateRange(map, it) } }
        }.minOf { it.first }
    }

    private fun translateRange(map: Map<LongRange, LongRange>, input: LongRange): List<LongRange> {
        val outputRanges = mutableListOf<LongRange>()
        var unmappedRanges = mutableListOf(input)
        for (srcRange in map.keys) {
            val outsideRanges = mutableListOf<LongRange>()
            for (toMapRange in unmappedRanges) {
                outsideRanges.addAll(toMapRange.exceptRange(srcRange))
                toMapRange.intersectRange(srcRange)?.let {
                    outputRanges.add(it.shift(map[srcRange]!!.first - srcRange.first))
                }
            }
            unmappedRanges = outsideRanges
        }
        outputRanges.addAll(unmappedRanges)
        return outputRanges
    }
}

