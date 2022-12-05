package se.saidaspen.aoc.util

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class UtilKtTest {

    @Test
    fun removeLast() {
        val list = mutableListOf<Int>(1, 2, 3, 4, 5)
        assertEquals(mutableListOf(5), list.removeLast(1))
        assertEquals(mutableListOf(3, 4), list.removeLast(2))
    }
}