import se.saidaspen.aoc.util.*

fun main() = Dayxx.run()

object Dayxx : Day(2022, 25) {

    override fun part1(): Any {
        var temp = "1=-0-2\n" +
                "12111\n" +
                "2=0=\n" +
                "21\n" +
                "2=01\n" +
                "111\n" +
                "20012\n" +
                "112\n" +
                "1=-1=\n" +
                "1-12\n" +
                "12\n" +
                "1=\n" +
                "122"
        return dec2Snafu(input.lines().map { snafu2Dec(it) }.sumOf { it })
    }

    public fun dec2Snafu(dec: Long): String {
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

    var mults = mutableMapOf('-' to -1, '=' to -2)

    fun snafu2Dec(snafu: String): Long {
        return snafu.e()
            .reversed()
            .foldIndexed(0L){ index, total, item ->
                var digit = when(item) {
                    '=' -> -2
                    '-' -> -1
                    '0' -> 0
                    '1' -> 1
                    '2' -> 2
                    else -> throw RuntimeException("Unsupported")
                }
               total + digit * 5L.pow(index)
            }

            //.sumOf { 5.pow(it.index) * (if (it.value in mults.keys) mults[it.value] else it.value.digitToInt())!! }
    }

    override fun part2(): Any {
        return ""
    }

}
