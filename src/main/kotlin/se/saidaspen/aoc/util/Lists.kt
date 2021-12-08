package se.saidaspen.aoc.util

import java.util.*
import kotlin.collections.ArrayDeque

fun <V> List<V>.permutations(): Sequence<List<V>> {
    val underlying = this
    val factorials = IntArray(underlying.size + 1)
    factorials[0] = 1
    for (i in 1..underlying.size) {
        factorials[i] = factorials[i - 1] * i
    }
    return sequence {
        for (i in 0 until factorials[underlying.size]) {
            val temp = mutableListOf<V>()
            temp.addAll(underlying)
            val onePermutation = mutableListOf<V>()
            var positionCode = i
            for (position in underlying.size downTo 1) {
                val selected = positionCode / factorials[position - 1]
                onePermutation.add(temp[selected])
                positionCode %= factorials[position - 1]
                temp.removeAt(selected)
            }
            yield(onePermutation)
        }
    }
}

fun <E> List<E>.toArrayDeque() = ArrayDeque(this)

fun <E> MutableList<E>.rotate(i: Int) {
    Collections.rotate(this, i)
}