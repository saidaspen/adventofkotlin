package se.saidaspen.aoc.util

import java.util.*
import kotlin.math.max

@Suppress("unused", "MemberVisibilityCanBePrivate")
class Grid<T>(val default: T) {

    val width : Int
        get() = if (grid.size == 0) 0 else grid[0].size

    val height : Int
        get() = grid.size

    fun size() = width * height

    private var grid: MutableList<MutableList<T>> = mutableListOf()

    operator fun set(x: Int, y: Int, value: T) {
        if (x >= width) {
            val colsToAdd = x+1 - width
            grid.forEach { it.addAll(MutableList(colsToAdd){default}) }
        }
        if (y >= height) {
            val rowsToAdd = y+1 - height
            val newWidth = max(x+1, width)
            grid.addAll(MutableList(rowsToAdd){ MutableList(newWidth){default} })
        }
        grid[y][x] = value
    }

    operator fun set(p: P<Int, Int>, value: T) {
        set(p.first, p.second, value)
    }

    override fun toString(): String {
        return grid.joinToString("\n") { it.joinToString(" ") { elem -> elem.toString()} }
    }

    fun points() : List<P<P<Int, Int>, T>> {
        return grid.mapIndexed { r, row -> row.mapIndexed{c, v -> P(P(r, c), v)}}.flatten()
    }

    fun shiftRow(row: Int, by: Int) {
        val list = (0 until width).map { x -> grid[row][x]  }.toList()
        Collections.rotate(list, by)
        (0 until width).map { x -> set(x, row, list[x])}
    }

    fun shiftCol(col: Int, by: Int) {
        val list = (0 until height).map { y -> grid[y][col]  }.toList()
        Collections.rotate(list, by)
        (0 until height).map { y -> set(col, y, list[y]) }
    }

    // Scale each point
    fun scale(x: Int, y: Int) {
        val newHeight = y * height
        val newWidth = x * width
        val newGrid = Grid(default)

        for (col in 0 until newWidth) {
            for (row in 0 until newHeight) {
                newGrid[col, row] = grid[row/y][col/x]
            }
        }
        this.grid = newGrid.grid
    }

}
