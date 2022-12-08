package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day14.run()

object Day14 : Day(2016, 14) {

    override fun part1(): Any {
        val salt = input
        var i = 0
        val keys = mutableSetOf<P<String, Int>>()
        val lastThousandHashes = mutableListOf<Triple<String, Char?, Int>>().toArrayDeque()
        while (keys.size < 100) {
            val hashed = md5(salt + i)
            val tripleChar : Char? = hashed.e().windowed(3).map { it.distinct() }.filter { it.size == 1 }.map { it[0] }.firstOrNull()
            lastThousandHashes.addLast(Triple(hashed, tripleChar, i))
            if (lastThousandHashes.size > 1000)
                lastThousandHashes.removeFirst()
            val pents: List<Char>? = hashed.e().windowed(5).map { it.distinct() }.filter { it.size == 1 }?.map { it.get(0) }
            if (pents!!.isNotEmpty()) {
                val keysFound = lastThousandHashes
                    .filter {
                        pents.any {
                                p -> it.second != null && it.second == p
                        }
                    }
                    .filter {
                        it.first != hashed
                    }
                    .map { P(it.first, it.third) }
                keys.addAll(keysFound)
            }
            i++
        }
        val tmp = keys.toMutableList().sortedBy { it.second }.toMutableList()
        tmp.removeLast(keys.size - 64)
        return tmp.last().second
    }


    override fun part2(): Any {
        var memo = mutableMapOf<String, String>()
        val salt = input
        var i = 0
        val keys = mutableSetOf<P<String, Int>>()
        val lastThousandHashes = mutableListOf<Triple<String, Char?, Int>>().toArrayDeque()
        while (keys.size < 70) {
            var hashed = salt + i
            repeat(2017) {
                if (memo.containsKey(hashed)) {
                    hashed = memo[hashed]!!
                }
                else {
                    val newHash = md5(hashed)
                    memo[hashed] = newHash
                    hashed = newHash
                }
            }
            val tripleChar : Char? = hashed.e().windowed(3).map { it.distinct() }.filter { it.size == 1 }.map { it[0] }.firstOrNull()
            lastThousandHashes.addLast(Triple(hashed, tripleChar, i))
            if (lastThousandHashes.size > 1000)
                lastThousandHashes.removeFirst()
            val pents: List<Char>? = hashed.e().windowed(5).map { it.distinct() }.filter { it.size == 1 }?.map { it.get(0) }
            if (pents!!.isNotEmpty()) {
                val keysFound = lastThousandHashes
                    .filter {
                        pents.any {
                                p -> it.second != null && it.second == p
                        }
                    }
                    .filter {
                        it.first != hashed
                    }
                    .map { P(it.first, it.third) }
                if (keysFound.isNotEmpty()) {
                    keys.addAll(keysFound)
                }
            }
            i++
        }
        val tmp = keys.toMutableList().sortedBy { it.second }.toMutableList()
        tmp.removeLast(keys.size - 64)
        return tmp.last().second
    }
}