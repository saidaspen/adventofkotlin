package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.removeFirst

fun main() {
    Day08.run()
}

class Node(private val children: MutableList<Node>, private val metas: List<Int>) {
    fun sumMetas(): Int = metas.sum() + children.sumBy { it.sumMetas() }
    fun value(): Int = if (children.isEmpty()) metas.sum() else metas.filter { it > 0 && it <= children.size }
        .sumOf { children[it - 1].value() }
}

object Day08 : Day(2018, 8) {
    override fun part1(): Any = buildTree(ints(input).toMutableList()).sumMetas()
    override fun part2(): Any = buildTree(ints(input).toMutableList()).value()
    private fun buildTree(code: MutableList<Int>): Node {
        val numChildren = code.removeAt(0)
        val numMeta = code.removeAt(0)
        return Node((0 until numChildren).map { buildTree(code) }.toMutableList(), code.removeFirst(numMeta))
    }
}
