package se.saidaspen.aoc.util

import kotlin.math.max
import kotlin.math.min

fun LongRange.exceptRange(srcRange: LongRange): List<LongRange> {
    val below = if (this.first < srcRange.first) this.first ..min(this.last, srcRange.first - 1) else null
    val above = if (this.last > srcRange.last) max(srcRange.last + 1, this.first).. this.last else null
    return listOfNotNull(below, above)
}
fun LongRange.shift(steps: Long) = this.first + steps .. this.last + steps
fun LongRange.intersectRange(that: LongRange) : LongRange? = if (this.first <= that.last && this.last >= that.first)
    max(that.first, this.first).. min(that.last, this.last) else null

fun IntRange.exceptRange(srcRange: IntRange): List<IntRange> {
    val below = if (this.first < srcRange.first) this.first .. min(this.last, srcRange.first - 1) else null
    val above = if (this.last > srcRange.last) max(srcRange.last + 1, this.first).. this.last else null
    return listOfNotNull(below, above)
}

fun IntRange.shift(steps: Int) = this.first + steps .. this.last + steps
fun IntRange.intersectRange(that: IntRange) : IntRange? = if (this.first <= that.last && this.last >= that.first) max(that.first, this.first)..min(that.last, this.last) else null


