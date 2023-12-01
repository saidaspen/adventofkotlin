package se.saidaspen.aoc.aoc2022

import junit.framework.TestCase.assertEquals
import org.junit.Test
import se.saidaspen.aoc.aoc2022.Day22.DIR.*
import se.saidaspen.aoc.util.P

internal class Day22Test {

    @Test
    fun testWrap() {
        // ---------------------- A - F BORDER
        assertEquals(P(P(0, 150), RIGHT), Day22.cubeWrap(P(50, 0), UP))
        assertEquals(P(P(0, 151), RIGHT), Day22.cubeWrap(P(51, 0), UP))
        assertEquals(P(P(0, 199), RIGHT), Day22.cubeWrap(P(99, 0), UP))

        assertEquals(P(P(50, 0), DOWN), Day22.cubeWrap(P(0, 150), LEFT))
        assertEquals(P(P(51, 0), DOWN), Day22.cubeWrap(P(0, 151), LEFT))
        assertEquals(P(P(99, 0), DOWN), Day22.cubeWrap(P(0, 199), LEFT))


        // ---------------------- A - E BORDER
        assertEquals(P(P(0, 149), RIGHT), Day22.cubeWrap(P(50, 0), LEFT))
        assertEquals(P(P(0, 148), RIGHT), Day22.cubeWrap(P(50, 1), LEFT))
        assertEquals(P(P(0, 100), RIGHT), Day22.cubeWrap(P(50, 49), LEFT))

        assertEquals(P(P(50, 0), RIGHT), Day22.cubeWrap(P(0, 149), LEFT))
        assertEquals(P(P(50, 1), RIGHT), Day22.cubeWrap(P(0, 148), LEFT))
        assertEquals(P(P(50, 49), RIGHT), Day22.cubeWrap(P(0, 100), LEFT))


        // ---------------------- B - F BORDER
        assertEquals(P(P(0, 199), UP), Day22.cubeWrap(P(100, 0), UP))
        assertEquals(P(P(1, 199), UP), Day22.cubeWrap(P(101, 0), UP))
        assertEquals(P(P(49, 199), UP), Day22.cubeWrap(P(149, 0), UP))

        assertEquals(P(P(100, 0), DOWN), Day22.cubeWrap(P(0, 199), DOWN))
        assertEquals(P(P(101, 0), DOWN), Day22.cubeWrap(P(1, 199), DOWN))
        assertEquals(P(P(149, 0), DOWN), Day22.cubeWrap(P(49, 199), DOWN))

        // ---------------------- B - D BORDER
        assertEquals(P(P(99, 149), LEFT), Day22.cubeWrap(P(149, 0), RIGHT))
        assertEquals(P(P(99, 148), LEFT), Day22.cubeWrap(P(149, 1), RIGHT))
        assertEquals(P(P(99, 100), LEFT), Day22.cubeWrap(P(149, 49), RIGHT))

        assertEquals(P(P(149, 49), LEFT), Day22.cubeWrap(P(99, 100), RIGHT))
        assertEquals(P(P(149, 48), LEFT), Day22.cubeWrap(P(99, 101), RIGHT))
        assertEquals(P(P(149, 0), LEFT), Day22.cubeWrap(P(99, 149), RIGHT))

        // ---------------------- B - C BORDER
        assertEquals(P(P(99, 50), LEFT), Day22.cubeWrap(P(100, 49), DOWN))
        assertEquals(P(P(99, 51), LEFT), Day22.cubeWrap(P(101, 49), DOWN))
        assertEquals(P(P(99, 99), LEFT), Day22.cubeWrap(P(149, 49), DOWN))

        assertEquals(P(P(100, 49), UP), Day22.cubeWrap(P(99, 50), RIGHT))
        assertEquals(P(P(101, 49), UP), Day22.cubeWrap(P(99, 51), RIGHT))
        assertEquals(P(P(149, 49), UP), Day22.cubeWrap(P(99, 99), RIGHT))

        // ---------------------- E - C BORDER
        assertEquals(P(P(0, 100), DOWN), Day22.cubeWrap(P(50, 50), LEFT))
        assertEquals(P(P(1, 100), DOWN), Day22.cubeWrap(P(50, 51), LEFT))
        assertEquals(P(P(49, 100), DOWN), Day22.cubeWrap(P(50, 99), LEFT))

        assertEquals(P(P(50, 50), RIGHT), Day22.cubeWrap(P(0, 100), UP))
        assertEquals(P(P(50, 51), RIGHT), Day22.cubeWrap(P(1, 100), UP))
        assertEquals(P(P(50, 99), RIGHT), Day22.cubeWrap(P(49, 100), UP))

        // ---------------------- F - D BORDER
        assertEquals(P(P(49, 150), LEFT), Day22.cubeWrap(P(50, 149), DOWN))
        assertEquals(P(P(49, 151), LEFT), Day22.cubeWrap(P(51, 149), DOWN))
        assertEquals(P(P(49, 199), LEFT), Day22.cubeWrap(P(99, 149), DOWN))

        assertEquals(P(P(50, 149), UP), Day22.cubeWrap(P(49, 150), RIGHT))
        assertEquals(P(P(51, 149), UP), Day22.cubeWrap(P(49, 151), RIGHT))
        assertEquals(P(P(99, 149), UP), Day22.cubeWrap(P(49, 199), RIGHT))
    }
}
