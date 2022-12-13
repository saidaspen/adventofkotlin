package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P
import se.saidaspen.aoc.util.e
import se.saidaspen.aoc.util.toMap
import java.lang.RuntimeException

fun main() = Day13.run()

object Day13 : Day(2018, 13) {

//    val map = toMap(input)
//    val carts = mutableListOf<Cart>()
//
//    init {
//         map.entries.filter { it.value in mutableListOf('v', '^', '<', '>') }
//            .map { Cart(it.key, it.value) }.forEach { carts.add(it) }
//        carts.forEach { map[it.pos] = if (it.direction in mutableListOf('<', '>')) '-' else '|' }
//    }
//
//    class Cart(val pos : P<Int, Int>, val direction: Char) {
//        fun updatePos() {
//            when(direction) {
//                '>' ->
//                '<' ->
//                '^' ->
//                'v' ->
//            }
//        }
//    }

    override fun part1(): Any {
//        println(input)
//        val map = toMap(input)
//        val carts = map.entries.filter { it.value in mutableListOf('v', '^', '<', '>') }
//            .map { Cart(it.key, it.value) }
//        carts.forEach { map[it.pos] = if (it.direction in mutableListOf('<', '>')) '-' else '|' }
//
//        while (true) {
//            carts.forEach { it.updatePos() }
//            val crashSite = carts.map { it.pos }.groupBy { it }.entries.firstOrNull{ it.value.size == 2 }?.key
//            if (crashSite != null) {
//                return "" + crashSite.first + "," + crashSite.second
//            }
//        }
        return ""
    }

    override fun part2(): Any {
        return ""
    }
}
