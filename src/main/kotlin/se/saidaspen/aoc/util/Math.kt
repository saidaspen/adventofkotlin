@file:Suppress("unused")

package se.saidaspen.aoc.util

import kotlin.math.ceil

fun gcd(a:Int, b:Int):Int = if(a == 0) b else gcd(b % a, a)
fun gcd(a:Long, b:Long):Long = if(a == 0L) b else gcd(b % a, a)

@Suppress("RemoveExplicitTypeArguments")
fun Iterable<Int>.gcd():Int = reduce<Int,Int>(::gcd)
@JvmName("longGcd")
@Suppress("RemoveExplicitTypeArguments")
fun Iterable<Long>.gcd():Long = reduce<Long, Long>(::gcd)

fun lcm(a:Int, b:Int):Int = a / gcd(a,b) * b
fun lcm(a:Long, b:Long):Long = a / gcd(a,b) * b

@Suppress("RemoveExplicitTypeArguments")
fun Iterable<Int>.lcm():Int = reduce<Int, Int>(::lcm)
@JvmName("longLcm")
@Suppress("RemoveExplicitTypeArguments")
fun Iterable<Long>.lcm():Long = reduce<Long, Long>(::lcm)

fun <T : Comparable<T>>Iterable<T>.min():T = minOrNull()!!
fun <T : Comparable<T>>Iterable<T>.max():T = maxOrNull()!!

fun <T : Comparable<T>>Array<T>.min():T = minOrNull()!!
fun <T : Comparable<T>>Array<T>.max():T = maxOrNull()!!

fun IntArray.min():Int = minOrNull()!!
fun IntArray.max():Int = maxOrNull()!!

fun LongArray.min():Long = minOrNull()!!
fun LongArray.max():Long = maxOrNull()!!

fun DoubleArray.min():Double = minOrNull()!!
fun DoubleArray.max():Double = maxOrNull()!!

inline fun <C, T : Comparable<T>>Iterable<C>.minBy(b:(C) -> T):C = minByOrNull(b)!!
inline fun <C, T : Comparable<T>>Iterable<C>.maxBy(b:(C) -> T):C = maxByOrNull(b)!!

inline fun <C, T : Comparable<T>>Iterable<C>.allMinBy(b:(C) -> T):List<C> {
    val min = minBy(b)
    return filter { b(it) == b(min) }
}
inline fun <C, T : Comparable<T>>Iterable<C>.allMaxBy(b:(C) -> T):List<C> {
    val max = maxBy(b)
    return filter { b(it) == b(max) }
}

inline fun <C, T : Comparable<T>>Array<C>.minBy(b:(C) -> T):C = minByOrNull(b)!!
inline fun <C, T : Comparable<T>>Array<C>.maxBy(b:(C) -> T):C = maxByOrNull(b)!!

inline fun <C, T : Comparable<T>>Array<C>.allMinBy(b:(C) -> T):List<C> {
    val min = minBy(b)
    return filter { b(it) == b(min) }
}

inline fun <C, T : Comparable<T>>Array<C>.allMaxBy(b:(C) -> T):List<C> {
    val max = maxBy(b)
    return filter { b(it) == b(max) }
}

inline fun <C : Comparable<C>>IntArray.minBy(b:(Int) -> C):Int = minByOrNull(b)!!
inline fun <C : Comparable<C>>IntArray.maxBy(b:(Int) -> C):Int = maxByOrNull(b)!!

inline fun <C : Comparable<C>>IntArray.allMinBy(b:(Int) -> C):List<Int> {
    val min = minBy(b)
    return filter { b(it) == b(min) }
}

inline fun <C : Comparable<C>>IntArray.allMaxBy(b:(Int) -> C):List<Int> {
    val max = maxBy(b)
    return filter { b(it) == b(max) }
}

inline fun <C : Comparable<C>>LongArray.minBy(b:(Long) -> C):Long = minByOrNull(b)!!
inline fun <C : Comparable<C>>LongArray.maxBy(b:(Long) -> C):Long = maxByOrNull(b)!!

inline fun <C : Comparable<C>>LongArray.allMinBy(b:(Long) -> C):List<Long> {
    val min = minBy(b)
    return filter { b(it) == b(min) }
}

inline fun <C : Comparable<C>>LongArray.allMaxBy(b:(Long) -> C):List<Long> {
    val max = maxBy(b)
    return filter { b(it) == b(max) }
}

