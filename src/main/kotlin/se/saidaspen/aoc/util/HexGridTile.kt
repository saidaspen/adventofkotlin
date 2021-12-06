package se.saidaspen.aoc.util

import se.saidaspen.aoc.util.HexGridTile.Dir.*
import kotlin.math.abs

@Suppress("MemberVisibilityCanBePrivate")
/**
 * This is a representation of a hexadecimal tile in a hexadecimal grid.
 * The tiles look something like this:
 *
 *   \ n  /
 * nw +--+ ne
 *   /    \
 * -+      +-
 *   \    /
 * sw +--+ se
 *   / s  \
 *
 * They use a cubic coordinate system, as that is easier.
 * More info can be found here:
 * https://www.redblobgames.com/grids/hexagons/
 *
 */
data class HexGridTile(val x: Int, val y: Int, val z: Int) {

    enum class Dir { N, S, NE, NW, SE, SW }

    fun move(dir: Dir): HexGridTile {
        return when (dir) {
            N -> HexGridTile(x, y + 1, z - 1)
            NE -> HexGridTile(x + 1, y, z - 1)
            SE -> HexGridTile(x + 1, y - 1, z)
            S -> HexGridTile(x, y - 1, z + 1)
            SW -> HexGridTile(x - 1, y, z + 1)
            NW -> HexGridTile(x - 1, y + 1, z)
        }
    }

    fun move(dir: String) = move(valueOf(dir.toUpperCase()))
    fun distance(b: HexGridTile) = (abs(x - b.x) + abs(y - b.y) + abs(z - b.z)) / 2

    fun adjacent(): List<HexGridTile> {
        return listOf(
            this.move(NE),
            this.move(N),
            this.move(SE),
            this.move(SW),
            this.move(S),
            this.move(NW)
        )
    }
}