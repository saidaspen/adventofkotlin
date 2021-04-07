package se.saidaspen.aoc.util

import kotlin.math.abs
import kotlin.math.ceil

fun Int.cdiv(i: Int): Int = ceil(this / i.toDouble()).toInt()

fun Int.mod(s: Int): Int {
    val value = this % s
    return if (value < 0) s + value else value
}

fun Long.mod(s: Long): Long {
    val value = this % s
    return if (value < 0) s + value else value
}

fun lcm(n1: Long, n2: Long) = abs(n1 * n2) / gcd(n1, n2)

fun gcd(n1: Long, n2: Long): Long {
    var a = if (n1 > 0) n1 else -n1
    var b = if (n2 > 0) n2 else -n2
    while (a != b) {
        if (a > b) a -= b
        else b -= a
    }
    return a
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
