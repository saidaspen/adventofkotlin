package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.aoc2021.SnailFishNumber.SFPair.Companion.parse
import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.permutations
import kotlin.math.ceil
import kotlin.math.floor

fun main() = Day18.run()

object Day18 : Day(2021, 18) {
    override fun part1() = input.lines()
        .map { parse(it) }
        .reduce { acc, snailFishNumber -> (acc + snailFishNumber).reduce() }
        .magnitude()

    override fun part2() = permutations(input.lines(), length = 2)
        .maxOf { (parse(it[0]) + parse(it[1])).reduce().magnitude() }
}

sealed class SnailFishNumber(var parent: SFPair? = null) {

    fun magnitude(): Long = when (this) {
        is Constant -> value
        is SFPair -> left.magnitude() * 3 + right.magnitude() * 2
    }

    operator fun plus(other: SnailFishNumber) = SFPair(this, other)

    fun reduce(): SnailFishNumber {
        while (true) {
            val beforeExplode = this.toString()
            explode()
            if (beforeExplode != this.toString())
                continue
            val beforeSplit = this.toString()
            split()
            if (beforeSplit != this.toString())
                continue
            break
        }
        return this
    }

    fun explode(): SnailFishNumber {
        val candidate = findExplodeCandidate()
        if (candidate != null) {
            notSideParent(candidate, SFPair::left)?.left?.most(SFPair::right)?.let {
                it.value += (candidate.left as Constant).value
            }
            notSideParent(candidate, SFPair::right)?.right?.most(SFPair::left)?.let {
                it.value += (candidate.right as Constant).value
            }
            candidate.replace(Constant(0))
        }
        return this
    }

    private fun most(side: SFPair.() -> SnailFishNumber): Constant = when (this) {
        is Constant -> this
        is SFPair -> this.side().most(side)
    }

    private fun notSideParent(n: SnailFishNumber, side: SFPair.() -> SnailFishNumber): SFPair? {
        return if (n.parent == null) null
        else if (n.parent!!.side() !== n) n.parent
        else notSideParent(n.parent!!, side)
    }

    fun split(): SnailFishNumber {
        val candidate = findSplitCandidate()
        if (candidate != null) {
            val left = Constant(floor(candidate.value / 2.0).toLong())
            val right = Constant(ceil(candidate.value / 2.0).toLong())
            candidate.replace(SFPair(left, right))
        }
        return this
    }

    private fun findExplodeCandidate(lvl: Int = 0): SFPair? {
        return when (this) {
            is Constant -> null
            is SFPair -> {
                if (lvl == 4) {
                    this
                } else {
                    this.left.findExplodeCandidate(lvl + 1) ?: this.right.findExplodeCandidate(lvl + 1)
                }
            }
        }
    }

    private fun findSplitCandidate(): Constant? {
        return when (this) {
            is Constant -> if (value >= 10) this else null
            is SFPair -> left.findSplitCandidate() ?: right.findSplitCandidate()
        }
    }

    data class Constant(var value: Long) : SnailFishNumber() {
        override fun toString() = value.toString()
    }

    fun replace(new: SnailFishNumber) {
        if (this.parent!!.left == this) {
            this.parent!!.left = new
        } else {
            this.parent!!.right = new
        }
        new.parent = this.parent!!
    }

    data class SFPair(var left: SnailFishNumber, var right: SnailFishNumber) : SnailFishNumber() {
        init {
            left.parent = this
            right.parent = this
        }

        override fun toString() = "[$left,$right]"

        companion object {
            fun parse(str: String): SnailFishNumber {
                val commaPos = findComma(str)
                return if (commaPos != null) {
                    val left = str.substring(0, commaPos).drop(1)
                    val right = str.substring(commaPos + 1).dropLast(1)
                    return SFPair(parse(left), parse(right))
                } else Constant(str.toLong())
            }

            private fun findComma(str: String): Int? {
                var lvl = 0
                for ((i, c) in str.toCharArray().withIndex()) {
                    when (c) {
                        '[' -> lvl++
                        ']' -> lvl--
                        ',' -> {
                            if (lvl == 1) {
                                return i
                            }
                        }
                    }
                }
                return null
            }
        }
    }
}









