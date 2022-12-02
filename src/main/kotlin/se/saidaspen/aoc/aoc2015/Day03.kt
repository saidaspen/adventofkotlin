package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.e
import se.saidaspen.aoc.util.move

fun main() = Day03.run()

object Day03 : Day(2015, 3) {

    override fun part1(): Any {
        var pos = P(0, 0)
        val visited = mutableSetOf(pos)
        input.e().forEach {
            pos = pos.move(it)
            visited.add(pos)
        }
        return visited.count()
    }

    enum class Turn { SANTA, ROBO }

    override fun part2(): Any {
        var santaPos = P(0, 0)
        var roboPos = P(0, 0)
        val visited = mutableSetOf(santaPos, roboPos)
        var turn = Turn.SANTA
        input.e().forEach {
            if (turn == Turn.SANTA) {
                santaPos = santaPos.move(it)
                visited.add(santaPos)
                turn = Turn.ROBO
            } else {
                roboPos = roboPos.move(it)
                visited.add(roboPos)
                turn = Turn.SANTA
            }
        }
        return visited.count()
    }
}
