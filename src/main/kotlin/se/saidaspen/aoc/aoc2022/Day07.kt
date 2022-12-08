package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.removeFirst

fun main() = Day07.run()

object Day07 : Day(2022, 7) {

    data class Dir (
        val name: String,
        val path : String = "",
        val parent: Dir?,
        val subs: MutableList<Dir> = mutableListOf(),
        var files: Int = 0,
    )

    private val sizes = mutableMapOf<String, Int>()

    init {
        val root = Dir("/", path = "/", parent = null)
        var current = root
        input.lines().drop(1).forEach {
            if (it.startsWith("$ cd ..")) {
                current = current.parent!!
            } else if (it.startsWith("$ cd")) {
                val name = it.drop(5)
                val newDir = Dir(name, path = current.path + name + "/", parent = current)
                current.subs.add(newDir)
                current = newDir
            } else if (it[0].isDigit()) {
                val size = it.split(" ")[0].toInt()
                current.files += size
            }
        }
        mapSizes(root, sizes)
    }

    override fun part1(): Any {
        return sizes.entries.filter { it.value < 100000 }.sumOf { it.value }
    }

    private fun mapSizes(curr: Dir, visited: MutableMap<String, Int>) {
        visited[curr.path] = sizeOf(curr)
        curr.subs.forEach { mapSizes(it, visited) }
    }

    private fun sizeOf(dir: Dir) : Int {
        val fsize = dir.files
        val subsizes = dir.subs.sumOf { sizeOf(it) }
        return subsizes + fsize
    }

    override fun part2(): Any {
        val currentUsed = sizes["/"]!!
        val unused = 70000000 - currentUsed
        val toDelete = 30000000 - unused
        return sizes.entries.sortedBy { it.value }.first { it.value > toDelete }.value
    }
}






