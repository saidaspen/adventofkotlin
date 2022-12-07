package se.saidaspen.aoc.util

import kotlin.math.abs

enum class Turn(val off: Int) {
    R(1), L(3)
}

class Walker(val start: Pair<Int, Int>, var facing: Dir) {

    enum class Dir(val intRep: Int) {
        N(0),
        E(1),
        S(2),
        W(3);

        fun turn(t: Turn): Dir = ofRep((this.intRep + t.off) % 4)

        private fun ofRep(intRep: Int): Dir = when (intRep) {
            0 -> N
            1 -> E
            2 -> S
            3 -> W
            else -> throw RuntimeException("No Dir represented with $intRep")
        }
    }

    companion object {
        private val moveDirs = mapOf(Dir.N to P(0,1), Dir.E to P(1,0), Dir.S to P(0,-1), Dir.W to P(-1, 0))
    }
    var pos = start

    fun turn(t: Turn) {
        facing = facing.turn(t)
    }

    fun move(steps: Int) {
        pos += steps * moveDirs[facing]
    }

    fun distanceTo(pos: P<Int, Int>) = abs(pos.first - this.pos.first) + abs(pos.second - this.pos.second)

}
