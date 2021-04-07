package se.saidaspen.aoc.util

class Grid<T> {

    val width by lazy { if (grid.size == 0) 0 else grid[0].size }
    val height by lazy { grid.size }
    fun size() = width * height
    constructor() { grid = mutableListOf() }
    private var grid: MutableList<MutableList<T>>

    constructor(map: List<List<T>>) { grid = map.map { it.toMutableList() }.toMutableList() }

    fun flipV() = Grid(grid.reversed().map { it })
    fun flipH() = Grid(grid.map { it.reversed() })

    operator fun get(row: Int, col: Int) = grid[row][col]
    operator fun get(i: Int) = grid[i]

    fun <G> map(function: (T) -> G) = grid.flatten().map(function)
    fun <G> mapElements(function: (T) -> G) = Grid(grid.map { it.map(function) })

    override fun toString() = "[" + grid.joinToString("/") { it.joinToString("") } + "]"

    fun rotateCw(i: Int) = Grid(rotateCw(grid, i))

    private fun rotateCw(rows: MutableList<MutableList<T>>, rotations: Int): MutableList<MutableList<T>> {
        if (grid.size == 0) return grid
        val nGrid = mutableListOf<MutableList<T>>()
        for (iCol in 0 until grid[0].size) {
            val row = mutableListOf<T>()
            for (iRow in grid.size - 1 downTo 0) row.add(rows[iRow][iCol])
            nGrid.add(row)
        }
        return if (rotations == 1) nGrid else rotateCw(nGrid, rotations - 1)
    }

    fun subGrid(y: Int, x: Int, height: Int, width: Int) = Grid((y until y + height).map { grid[it].subList(x, x + width) }.toList())

    fun appendRight(right: Grid<T>)  =  Grid((0 until grid.size).map { listOf(grid[it], right[it]).flatten() }.toList())
    fun appendBelow(below: Grid<T>) = Grid(listOf(grid, below.grid).flatten())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Grid<*>
        if (grid != other.grid) return false
        return true
    }

    override fun hashCode() = grid.hashCode()
}

fun <T> gridFrom(map: T) = Grid(listOf(listOf(map)))

