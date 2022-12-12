import se.saidaspen.aoc.util.*

fun main() = Day12.run()

object Day12 : Day(2022, 12) {

    private val map = toMap(input)
    private fun costOf(b: Pair<Int, Int>, a: Pair<Int, Int>) =  (map[b]!!.code - 'a'.code) - (map[a]!!.code - 'a'.code)
    private val startPos = map.entries.first { it.value == 'S' }.key
    private val endPos = map.entries.first { it.value == 'E' }.key
    init {
        map[startPos] = 'a'
        map[endPos] = 'z'
    }

    override fun part1() =  search(startPos).second

    override fun part2(): Any {
        val candidateStarts = map.entries.filter { it.value == 'a' }.map { it.key }
        return candidateStarts.map { search(it) }.filter { it.first != null }.minOf { it.second }
    }

    private fun search(s: P<Int, Int>) = bfs(s, { it == endPos }, { t ->
        t.neighborsSimple().filter { map.containsKey(it) }
            .filter { costOf(it, t) <= 1 }
            .toMutableList()
    })
}





