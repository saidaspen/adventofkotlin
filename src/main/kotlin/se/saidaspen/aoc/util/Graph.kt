package se.saidaspen.aoc.util

inline fun <State> bfs(start: State, isEnd: (State) -> Boolean, next: (State) -> Iterable<State>): Pair<State?, Int> {
    val seen = mutableSetOf(start)
    var queue = mutableListOf(start)
    var nextQueue = mutableListOf<State>()
    var dist = -1

    while (queue.isNotEmpty()) {
        dist++
        for (current in queue) {
            if (isEnd(current)) return current to dist
            for (i in next(current)) {
                if (seen.add(i))
                    nextQueue.add(i)
            }
        }
        val p = nextQueue
        nextQueue = queue
        queue = p
        nextQueue.clear()
    }
    return null to dist
}