inline fun <C : Comparable<C>>DoubleArray.minBy(b: (Double) -> C):Double = minByOrNull(b)!!
inline fun <C : Comparable<C>>DoubleArray.maxBy(b: (Double) -> C):Double = maxByOrNull(b)!!

inline fun <C : Comparable<C>>DoubleArray.allMinBy(b: (Double) -> C):List<Double> {
    val min = minBy(b)
    return filter { b(it) == b(min) }
}
inline fun <C : Comparable<C>>DoubleArray.allMaxBy(b: (Double) -> C):List<Double> {
    val max = maxBy(b)
    return filter { b(it) == b(max) }
}

@JvmName("minMaxVararg")
fun <T : Comparable<T>>minMax(vararg e:T):Pair<T, T> = e.min() to e.max()
fun <T : Comparable<T>>Iterable<T>.minMax():Pair<T, T> = min() to max()
fun <T : Comparable<T>>Array<T>.minMax():Pair<T, T> = min() to max()
fun IntArray.minMax():Pair<Int, Int> = min() to max()
fun LongArray.minMax():Pair<Long, Long> = min() to max()
fun DoubleArray.minMax():Pair<Double, Double> = min() to max()

@JvmName("minMaxIRIntsVararg")
fun minMaxIR(vararg e:Int):IntRange = e.min() .. e.max()
@JvmName("minMaxIRInts")
fun Iterable<Int>.minMaxIR():IntRange = min() .. max()
@JvmName("minMaxIRInts")
fun Array<Int>.minMaxIR():IntRange = min() .. max()
@JvmName("minMaxIRInts")
fun IntArray.minMaxIR():IntRange = min() .. max()

@JvmName("minMaxIRLongsVararg")
fun minMaxIR(vararg e:Long):LongRange = e.min() .. e.max()
@JvmName("minMaxIRLongs")
fun Iterable<Long>.minMaxIR():LongRange = min() .. max()
@JvmName("minMaxIRLongs")
fun Array<Long>.minMaxIR():LongRange = min() .. max()
@JvmName("minMaxIRLongs")
fun LongArray.minMaxIR():LongRange = min() .. max()

fun Int.pow(x: Int): Int = when {
    x == 0 -> 1
    x == 1 -> this
    x % 2 == 0 -> (this * this).pow(x / 2)
    else -> (this * this).pow(x / 2) * this
}

fun Long.pow(x: Int): Long = when {
    x == 0 -> 1
    x == 1 -> this
    x % 2 == 0 -> (this * this).pow(x / 2)
    else -> (this * this).pow(x / 2) *this
}

fun Long.powMod(x: Int, y:Long): Long = when {
    x == 0 -> 1
    x == 1 -> this % y
    x % 2 == 0 -> (this * this % y).powMod(x / 2, y)
    else -> (this * this % y).powMod(x / 2, y) * this % y
}

fun Int.primeFactors(): List<Pair<Int, Int>> {
    val factors = mutableListOf<Pair<Int, Int>>()
    var n = this
    for (i in 2..n) {
        var count = 0
        while (n % i == 0) {
            count++
            n /= i
        }
        if (count > 0) factors.add(i to count)
    }
    return factors
}

fun Int.allDivisors(): List<Int> {
    val factors = primeFactors()
    var divisors = listOf(1)

    for ((factor, count) in factors) {
        divisors = divisors.flatMap { d -> (0..count).map { d * factor.pow(it) } }
    }

    return divisors
}

fun Int.cdiv(i: Int): Int = ceil(this / i.toDouble()).toInt()

fun Int.mod(s: Int): Int {
    val value = this % s
    return if (value < 0) s + value else value
}

fun Long.mod(s: Long): Long {
    val value = this % s
    return if (value < 0) s + value else value
}


fun multInv(a: Long, b: Long): Long {
    if (b == 1L) return 1L
    var aa = a
    var bb = b
    var x0 = 0L
    var x1 = 1L
    while (aa > 1L) {
        val q = aa / bb
        var t = bb
        bb = aa % bb
        aa = t
        t = x0
        x0 = x1 - q * x0
        x1 = t
    }
    if (x1 < 0) x1 += b
    return x1
}

fun Int.toBinary(len: Int): String {
    return String.format("%" + len + "s", this.toString(2)).replace(" ".toRegex(), "0")
}

fun chineseRemainder(n: LongArray, a: LongArray): Long {
    val prod = n.fold(1L) { acc, i -> acc * i }
    var sum = 0L
    for (i in n.indices) {
        val p = prod / n[i]
        sum += a[i] * multInv(p, n[i]) * p
    }
    return sum % prod
}