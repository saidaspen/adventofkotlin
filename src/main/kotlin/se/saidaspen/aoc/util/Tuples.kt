package se.saidaspen.aoc.util

typealias P<A, B> = Pair<A, B>

data class P3<T>(val x: T, val y: T, val z: T)
data class P4<T>(val x: T, val y: T, val z: T, val w: T)


operator fun P3<Int>.plus(that: P3<Int>) = P3(this.x + that.x, this.y + that.y, this.z + that.z)
operator fun P3<Int>.minus(that: P3<Int>) = P3(this.x - that.x, this.y - that.y, this.z - that.z)
operator fun P4<Int>.plus(that: P4<Int>) = P4(this.x + that.x, this.y + that.y, this.z + that.z, this.w + that.w)
operator fun P4<Int>.minus(that: P4<Int>) = P4(this.x - that.x, this.y - that.y, this.z - that.z, this.w - that.w)
operator fun Pair<Int, Int>.plus(that: Pair<Int, Int>) = Pair(this.first + that.first, this.second + that.second)
operator fun Pair<Int, Int>.minus(that: Pair<Int, Int>) = Pair(this.first - that.first, this.second - that.second)

operator fun Int.times(inp: P<Int, Int>?): P<Int, Int> = P(inp!!.first * this, inp.second * this)
operator fun Int.times(inp: P3<Int>?): P3<Int> = P3(inp!!.x * this, inp.y * this, inp.z * this)
operator fun Int.times(inp: P4<Int>?): P4<Int> = P4(inp!!.x * this, inp.y * this, inp.z * this, inp.w * this)

val P<Int, Any>.x: Int
    get() = this.first

val P<Any, Int>.y: Int
    get() = this.second


