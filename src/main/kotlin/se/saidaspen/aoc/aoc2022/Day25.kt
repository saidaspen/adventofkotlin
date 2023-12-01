package se.saidaspen.aoc.aoc2022
import se.saidaspen.aoc.util.*

fun main() = Day25.run()

object Day25 : Day(2022, 25) {

    override fun part1() = dec2Snafu(input.lines().map { snafu2Dec(it) }.sumOf { it })
    override fun part2() = ""

    private fun dec2Snafu(dec: Long): String {
        var left = dec
        var snafu = ""

        while (left > 0) {
            val curr = left % 5
            val (digit, chr) = when (curr) {
                0L -> P(0L, '0')
                1L -> P(1L, '1')
                2L -> P(2L, '2')
                3L -> P(-2L, '=')
                4L -> P(-1L, '-')
                else -> throw RuntimeException("Unsupported")
            }
            snafu += chr
            left -= digit
            left /= 5
        }
        return snafu.reversed()
    }

    private fun snafu2Dec(snafu: String): Long {
        return snafu.e()
            .reversed()
            .foldIndexed(0L){ i, acc, elem ->
                val digit = when(elem) {
                    '=' -> -2
                    '-' -> -1
                    '0' -> 0
                    '1' -> 1
                    '2' -> 2
                    else -> throw RuntimeException("Unsupported")
                }
                acc + digit * 5L.pow(i)
            }
    }
}

