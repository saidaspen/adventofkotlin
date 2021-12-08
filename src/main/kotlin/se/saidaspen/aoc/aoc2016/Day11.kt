package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day


fun main() = Day11.run()

data class State(val elevatorFloor: Int, val floors: List<Set<String>>)

object Day11 : Day(2016, 11) {
    override fun part1(): Any {
        println(input)
        var testInp = """
           The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
           The second floor contains a hydrogen generator.
           The third floor contains a lithium generator.
           The fourth floor contains nothing relevant.
        """.trimIndent()
        input = testInp
        val floorStuff = input
            .lines()
            .map { it.replace("and", ",").replace(".", "").substring(it.indexOf("contains") + "contains".length) }
            .map {
                it.split(",").map { it.replace("nothing relevant", "").replace("a ", "").trim() }.filter { it != "" }
                    .map { it.split(" ")[0].substring(0, 2).toUpperCase() + it.split(" ")[1][0].toUpperCase() }.sorted()
                    .toMutableList()
            }
        val startState =
            State(0, listOf(floorStuff[0].toSet(), floorStuff[1].toSet(), floorStuff[2].toSet(), floorStuff[3].toSet()))
        val endState = State(3, listOf(setOf(), setOf(), setOf(), floorStuff.flatten().toSet()))
        val steps = mutableMapOf<State, Int>()
        steps[startState] = 0
        val queue = mutableListOf<State>()
        queue.add(startState)
        while (queue.size != 0) {
            val state = queue.removeAt(0)
            val neighbours = findNeighbours(state)
            val newRoute = steps[state]!! + 1
            for (n in neighbours) {
                val lastFastest = steps[n]
                if (lastFastest != null) {
                    if (lastFastest > newRoute) {
                        steps[n] = newRoute
                    }
                } else {
                    steps[n] = newRoute
                    queue.add(n)
                }
            }
        }
        return steps[endState]!!
    }

    private fun findNeighbours(node: State): List<State> {
        val possibleFloors = mutableListOf(node.elevatorFloor - 1, node.elevatorFloor + 1).filter { it in 1..4 }
        val possibleToTakeOne = node.floors[node.elevatorFloor].toList()
        val possibleToTakeTwo = mutableListOf<List<String>>()

        for (i1 in 0 until possibleToTakeOne.size - 1) {
            for (i2 in i1+1 until possibleToTakeOne.size) {
                possibleToTakeTwo.add(listOf(possibleToTakeOne[i1], possibleToTakeOne[i2]))
            }
        }
        var candidates =
            possibleToTakeOne.map { listOf(it) }.union(possibleToTakeTwo).zip(possibleFloors).map { toState(node, it) }
        return candidates.filter { isValid(it) }

    }

    private fun isValid(s: State): Boolean {
        val floorItems = s.floors[s.elevatorFloor]
        val microChips = floorItems.filter { it.toCharArray().last() == 'M' }
        return floorItems.containsAll(microChips.map { it.dropLast(0) + 'G' })
    }

    private fun toState(node: State, move: Pair<List<String>, Int>): State {
        val newFloor = move.second
        val newFloors = mutableListOf<Set<String>>()
        for (f in 0 until 3) {
            if (f == node.elevatorFloor) {
                newFloors.add(node.floors[node.elevatorFloor] - move.first.toSet())
            } else if (f == newFloor) {
                newFloors.add(node.floors[node.elevatorFloor] + move.first.toSet())
            } else {
                newFloors.add(node.floors[node.elevatorFloor])
            }
        }
        return State(newFloor, newFloors)
    }

    override fun part2(): Any {
        return ""
    }
}
