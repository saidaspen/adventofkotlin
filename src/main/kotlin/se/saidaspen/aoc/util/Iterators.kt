package se.saidaspen.aoc.util


operator fun Iterable<Char>.div(s: Iterable<Char>) = this.intersect(s)

fun <T> Iterable<T>.pairWise(): List<Pair<T, T>> = flatMapIndexed { i, v -> drop(i + 1).map { v to it } }
