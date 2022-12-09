import se.saidaspen.aoc.util.*
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() = Day09.run()

object Day09 : Day(2022, 9) {

    override fun part1(): Any {
        var h = P(0, 0)
        var t = P(0, 0)
        val visited = mutableSetOf<P<Int, Int>>()
        for (l in input.lines()) {
            repeat(ints(l)[0]) {
                h = h.move(l[0])
                t = move(h, t)
                visited.add(t)
            }
        }
        return visited.size
    }

    override fun part2(): Any {
        val snake = MutableList(10){P(0,0)}
        val visited = mutableSetOf<P<Int, Int>>()
        for (l in input.lines()) {
            repeat(ints(l)[0]) {
                snake[0] = snake[0].move(l[0])
                for (i in 1 until snake.size) snake[i] = move(snake[i - 1], snake[i])
                visited.add(snake.last())
            }
        }
        return visited.size
    }

    private fun move(h: Pair<Int, Int>, t: Pair<Int, Int>): Pair<Int, Int> {
        val dx = h.x - t.x
        val dy = h.y - t.y
        return if (dx.absoluteValue > 1 || dy.absoluteValue > 1) { P(t.x + dx.sign, t.y + dy.sign) } else  t
    }
}






