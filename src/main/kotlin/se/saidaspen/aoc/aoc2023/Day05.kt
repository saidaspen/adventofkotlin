package se.saidaspen.aoc.aoc2023

import se.saidaspen.aoc.util.*

fun main() = Day05.run()

object Day05 : Day(2023, 5) {

    private val maps = input.split("\n\n").drop(1)
        .map { it.lines().drop(1)
            .associate {
                l -> longs(l).let { (dst, src, len) -> src..(src + len) to dst..(dst + len) }
            }
        }

    override fun part1() = longs(input.lines()[0])
        .map { it..it }
        .flatMap {
            seed -> maps.fold(listOf(seed)) { acc, map -> acc.flatMap { translateRange(map, it) } }
        }.minOf { it.first }

    override fun part2() = longs(input.lines()[0]).chunked(2)
        .map { it[0]..it[0] + it[1] }
        .flatMap {
            seed -> maps.fold(listOf(seed)) { acc, map -> acc.flatMap { translateRange(map, it) } }
        }.minOf { it.first }

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

