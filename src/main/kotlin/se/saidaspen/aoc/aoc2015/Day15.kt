package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day

fun main() = Day15.run()

object Day15 : Day(2015, 15) {

    data class Ingredient(val name: String, val cap: Int, val dur: Int, val fla: Int, val tex: Int, val cal: Int)

    private val inp = input.lines().map {
        val (ing, cap, dur, fla, tex, cal) = """(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""".toRegex().find(it)!!.destructured
        Ingredient(ing, cap.toInt(), dur.toInt(), fla.toInt(), tex.toInt(), cal.toInt())
    }.toList()

    private fun score(a: Int, b: Int, c: Int, d: Int): Int {
        var score = 1
        score *= 0.coerceAtLeast(inp[0].cap * a + inp[1].cap * b + inp[2].cap * c + inp[3].cap * d)
        score *= 0.coerceAtLeast(inp[0].dur * a + inp[1].dur * b + inp[2].dur * c + inp[3].dur * d)
        score *= 0.coerceAtLeast(inp[0].fla * a + inp[1].fla * b + inp[2].fla * c + inp[3].fla * d)
        score *= 0.coerceAtLeast(inp[0].tex * a + inp[1].tex * b + inp[2].tex * c + inp[3].tex * d)
        return score
    }

    override fun part1(): Any {
        var score = 0
        for (a in 0..100) {
            for (b in 0..100 - a) {
                for (c in 0..100 - a - b) {
                    val d = 100 - a - b - c
                    score = score.coerceAtLeast(score(a, b, c, d))
                }
            }
        }
        return score
    }

    private fun calOf(a: Int, b: Int, c: Int, d: Int) = inp[0].cal * a + inp[1].cal * b + inp[2].cal * c + inp[3].cal * d

    override fun part2(): Any {
        var score = 0
        for (a in 0..100) {
            for (b in 0..100 - a) {
                for (c in 0..100 - a - b) {
                    val d = 100 - a - b - c
                    val localScore = score(a, b, c, d)
                    if (localScore > score && calOf(a, b, c, d) == 500) {
                        score = localScore
                    }
                }
            }
        }
        return score
    }
}


