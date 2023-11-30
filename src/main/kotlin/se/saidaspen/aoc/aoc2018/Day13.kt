package se.saidaspen.aoc.aoc2018

import se.saidaspen.aoc.util.*

fun main() = Day13.run()

object Day13 : Day(2018, 13) {

    private val map = toMap(input)
    private var carts = mutableListOf<Cart>()

    init {
         map.entries.filter { it.value in mutableListOf('v', '^', '<', '>') }
            .map { Cart(it.key, it.value) }.forEach { carts.add(it) }
        carts.forEach { map[it.pos] = if (it.direction in mutableListOf('<', '>')) '-' else '|' }
    }

    class Cart(var pos : P<Int, Int>, var direction: Char) {
        private var nextTurn = 0
        fun move() {
            pos = when(direction) {
                '>' -> pos + P(1, 0)
                '<' -> pos + P(-1, 0)
                '^' -> pos + P(0, -1)
                'v' -> pos + P(0, 1)
                else -> throw java.lang.RuntimeException("Unsupported move $direction")
            }
            val currTile = map[pos]!!
            if (direction == '^' && currTile == '/') direction = '>'
            else if (direction == 'v' && currTile == '/') direction = '<'
            else if (direction == '<' && currTile == '/') direction = 'v'
            else if (direction == '>' && currTile == '/') direction = '^'

            else if (direction == '>' && currTile == '\\') direction = 'v'
            else if (direction == '^' && currTile == '\\') direction = '<'
            else if (direction == 'v' && currTile == '\\') direction = '>'
            else if (direction == '<' && currTile == '\\') direction = '^'

            else if (currTile == '+')  {
                if (direction == '^') {
                    if (nextTurn == 0) direction = '<'
                    else if (nextTurn == 2) direction = '>'
                } else if (direction == 'v') {
                    if (nextTurn == 0) direction = '>'
                    else if (nextTurn == 2) direction = '<'
                } else if (direction == '<') {
                    if (nextTurn == 0) direction = 'v'
                    else if (nextTurn == 2) direction = '^'
                } else if (direction == '>') {
                    if (nextTurn == 0) direction = '^'
                    else if (nextTurn == 2) direction = 'v'
                }
                nextTurn = (nextTurn + 1) % 3
            }

        }
    }

    override fun part1(): Any {
        while(true) {
            carts = carts.sortedWith(compareBy<Cart> { it.pos.second }.thenBy {it.pos.first}).toMutableList()
            for (cart in carts) {
                cart.move()
                val collision = carts.map { it.pos }.histo().entries.firstOrNull { it.value == 2 }
                if (collision != null) { return "" + collision.key.first + "," + collision.key.second }
            }
        }
    }

    override fun part2(): Any {
        val pendingDelete = mutableSetOf<Cart>()
        while(true) {
            carts = carts.sortedWith(compareBy<Cart> { it.pos.second }.thenBy {it.pos.first}).toMutableList()
            for (cart in carts) {
                if (pendingDelete.contains(cart)) continue
                cart.move()
                val collisionPositions = carts.map { it.pos }.histo().entries.firstOrNull { it.value == 2 }?.key
                if (collisionPositions != null ) { pendingDelete.addAll(carts.filter { it.pos == collisionPositions}.toList()) }
            }
            carts.removeAll(pendingDelete)
            pendingDelete.clear()
            if (carts.size == 1) { return "" + carts[0].pos.first + "," + carts[0].pos.second }
        }
    }
}
