package se.saidaspen.aoc.aoc2022

import se.saidaspen.aoc.aoc2022.Day22.DIR.*
import se.saidaspen.aoc.aoc2022.Day22.SIDE.*
import se.saidaspen.aoc.util.*
import kotlin.collections.minBy

fun main() = Day22.run()

object Day22 : Day(2022, 22) {

    enum class DIR(val p: Point) { RIGHT(P(1, 0)), DOWN(P(0, 1)), LEFT(P(-1, 0)), UP(P(0, -1));
        fun right() = values()[(this.ordinal + 1).mod(values().size)]
        fun left() = values()[(this.ordinal - 1).mod(values().size)]
    }
    enum class SIDE { A, B, C, D, E, F }

    private val map = toMap(input.split("\n\n")[0]).entries.filter { it.value != ' ' }.associate { it.key to it.value }

    override fun part1() = solve(input.split("\n\n")[1]) { curr, dir -> planeWrap(curr, dir) }
    override fun part2() = solve(input.split("\n\n")[1]) { curr, dir -> cubeWrap(curr, dir) }

    private fun solve(fullInstr: String, next: (P<Int, Int>, DIR) -> P<P<Int, Int>, DIR>): Int {
        var instr = fullInstr
        val startPos = map.entries.filter { it.key.second == 0 }.minByOrNull { it.key.first }!!.key
        var currPos = startPos
        var currDir = RIGHT
        while (instr.isNotEmpty()) {
            val firstTurnIdx = instr.indexOfFirst { it == 'R' || it == 'L' }
            // MOVING
            if (firstTurnIdx > 0 || firstTurnIdx == -1) {
                val move = if (firstTurnIdx == -1) instr.toInt() else instr.substring(0, firstTurnIdx).toInt()
                instr = if (firstTurnIdx == -1) "" else instr.substring(firstTurnIdx)
                for (i in 0 until move) {
                    val (nextPos, nextDir) =
                        if (map.containsKey(currPos + currDir.p)) { P(currPos + currDir.p, currDir) } // Normal move
                        else { next(currPos, currDir) } //Wrap around
                    if (map[nextPos] == '#') break
                    else {
                        currPos = nextPos
                        currDir = nextDir
                    }
                }
            } else { // TURNING
                currDir = if (instr[0] == 'R') { currDir.right() } else { currDir.left() }
                instr = instr.drop(1)
            }
        }
        return 1000 * (currPos.second + 1) + 4 * (currPos.first + 1) + currDir.ordinal
    }

    private fun planeWrap(curr: P<Int, Int>, dir: DIR): P<P<Int, Int>, DIR> {
        return when (dir) {
            DOWN -> P(map.entries.filter { it.key.first == curr.first }.minBy { it.key.second }.key, dir)
            UP -> P(map.entries.filter { it.key.first == curr.first }.maxBy { it.key.second }.key, dir)
            RIGHT -> P(map.entries.filter { it.key.second == curr.second }.minBy { it.key.first }.key, dir)
            LEFT -> P(map.entries.filter { it.key.second == curr.second }.maxBy { it.key.first }.key, dir)
        }
    }

    fun cubeWrap(curr: P<Int, Int>, currDir: DIR): P<P<Int, Int>, DIR> {
        var nextDir = currDir
        val currSide = sideOf(curr)
        var nextPos = curr
        if (currSide == A && currDir == UP) {
            nextDir = RIGHT
            nextPos = P(0, 3 * 50 + curr.x - 50) // nextSide = F
        } else if (currSide == A && currDir == LEFT) {
            nextDir = RIGHT
            nextPos = P(0, 2 * 50 + (50 - curr.y - 1)) // nextSide = E
        } else if (currSide == B && currDir == UP) {
            nextDir = UP
            nextPos = P(curr.first - 100, 199) // nextSide = F
        } else if (currSide == B && currDir == RIGHT) {
            nextDir = LEFT
            nextPos = P(99, (50 - curr.second) + 2 * 50 - 1) // nextSide = D
        } else if (currSide == B && currDir == DOWN) {
            nextDir = LEFT
            nextPos = P(99, 50 + (curr.x - 2 * 50)) // nextSide = C
        } else if (currSide == C && currDir == RIGHT) {
            nextDir = UP
            nextPos = P((curr.y - 50) + 2 * 50, 49) // nextSide = B
        } else if (currSide == C && currDir == LEFT) {
            nextDir = DOWN
            nextPos = P(curr.y - 50, 100) // nextSide = E
        } else if (currSide == E && currDir == LEFT) {
            nextDir = RIGHT
            nextPos = P(50, 50 - (curr.y - 2 * 50) - 1) // nextSide = A
        } else if (currSide == E && currDir == UP) {
            nextDir = RIGHT
            nextPos = P(50, 50 + curr.x) // nextSide = C
        } else if (currSide == D && currDir == DOWN) {
            nextDir = LEFT
            nextPos = P(49, 3 * 50 + (curr.x - 50)) // nextSide = F
        } else if (currSide == D && currDir == RIGHT) {
            nextDir = LEFT
            nextPos = P(149, 50 - (curr.y - 50 * 2) - 1) // nextSide = B
        } else if (currSide == F && currDir == RIGHT) {
            nextDir = UP
            nextPos = P((curr.y - 3 * 50) + 50, 149) // nextSide = D
        } else if (currSide == F && currDir == LEFT) {
            nextDir = DOWN
            nextPos = P(50 + (curr.y - 3 * 50), 0) // nextSide = A
        } else if (currSide == F && currDir == DOWN) {
            nextDir = DOWN
            nextPos = P(curr.x + 100, 0) // nextSide = B
        }
        return P(nextPos, nextDir)
    }

    private fun sideOf(pos: Point): SIDE {
        if (pos.x in 50..99 && pos.y in 0..49) return A
        if (pos.x in 100..149 && pos.y in 0..49) return B
        if (pos.x in 50..99 && pos.y in 50..99) return C
        if (pos.x in 50..99 && pos.y in 100..149) return D
        if (pos.x in 0..49 && pos.y in 100..149) return E
        if (pos.x in 0..49 && pos.y in 150..199) return F
        throw java.lang.RuntimeException("Side does not exist for $pos")
    }
}
