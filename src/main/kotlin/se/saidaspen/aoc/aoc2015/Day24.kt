package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.words
import java.util.*

fun main() {
    Day24.run()
}

object Day24 : Day(2015, 24) {

    inline fun <reified T> combinations(arr: Array<T>, m: Int) = sequence {
        val n = arr.size
        val result = Array(m) { arr[0] }
        val stack = LinkedList<Int>()
        stack.push(0)
        while (stack.isNotEmpty()) {
            var resIndex = stack.size - 1;
            var arrIndex = stack.pop()

            while (arrIndex < n) {
                result[resIndex++] = arr[arrIndex++]
                stack.push(arrIndex)

                if (resIndex == m) {
                    yield(result.toList())
                    break
                }
            }
        }
    }

    override fun part1(): Any {
        val inputs = ints(input).toTypedArray()
        val target = inputs.sum() / 3
        for (length in 2..inputs.size) {
            val qe = combinations(inputs, length).filter { it.sum() ==  target}.map { it.map{ v -> v.toLong()}.reduce { acc, i -> acc*i } }.minOrNull()
            if (qe != null) return qe
        }
        return ""
    }

    override fun part2(): Any {
        val inputs = ints(input).toTypedArray()
        val target = inputs.sum() / 4
        for (length in 2..inputs.size) {
            val qe = combinations(inputs, length).filter { it.sum() ==  target}.map { it.map{ v -> v.toLong()}.reduce { acc, i -> acc*i } }.minOrNull()
            if (qe != null) return qe
        }
        return ""
    }

}
