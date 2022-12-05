package se.saidaspen.aoc.util

fun <T> Iterable<T>.disjuct(other: Iterable<T>): Set<T> {
    return (this union other) - (this intersect other)
}
fun <T> Iterable<T>.isSubset(other: Iterable<T>) = this.all { other.contains(it) }
