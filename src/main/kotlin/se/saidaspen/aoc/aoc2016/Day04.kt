package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*

fun main() = Day04.run()

object Day04 : Day(2016, 4) {

    private var memo = mutableMapOf<P<String, Int>, String>()
    class Room(val letters: String, val checksum: String, val sector: Int)

    override fun part1(): Any {
        val rooms = toRooms(input)
        return rooms.filter { checksum(it) == it.checksum }.map { it.sector }.sum()
    }

    override fun part2(): Any {
        val decrypted = toRooms(input)
            .filter { checksum(it) == it.checksum }
            .map { P(decrypt(it), it.sector) }.toList()
        return decrypted.filter { it.first.startsWith("north") }.toList()[0].second
    }

    private fun toRooms(input: String): MutableList<Room> {
        val rooms = mutableListOf<Room>()
        for (line in input.lines()) {
            val m = Regex("(.+)-(\\d+)\\[(.+)]").find(line)!!
            rooms.add(Room(m.groupValues[1], m.groupValues[3], m.groupValues[2].toInt()))
        }
        return rooms
    }

    private fun checksum(t: Room): String {
        val frequencies
                = freqMap(t.letters.replace("-", ""))
        return frequencies.entries
            .sortedWith(compareBy<Map.Entry<Char, Int>> { it.value }.thenByDescending { it.key })
            .reversed().take(5).map { it.key }.joinToString("")
    }

    private fun decrypt(room: Room) : String {
        val words = room.letters.split("-")
        return words.joinToString(" ") { decryptWord(it, room.sector) }
    }

    private fun decryptWord(it: String, times: Int): String {
        if (memo.containsKey(P(it, times)))
            return memo[P(it, times)]!!
        var output = it
        for (i in 0 until times) {
            var newWord = ""
            for (c in output.chars()) newWord += if (c == 'z'.toInt()) 'a' else (c + 1).toChar()
            output = newWord
        }
        return output
    }
}

