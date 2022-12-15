package se.saidaspen.aoc.util

import kotlin.math.abs
import kotlin.math.sign

typealias P<A, B> = Pair<A, B>
typealias Point = P<Int, Int>

val P<Int, Any>.x: Int
    get() = this.first

val P<Any, Int>.y: Int
    get() = this.second

fun Pair<Int, Int>.move(it: Char) = when (it) {
    '^', 'U' -> this.up()
    'v', 'D' -> this.down()
    '<', 'L' -> this.left()
    '>', 'R' -> this.right()
    else -> throw RuntimeException("Unsupported char")
}

fun Pair<Int, Int>.down() = P(this.first, this.second - 1)
fun Pair<Int, Int>.up() = P(this.first, this.second + 1)
fun Pair<Int, Int>.left() = P(this.first - 1, this.second)
fun Pair<Int, Int>.right() = P(this.first + 1, this.second)

fun rectangle(it: P<P<Int, Int>, P<Int, Int>>) = rectangle(it.first, it.second)
fun rectangle(c1: P<Int, Int>, c2: P<Int, Int>): List<P<Int, Int>> {
    val leftMost = if (c1.x <= c2.x) c1.x else c2.x
    val rightMost = if (c1.x >= c2.x) c1.x else c2.x
    val bottomMost = if (c1.y <= c2.y) c1.y else c2.y
    val topMost = if (c1.y > c2.y) c1.y else c2.y
    val points = mutableListOf<P<Int, Int>>()
    for (x in leftMost..rightMost) {
        for (y in bottomMost..topMost) {
            points.add(P(x, y))
        }
    }
    return points
}

fun Point.sign() =  Point(this.x.sign, this.y.sign)

fun Point.manhattanDistTo(other: Point): Int {
    return abs(this.x - other.x) + abs(this.y - other.y)
}

fun Point.lineTo(other: Point): List<Point> {
    val step = (other - this).sign()
    val line = mutableListOf<Point>()
    var curr = this
    while (curr != other) {
        line.add(curr)
        curr += step
    }
    line.add(curr)
    return line
}