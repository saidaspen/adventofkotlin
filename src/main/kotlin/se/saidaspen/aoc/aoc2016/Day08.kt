package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day08.run()

object Day08 : Day(2016, 8) {

    private const val width = 50
    private const val heigh = 6

    override fun part1(): Any {
        val grid = Grid('.')
        List(width){it}.flatMap{ x -> List(heigh){it}.map { y-> x to y } }.forEach{ grid[it.first, it.second] = '.' }
        input.lines().forEach{
            when(it.split(" ")[0]) {
                "rect" -> rect(grid, it)
                "rotate" -> rotate(grid, it)
            }
        }
        return grid.points().map { it.second }.count { it == '#'}
    }

    private fun rotate(grid: Grid<Char>, it: String) {
        val splitCommand = it.split(" ")
        val isRow = splitCommand[1] == "row"
        val rowCol = ints(it)[0]
        val by = ints(it)[1]
        if (isRow) {
            grid.shiftRow(rowCol, by)
        } else {
            grid.shiftCol(rowCol, by)
        }
    }

    private fun rect(grid: Grid<Char>, it: String) {
        val rectW = ints(it)[0]
        val rectH = ints(it)[1]

        for (x in 0 until rectW) {
            for (y in 0 until rectH) {
                grid[x, y] = '#'
            }
        }
    }

    override fun part2(): Any {
        val grid = Grid('.')
        List(width){it}.flatMap{ x -> List(heigh){it}.map { y-> x to y } }.forEach{ grid[it.first, it.second] = '.' }
        input.lines().forEach{
            when(it.split(" ")[0]) {
                "rect" -> rect(grid, it)
                "rotate" -> rotate(grid, it)
            }
        }
        grid.scale(5, 6)
        println(grid)
        return "RURUCEOEIL"
    }
}
