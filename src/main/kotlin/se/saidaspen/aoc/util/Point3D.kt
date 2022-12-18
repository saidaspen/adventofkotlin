package se.saidaspen.aoc.util

import kotlin.math.sqrt


data class Point3D(val x: Int, val y: Int, val z: Int){
    companion object {
        private var dirs = setOf(Point3D(1, 0, 0), Point3D(0, 1, 0), Point3D(0, 0, 1), Point3D(-1, 0, 0), Point3D(0, -1, 0), Point3D(0, 0, -1))
        fun of(ll: List<Int>) = Point3D(ll[0], ll[1], ll[2])
    }

    fun hexNeightbours() = dirs.map { this + it }.toSet()

    private operator fun plus(that: Point3D) = Point3D(this.x + that.x, this.y + that.y, this.z + that.z)

    fun dist(other: Point3D): Double {
        return sqrt(
            ((this.x - other.x).pow(2)
                    + (this.y - other.y).pow(2)
                    + (this.z - other.z).pow(2)).toDouble()
        )
    }
}