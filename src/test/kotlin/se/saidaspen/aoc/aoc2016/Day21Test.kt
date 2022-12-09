package se.saidaspen.aoc.aoc2016

import org.junit.Test
import se.saidaspen.aoc.aoc2016.Day21.rotate
import kotlin.test.assertEquals
import kotlin.test.assertTrue


internal class Day21Test {

    @Test
    fun rotateTest() {
        assertEquals("dabc", rotate("abcd", 1))
        assertEquals("cdab", rotate("abcd", 2))
        assertEquals("bcda", rotate("abcd", 3))
        assertEquals("abcd", rotate("abcd", 4))
        assertEquals("dabc", rotate("abcd", 5))

        assertEquals("bcda", rotate("abcd", -1))
        assertEquals("cdab", rotate("abcd", -2))
        assertEquals("dabc", rotate("abcd", -3))
        assertEquals("abcd", rotate("abcd", -4))
        assertEquals("bcda", rotate("abcd", -5))
    }

}