package se.saidaspen.aoc.aoc2017

import se.saidaspen.aoc.util.Day

fun main() = Day16.run()

object Day16 : Day(2017, 16) {

    override fun part1(): String {
        val moves = input.split(",").toList()
        return dance("abcdefghijklmnop", moves)
    }

    private fun dance(input: String, moves: List<String>): String {
        val mem = input.toCharArray().toMutableList()
        for (move in moves) {
            when {
                move.startsWith("s") -> {
                    val spinStart = move.replaceFirst("s", "").toInt()
                    for (i in 0 until spinStart) mem.spinRight()
                }
                move.startsWith("x") -> {
                    val pos1 = move.replaceFirst("x", "").split("/")[0].toInt()
                    val pos2 = move.replaceFirst("x", "").split("/")[1].toInt()
                    val tmp = mem[pos1]
                    mem[pos1] = mem[pos2]
                    mem[pos2] = tmp
                }
                move.startsWith("p") -> {
                    val obj1 = move.replaceFirst("p", "").split("/")[0][0]
                    val obj2 = move.replaceFirst("p", "").split("/")[1][0]
                    val pos1 = mem.indexOf(obj1)
                    val pos2 = mem.indexOf(obj2)
                    val tmp = mem[pos1]
                    mem[pos1] = mem[pos2]
                    mem[pos2] = tmp
                }
                else -> {
                    throw RuntimeException("Not supported operation $move")
                }
            }
        }
        return mem.joinToString("")
    }

    fun <T> MutableList<T>.spinRight() {
        val tmp = this[this.size - 1]
        for (i in this.size - 2 downTo 0) this[i + 1] = this[i]
        this[0] = tmp
    }

    override fun part2(): String {
        val moves = input.split(",").toList()
        val memo = mutableMapOf<String, String>()
        var pos = "abcdefghijklmnop"
        // Since it is repeating we could calculate what it will be instead
        // but no use, it just took a couple of seconds to run through them
        // all.
        for (i in 0 until 1000_000_000) {
            if (memo.containsKey(pos))
                pos = memo[pos]!!
            else {
                val newPos = dance(pos, moves)
                memo[pos] = newPos
                pos = newPos
            }
        }
        return pos
    }
}