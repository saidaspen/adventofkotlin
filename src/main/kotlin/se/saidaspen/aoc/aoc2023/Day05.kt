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

    private fun translateRange(map: Map<LongRange, LongRange>, input: LongRange): List<LongRange> {
        val outputRanges = mutableListOf<LongRange>()
        var unmappedRange = mutableListOf(input)
        for (srcRange in map.keys) {
            val outsideRanges = mutableListOf<LongRange>()
            for (toMapRange in unmappedRange) {
                // Stuff that is outside (lower) than the mapping
                if (toMapRange.first < srcRange.first)
                    outsideRanges.add(toMapRange.first .. min(toMapRange.last, srcRange.first - 1))
                // Stuff that is outside (larger) than the mapping
                if (toMapRange.last > srcRange.last)
                    outsideRanges.add(max(srcRange.last + 1, toMapRange.first) .. toMapRange.last)
                // Stuff that is in the mappers range
                if (toMapRange.first <= srcRange.last && toMapRange.last >= srcRange.first) {
                    val insideRange = max(srcRange.first, toMapRange.first) .. min(srcRange.last, toMapRange.last)
                    val translation = map[srcRange]!!.first - srcRange.first
                    outputRanges.add(insideRange.first + translation .. insideRange.last + translation)
                }
            }
            unmappedRange = outsideRanges
        }
        outputRanges.addAll(unmappedRange)
        return outputRanges
    }

    override fun part2(): Any {
        val seedRanges = longs(input.lines()[0]).chunked(2).map { it[0] .. it[0]+it[1] }
        val maps = input.split("\n\n").drop(1).map { section ->
            section.lines()
                .drop(1) // This is the header
                .associate { longs(it).let { (dst, src, len) -> src..(src + len) to dst..(dst + len) } }
        }
        return seedRanges.flatMap { seed ->
            maps.fold(listOf(seed)) { acc, map -> acc.flatMap { translateRange(map, it) } }
        }.minOf { it.first }
    }
}



