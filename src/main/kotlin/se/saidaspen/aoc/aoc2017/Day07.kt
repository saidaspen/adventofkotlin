package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day07.run()

object Day07 : Day(2017, 7) {

    private var nodes: HashSet<String> = HashSet()
    private var heldBy: HashMap<String, String> = HashMap()
    private var holding: HashMap<String, List<String>> = HashMap()
    private var weights: HashMap<String, Int> = HashMap()
    private var lines: List<String> = input.lines()

    init {
        for (line in lines) {
            val split = line.split("->".toRegex())
            val node = split[0].split("\\s".toRegex())[0].trim()
            val weight = split[0].split("\\s".toRegex())[1].trim().replace("(", "").replace(")", "").toInt()
            weights[node] = weight
            val nHolding = if (split.size > 1) split[1].split(",".toRegex()).map { it.trim() }.toList() else listOf()
            nodes.add(node)
            holding[node] = nHolding
            for (n in nHolding) heldBy[n] = node
        }
    }

    override fun part1() = getBaseNode()

    private fun getBaseNode(): String {
        for (n in nodes) if (!heldBy.containsKey(n)) return n
        throw RuntimeException("Unable to find base node")
    }

    override fun part2(): String = findUnbalancedNode(getBaseNode()).toString()

    private fun findUnbalancedNode(node: String): Int {
        val unbalancedNodes = getUnbalancedNode(node)
        return when {
            unbalancedNodes.size > 1 -> throw RuntimeException("Found multiple unbalanced nodes")
            unbalancedNodes.isNotEmpty() -> findUnbalancedNode(unbalancedNodes[0].first)
            else -> {
                val expectedWeight = weigtOf(holding[heldBy[node]]!!.filter { it != node }[0])
                val weightOfChildren = weigtOf(node) - weights[node]!!
                expectedWeight - weightOfChildren
            }
        }
    }

    private fun getUnbalancedNode(baseNode: String): List<Pair<String, Int>> {
        var childNodes = holding[baseNode]
        childNodes = childNodes ?: mutableListOf()
        val childWeights = mutableMapOf<Int, MutableList<String>>()
        for (cn in childNodes) {
            val weight = weigtOf(cn)
            val tmp = childWeights.remove(weight) ?: mutableListOf()
            tmp.add(cn)
            childWeights[weight] = tmp
        }
        return childWeights.filter { it.value.size == 1 }.map { Pair(it.value[0], it.key) }.toList()
    }

    private fun weigtOf(cn: String): Int {
        return weights[cn]?.plus(holding[cn]!!.map { weigtOf(it) }.sum())!!
    }
}