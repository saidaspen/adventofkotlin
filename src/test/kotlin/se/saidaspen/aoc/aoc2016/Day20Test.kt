package se.saidaspen.aoc.aoc2016

import org.junit.Test
import se.saidaspen.aoc.aoc2016.Day20.split
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class Day20Test {

    @Test
    fun blocksLowerRange() {
        var tmp = split(Pair(0L, 100L), Pair(0L, 1L))
        assertTrue(tmp.size == 1)
        assertEquals(Pair(2L, 100L), tmp.first())
    }

    @Test
    fun blocksHigherRange() {
        var tmp = split(Pair(0L, 100L), Pair(98L, 101L))
        assertTrue(tmp.size == 1)
        assertEquals(Pair(0L, 97L), tmp.first())
    }

    @Test
    fun blocksAll() {
        assertTrue(split(Pair(10L, 100L), Pair(10L, 100L)).isEmpty())
        assertTrue(split(Pair(10L, 100L), Pair(4L, 101L)).isEmpty())
    }

    @Test
    fun blocksMid() {
        var tmp = split(Pair(0L, 100L), Pair(10L, 90L))
        assertTrue(tmp.size == 2)
        assertEquals(Pair(0L, 9L), tmp.first())
        assertEquals(Pair(91L, 100L), tmp[1])
    }
}