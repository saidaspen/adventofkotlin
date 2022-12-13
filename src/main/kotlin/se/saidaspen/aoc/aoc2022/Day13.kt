import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.P

fun main() = Day13.run()

object Day13 : Day(2022, 13) {

    class Packet (private val fixed : Int? = null, val values : MutableList<Packet> = mutableListOf()) : Comparable<Packet> {
        override fun toString() =  fixed?.toString() ?: ("[" + values.joinToString(",") + "]")

        override operator fun compareTo(other: Packet): Int {
            if (this.fixed != null && other.fixed != null) return this.fixed.compareTo(other.fixed)
            val left = if (this.fixed != null) Packet(null, mutableListOf(Packet(this.fixed))) else this
            val right = if (other.fixed != null) Packet(null, mutableListOf(Packet(other.fixed))) else other
            for (i in left.values.indices) {
                if (i >= right.values.size) return 1
                val compareI = left.values[i].compareTo(right.values[i])
                if (compareI != 0) return compareI
            }
            return left.values.size.compareTo(right.values.size)
        }

        companion object {
            fun parse(inp: String): Packet {
                val p = Packet()
                val part = inp.dropLast(1).drop(1)
                var i = 0
                var num = ""
                while (i < part.length) {
                    val c = part[i]
                    if (c.isDigit()) {
                        num += c
                    } else if (c == '[') {
                        var j = i+1
                        var cnt = 1
                        while (cnt > 0){
                            if (part[j] == ']') {
                                cnt -= 1
                            } else if (part[j] == '['){
                                cnt += 1
                            }
                            j+=1
                        }
                        val subPacket = part.substring(i, j)
                        p.values.add(parse(subPacket))
                        i = j
                        continue
                    } else if (c == ']') {
                        throw RuntimeException("Unexpected char ]")
                    } else if (c == ',' && num != "") {
                        p.values.add(Packet(num.toInt()))
                        num = ""
                    }
                    i+=1
                }
                if (num != "") {
                    p.values.add(Packet(num.toInt()))
                }
                return p
            }
        }
    }

    override fun part1(): Any {
        val packetPairs = input.split("\n\n").map { P(Packet.parse(it.lines()[0]), Packet.parse(it.lines()[1])) }
        val tmp = packetPairs.withIndex().filter { it.value.first < it.value.second }.map { it.index + 1 }
        return tmp.sumOf { it }
    }

    override fun part2(): Any {
        val packets = input.lines().filter { it != "" }.map { Packet.parse(it) }.toMutableList()
        val divider1 = Packet.parse("[[2]]")
        val divider2 = Packet.parse("[[6]]")
        packets.add(divider1)
        packets.add(divider2)
        packets.sort()
        return (packets.indexOf(divider1) +1) * (packets.indexOf(divider2) +1)
    }

}







