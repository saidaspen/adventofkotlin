package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day17.run()

typealias State = P<P<Int, Int>, String>

object Day17 : Day(2016, 17) {

    val next : (State) -> Iterable<State> = { (pos, inp) ->
        val doorState = doorState(inp, pos)
        val next = mutableListOf<State>()
        if (doorState[0]){ next.add(P(pos.x, pos.y-1) to inp + "U") }
        if (doorState[1]){ next.add(P(pos.x, pos.y+1) to inp + "D") }
        if (doorState[2]){ next.add(P(pos.x-1, pos.y) to inp + "L")}
        if (doorState[3]){ next.add(P(pos.x+1, pos.y) to inp + "R")}
        next
    }

    override fun part1(): Any {
       return bfs(P(0,0) to input, { (a, _) -> a.x == 3 && a.y == 3 }, next).first!!.second.replace(input, "")
    }

    override fun part2(): Any {
        return bfsLong(P(0,0) to input, { (a, _) -> a.x == 3 && a.y == 3 }, next)!!.first.second.replace(input, "").length
    }

    private fun doorState(input: String, pos : P<Int, Int>) : MutableList<Boolean> {
        val doors = md5(input).take(4).map { "bcdef".contains(it) }.toMutableList()
        if (pos.x == 0) doors[2] = false
        if (pos.x == 3) doors[3] = false
        if (pos.y == 0) doors[0] = false
        if (pos.y == 3) doors[1] = false
        return doors
    }

    inline fun <State> bfsLong(start: State, isEnd: (State) -> Boolean, next: (State) -> Iterable<State>): Pair<State, Int>? {
        var queue = mutableListOf(start)
        var nextQueue = mutableListOf<State>()
        var dist = -1
        var longest: Pair<State, Int>? = null
        while (queue.isNotEmpty()) {
            dist++
            for (current in queue) {
                if (isEnd(current)) longest = current to dist
                else
                    for (i in next(current)) {
                        nextQueue.add(i)
                    }
            }
            val p = nextQueue
            nextQueue = queue
            queue = p
            nextQueue.clear()
        }
        return longest
    }

}
