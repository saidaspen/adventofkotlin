package se.saidaspen.aoc.aoc2021

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.StringTokenizer
import se.saidaspen.aoc.util.toBinary
import java.lang.RuntimeException

fun main() = Day16.run()

object Day16 : Day(2021, 16) {

    override fun part1(): Any {
        val decoder = PacketDecoder()
        decoder.decode(input)
        return decoder.versionSum
    }

    override fun part2(): Any {
        val decoder = PacketDecoder()
        return decoder.decode(input)
    }
}

class PacketDecoder {
    private var stack = mutableListOf<Long>()
    var versionSum = 0

    fun decode(hex: String): Long {
        val binIn = hex.toCharArray().toList().joinToString("") { Integer.parseInt(it.toString(), 16).toBinary(4) }
        val tok = StringTokenizer(binIn)
        fun readPackage(): Int {
            val leftBefore = tok.left()
            val version = tok.take(3).toInt(2)
            versionSum += version
            val typeId = tok.take(3).toInt(2)
            if (typeId == 4) {  // Literal value
                var tmp = ""
                var continueFlag: String
                do {
                    continueFlag = tok.take(1)
                    tmp += tok.take(4)
                } while (continueFlag == "1")
                stack.add(tmp.toLong(2))
            } else {
                val lenTypeId = tok.take(1)
                var subPackages = 0
                if (lenTypeId == "0") {
                    val packageLength = tok.take(15).toInt(2)
                    var tRead = 0
                    while (tRead < packageLength) {
                        tRead += readPackage()
                        subPackages++
                    }
                } else {
                    val numberOfSubPackages = tok.take(11).toInt(2)
                    repeat(numberOfSubPackages) {
                        readPackage()
                        subPackages++
                    }
                }

                val operands = stack.takeLast(subPackages)
                stack = stack.dropLast(subPackages).toMutableList()
                val value: Long = when (typeId) {
                    0 -> operands.sumOf { it }
                    1 -> operands.fold(1L){ acc, i -> acc * i}
                    2 -> operands.minOf { it }
                    3 -> operands.maxOf { it }
                    5 -> if (operands[0] > operands[1]) 1 else 0
                    6 -> if (operands[0] < operands[1]) 1 else 0
                    7 -> if (operands[0] == operands[1]) 1 else 0
                    else -> throw RuntimeException("Unsupported type $typeId")
                }
                stack.add(value)
            }
            return leftBefore - tok.left()
        }
        while (tok.toString().any { it == '1' }) {
            readPackage()
        }
        return stack[0]
    }
}

data class Packet(val version: Int, val type: PacketType, val value: Long? = null, val sub: MutableList<Packet> = mutableListOf()) {
    val versionSum: Int
        get() = version + sub.sumOf { it.versionSum }

    fun eval() : Long = when(type) {
        PacketType.LIT -> value!!
        PacketType.SUM -> sub.sumOf { it.eval() }
        PacketType.PROD -> sub.fold(1L) { acc, n -> acc * n.eval() }
        PacketType.MIN -> sub.minOf { it.eval() }
        PacketType.MAX -> sub.maxOf { it.eval() }
        PacketType.GT -> sub.let { (a, b) -> if(a.eval() > b.eval()) 1 else 0 }
        PacketType.LT -> sub.let { (a, b) -> if(a.eval() < b.eval()) 1 else 0 }
        PacketType.EQ -> sub.let { (a, b) -> if(a.eval() == b.eval()) 1 else 0 }
    }
}

enum class PacketType {
    SUM,
    PROD,
    MIN,
    MAX,
    LIT,
    GT,
    LT,
    EQ
}












