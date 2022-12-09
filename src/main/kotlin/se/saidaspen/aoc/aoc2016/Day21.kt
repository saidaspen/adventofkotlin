package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.e
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.permutations
import java.lang.RuntimeException
import kotlin.math.absoluteValue

fun main() = Day21.run()

object Day21 : Day(2016, 21) {

    override fun part1(): Any {
        val operations = input.lines().map { toOperation(it) }.toList()
        return scramble(operations, "abcdefgh")
    }

    private fun scramble(operations: List<(String) -> String>, s: String): Any {
        var str = s
        for (op in operations) { str = op.invoke(str) }
        return str
    }

    private fun toOperation(it: String): (String) -> String {
        if (it.startsWith("swap position")) {
            return { s ->
                val (f, t) = ints(it)
                val n = s.e().toMutableList()
                val tmp = n[f]
                n[f] = n[t]
                n[t] = tmp
                n.joinToString("")
            }
        } else if (it.startsWith("swap letter")) {
            return { s ->
                val f = it.split(" ")[2]
                val t = it.split(" ")[5]
                var tmp = s.replace(f, "*")
                tmp = tmp.replace(t, f)
                tmp = tmp.replace("*", t)
                tmp
            }
        } else if (it.startsWith("rotate based")) {
            return { s ->
                val letter = it.split(" ")[6]
                val index = s.indexOf(letter)
                rotate(s, 1+ index + (if (index >= 4) +1 else 0))
            }
        } else if (it.startsWith("rotate")) {
            return { s ->
                val steps = it.split(" ")[2].toInt()
                val dir = it.split(" ")[1]
                rotate(s, if (dir == "right") steps else steps * -1)
            }
        } else if (it.startsWith("move")) {
            return { s ->
                val (f, t) = ints(it)
                val n = s.e().toMutableList()
                val elem = n.removeAt(f)
                n.add(t, elem)
                n.joinToString("")
            }
        } else if (it.startsWith("reverse")) {
            return { s ->
                val (f, t) = ints(it)
                s.substring(0, f) + s.substring(f, t + 1 ).reversed() + if(t+1 < s.length) s.substring(t+1) else ""
            }
        } else {
            throw RuntimeException("Unsupported operation")
        }
    }

    fun rotate(s: String, i: Int): String {
        val steps = i % s.length
        return if (steps > 0) {
            s.substring(s.length - steps) + s.substring(0, s.length - steps)
        } else {
            s.substring(steps.absoluteValue) + s.substring(0, steps.absoluteValue)
        }
    }

    override fun part2(): Any {
        val operations = input.lines().map { toOperation(it) }.toList()
        val possibles = permutations("abcdefgh".e(), "abcdefgh".length)
        return possibles.first { scramble(operations, it.joinToString("")) == "fbgdceah"  }.joinToString("")
    }
}
