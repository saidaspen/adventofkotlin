package se.saidaspen.aoc.util

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
            val newWidth = if (width == 0) y+1 else width
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

}
