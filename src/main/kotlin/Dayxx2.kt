import se.saidaspen.aoc.util.*
import kotlin.math.sqrt


fun main() = Dayxx2.run()

object Dayxx2 : Day(2022, 18) {
    override fun part1(): Any {
        var input2 = "2,2,2\n" +
                "1,2,2\n" +
                "3,2,2\n" +
                "2,1,2\n" +
                "2,3,2\n" +
                "2,2,1\n" +
                "2,2,3\n" +
                "2,2,4\n" +
                "2,2,6\n" +
                "1,2,5\n" +
                "3,2,5\n" +
                "2,1,5\n" +
                "2,3,5"
        var points = input.lines().map { ints(it) }.map { Triple(it[0], it[1], it[2]) }
        var surfaceArea = points.sumOf { neightbours(it).count { n -> n notin points } }
        return surfaceArea
    }

    var dirs =
        setOf(Triple(1, 0, 0), Triple(0, 1, 0), Triple(0, 0, 1), Triple(-1, 0, 0), Triple(0, -1, 0), Triple(0, 0, -1))

    private fun neightbours(p: Triple<Int, Int, Int>): Set<Triple<Int, Int, Int>> {
        var result = mutableSetOf<Triple<Int, Int, Int>>()
        result.add(p + Triple(1, 0, 0))
        result.add(p + Triple(0, 1, 0))
        result.add(p + Triple(0, 0, 1))
        result.add(p + Triple(-1, 0, 0))
        result.add(p + Triple(0, -1, 0))
        result.add(p + Triple(0, 0, -1))
        return result
    }

    override fun part2(): Any {
        var input2 = "2,2,2\n" +
                "1,2,2\n" +
                "3,2,2\n" +
                "2,1,2\n" +
                "2,3,2\n" +
                "2,2,1\n" +
                "2,2,3\n" +
                "2,2,4\n" +
                "2,2,6\n" +
                "1,2,5\n" +
                "3,2,5\n" +
                "2,1,5\n" +
                "2,3,5"
        val points = input.lines().map { ints(it) }.map { Triple(it[0], it[1], it[2]) }
        val min = points.minBy { it.first + it.second + it.third}
        val firstAir = neightbours(min).first { it !in points }

        var queue = mutableListOf(firstAir)
        val airBlocks = mutableSetOf<Triple<Int, Int, Int>>()
        while (queue.isNotEmpty()) {
            val cur = queue.removeLast()
            airBlocks.add(cur)
            for (neighbor in neightbours(cur)) {
                if (airBlocks.contains(neighbor) || points.contains(neighbor) || queue.contains(neighbor)) continue
                //if shortest dist from any droplet is more than two, we're expanding out into infinity
                if (points.map { it.dist(neighbor) }.min() > 2) continue
                queue.add(neighbor)
            }
        }

        var surfaceArea = 0
        for (c in airBlocks) {
            var nc = neightbours(c)
            for (p in nc) {
                if (points.contains(p)){
                    surfaceArea += 1
                }
            }
        }
        return surfaceArea
    }




}

private fun Triple<Int, Int, Int>.dist(other: Triple<Int, Int, Int>): Double {
    return sqrt(
        ((this.first - other.first).pow(2)
                + (this.second - other.second).pow(2)
                + (this.third - other.third).pow(2)).toDouble()
    )
}

private operator fun  Triple<Int, Int, Int>.times(m: Int): Triple<Int, Int, Int> {
    return Triple(this.first*m, this.second *m, this.third *m)
}

private operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    return Triple(this.first + other.first, this.second + other.second, this.third + other.third)
}

private infix fun <A, B, C> Triple<A, B, C>.notin(other: List<Triple<A, B, C>>): Boolean {
    return !other.contains(this)
}