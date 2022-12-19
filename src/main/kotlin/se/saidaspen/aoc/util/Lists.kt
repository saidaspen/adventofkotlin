package se.saidaspen.aoc.util

import java.util.*
import kotlin.collections.ArrayDeque

fun <E> permutations(list: List<E>, length: Int? = null): Sequence<List<E>> = sequence {
    val n = list.size
    val r = length ?: list.size

    val indices = list.indices.toMutableList()
    val cycles = (n downTo (n - r)).toMutableList()
    yield(indices.take(r).map { list[it] })

    while (true) {
        var broke = false
        for (i in (r - 1) downTo 0) {
            cycles[i]--
            if (cycles[i] == 0) {
                val end = indices[i]
                for (j in i until indices.size - 1) {
                    indices[j] = indices[j + 1]
                }
                indices[indices.size - 1] = end
                cycles[i] = n - i
            } else {
                val j = cycles[i]
                val tmp = indices[i]
                indices[i] = indices[-j + indices.size]
                indices[-j + indices.size] = tmp
                yield(indices.take(r).map { list[it] })
                broke = true
                break
            }
        }
        if (!broke) {
            break
        }
    }
}

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

inline fun <reified T> combinations(arr: Array<T>, m: Int) = sequence {
    val n = arr.size
    val result = Array(m) { arr[0] }
    val stack = LinkedList<Int>()
    stack.push(0)
    while (stack.isNotEmpty()) {
        var resIndex = stack.size - 1
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

fun <E> List<E>.toArrayDeque() = ArrayDeque(this)
fun <E> List<E>.histo() = this.groupingBy { it }.eachCount()
fun <E> MutableList<E>.rotate(i: Int) = Collections.rotate(this, i)

fun List<Int>.elemAdd(other: List<Int>) = this.mapIndexed { i, e -> e + other[i] }
fun List<Int>.elemAdd(vararg other: Int) = this.mapIndexed { i, e -> e + other[i] }
fun List<Int>.elemSubtract(other: List<Int>) = this.mapIndexed { i, e -> e - other[i] }
fun <E> List<E>.subList(fromIndex: Int) = this.subList(fromIndex, this.size - 1)