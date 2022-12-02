package se.saidaspen.aoc.util


operator fun Iterable<Char>.div(s: Iterable<Char>) = this.intersect(s)
