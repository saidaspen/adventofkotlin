package se.saidaspen.aoc.aoc2021

import org.junit.Assert.assertEquals
import org.junit.Test
import se.saidaspen.aoc.aoc2021.SnailFishNumber.SFPair.Companion.parse

internal class Day18Test {

    @Test
    fun testParse() {
        val tests = mutableListOf("8", "11", "[1,2]", "[[1,2],9]", "[[1,2],[9,0]]")
        tests.forEach {
            assertEquals(it, parse(it).toString())
        }
    }

    @Test
    fun testSplit() {
        assertEquals("[[5,5],2]", parse("[10,2]").split().toString())
        assertEquals("[[5,6],1]", parse("[11,1]").split().toString())
        assertEquals("[[5,6],13]", parse("[11,13]").split().toString())
        assertEquals("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", parse("[[[[0,7],4],[15,[0,13]]],[1,1]]").split().toString())
    }

    @Test
    fun testExplode() {
        val tests = mutableListOf(
            Pair("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]"),
            Pair("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]"),
            Pair("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]"),
            Pair("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"),
            Pair("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"),
            Pair("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
        )
        for (test in tests) {
            val parsed = parse(test.first)
            assertEquals(test.second, parsed.explode().toString())
        }
    }

    @Test
    fun testReduce() {
        val tests = mutableListOf(
            Pair("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
        )
        for (test in tests) {
            val parsed = parse(test.first)
            assertEquals(test.second, parsed.reduce().toString())
        }
    }
}
