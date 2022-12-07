package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day

fun main() = Day07.run()

object Day07 : Day(2015, 7) {

    private var data = input.lines()
    private val values = mutableMapOf<String, Int>()
    private val consts = mutableMapOf<String, String>()
    private val ands = mutableMapOf<String, Pair<String, String>>()
    private val ors = mutableMapOf<String, Pair<String, String>>()
    private val lshifts = mutableMapOf<String, Pair<String, Int>>()
    private val rshifts = mutableMapOf<String, Pair<String, Int>>()
    private val nots = mutableMapOf<String, String>()

    init {
        for (line in data) {
            when {
                "AND" in line -> {
                    val (a, _, b, _, c) = line.split(" ")
                    ands[c] = a to b
                }

                "OR" in line -> {
                    val (a, _, b, _, c) = line.split(" ")
                    ors[c] = a to b
                }

                "LSHIFT" in line -> {
                    val (a, _, b, _, c) = line.split(" ")
                    lshifts[c] = a to b.toInt()
                }

                "RSHIFT" in line -> {
                    val (a, _, b, _, c) = line.split(" ")
                    rshifts[c] = a to b.toInt()
                }

                "NOT" in line -> {
                    val (_, a, _, b) = line.split(" ")
                    nots[b] = a
                }

                else -> {
                    val (a, _, b) = line.split(" ")
                    consts[b] = a
                }
            }
        }
    }

    private fun find(x: String): Int {
        values[x]?.let { return it }

        val i = x.toIntOrNull()
        if (i != null) {
            values[x] = i
            return i
        }

        consts[x]?.let {
            values[x] = find(it); return values[x]!!
        }

        ands[x]?.let { values[x] = find(it.first) and find(it.second); return values[x]!! }
        ors[x]?.let { values[x] = find(it.first) or find(it.second); return values[x]!! }
        lshifts[x]?.let { values[x] = (find(it.first) shl it.second) and UShort.MAX_VALUE.toInt(); return values[x]!! }
        rshifts[x]?.let { values[x] = find(it.first) shr it.second; return values[x]!! }
        nots[x]?.let { values[x] = find(it).inv() and UShort.MAX_VALUE.toInt(); return values[x]!! }
        error("Can't find $x")
    }

    override fun part1(): Any {
        return find("a")
    }

    override fun part2(): Any {
        val valA = find("a")
        values.clear()
        values["b"] = valA
        return find("a")
    }
}


