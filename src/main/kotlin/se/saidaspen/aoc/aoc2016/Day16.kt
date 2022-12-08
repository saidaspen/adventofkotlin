package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.e

fun main() = Day16.run()

object Day16 : Day(2016, 16) {

    override fun part1(): Any {
        val len = 272
        val cs = checksum(dataOf(len))
        return cs.joinToString("")
    }

    private fun dataOf(len: Int): MutableList<Int> {
        var data = input.e().map { it.toString().toInt() }.toMutableList()
        while (data.size < len) {
            val a: MutableList<Int> = data
            val b: MutableList<Int> = a.reversed().map { if (it == 1) 0 else 1 }.toMutableList()
            data = a + 0 + b
        }
        data = data.subList(0, len)
        return data
    }

    private fun checksum(inp: MutableList<Int>) : List<Int> {
        val cs = inp.windowed(2, 2).map {
            if (it[0] == it[1]) 1 else 0
        }.toMutableList()
        return if (cs.size % 2 == 0) {
            checksum(cs)
        } else {
            cs
        }
    }

    override fun part2(): Any {
        val len = 35651584
        val cs = checksum(dataOf(len))
        return cs.joinToString("")
    }
}

private operator fun <E> MutableList<E>.plus(that: E): MutableList<E> {
    val newList = this.toMutableList()
    newList.add(that)
    return newList
}

private operator fun <E> MutableList<E>.plus(that: MutableList<E>): MutableList<E> {
    val newList = this.toMutableList()
    newList.addAll(that)
    return newList
}

private fun <E> MutableList<E>.inv(): MutableList<Int> {
    return this.map { if (it == 1) 0 else 1}.toMutableList()
}
