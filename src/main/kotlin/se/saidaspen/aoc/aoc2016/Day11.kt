package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.bfs
import se.saidaspen.aoc.util.pairWise

fun main() = Day11.run()

object Day11 : Day(2016, 11) {

    private val itemsLocs = input.lines().map {
        it.substring(it.indexOf("contains") + 8)
            .split(",|and".toRegex())
            .map { e -> e.replace(" a ", "").replace(" and", "").replace(".", "").trim() }
            .filter { e -> e != "nothing relevant" }
            .filter { e -> e.isNotEmpty() }
            .map { e -> e.split(" ")[0][0].uppercase() + e.split(" ")[0][1] + e.split(" ")[1][0].uppercase() }
            .toSet()
    }.toList()

    private val next: (State) -> Iterable<State> = { (floor, items) ->
        val nextStates = mutableListOf<Triple<Boolean, MutableList<String>, State>>()
        val pairWiseItems = items[floor].pairWise().map { mutableListOf(it.first, it.second) }
        val singleItems = items[floor].map { mutableListOf(it) }
        val possibleMoves = pairWiseItems.union(singleItems)
        val possibleFloors = mutableListOf(floor - 1, floor + 1).filter { it in 0..3 }
        for (move in possibleMoves) {
            for (nextFloor in possibleFloors) {
                val nextItems = mutableListOf(
                    items[0].toMutableSet(),
                    items[1].toMutableSet(),
                    items[2].toMutableSet(),
                    items[3].toMutableSet()
                )
                nextItems[floor].removeAll(move.toSet())
                nextItems[nextFloor].addAll(move)
                val nextState = State(nextFloor, nextItems)

                if (nextState.isValid()) {
                    nextStates.add(Triple(nextFloor > floor, move, nextState))
                }
            }
        }
        val hasMoveTwoUp = (nextStates.any { it.first && it.second.size == 2 })
        val hasMoveOneDown = (nextStates.any { !it.first && it.second.size == 1 })
        nextStates
            .filter { it.third.emptyFloorOptimization(items) } // If floors below are empty, don't move stuff back down to them
            .filter { !hasMoveTwoUp || (!it.first || it.second.size == 2) } // If can move 2 up, then don't bother moving one up
            .filter { !hasMoveOneDown || (it.first || it.second.size == 1) } // If can move 1 down, then don't bother moving two down
            .map { it.third }
    }

    data class State(val floor: Int, val items: List<Set<String>>) {
        fun isValid(): Boolean {
            for (its in items) {
                val chips = its.filter { it.endsWith("M") }
                if (chips.isNotEmpty() && its.any { it.endsWith("G") })
                    if (!chips.all { its.contains(it.substring(0, 2) + "G") }) {
                        return false
                    }
            }
            return true
        }

        fun emptyFloorOptimization(itemsPrev : List<Set<String>>): Boolean {
            val firstNonEmptyFloorPrev = itemsPrev.withIndex().first { it.value.isNotEmpty() }.index
            val firstNonEmptyFloorNext = items.withIndex().first { it.value.isNotEmpty() }.index
            if (firstNonEmptyFloorNext < firstNonEmptyFloorPrev) {
                return false
            }
            return true
        }
    }

    override fun part1(): Any {
        val isEnd: (State) -> Boolean = { it.items[3].size == itemsLocs.flatten().size } // End state is that all items are at level 3
        val bfs = bfs(State(0, itemsLocs), isEnd, next)
        return if (bfs.first != null) bfs.second else "N/A"
    }

    override fun part2(): Any {
        val extendedItems = itemsLocs.toMutableList()
        val itemsF1 = extendedItems[0].toMutableSet()
        itemsF1.addAll(setOf("ElG", "ElM", "DiG", "DiM"))
        extendedItems[0] = itemsF1
        val isEnd: (State) -> Boolean = { it.items[3].size == extendedItems.flatten().size } // End state is that all items are at level 3
        val bfs = bfs(State(0, extendedItems), isEnd, next)
        return if (bfs.first != null) bfs.second else "N/A"
    }
}
