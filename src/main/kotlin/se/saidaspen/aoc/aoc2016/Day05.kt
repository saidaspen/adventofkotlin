package se.saidaspen.aoc.aoc2016

import se.saidaspen.aoc.util.*
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

fun main() = Day05.run()

object Day05 : Day(2016, 5) {

    override fun part1(): Any {
        var i = 0
        var password = ""
        val hasher = MessageDigest.getInstance("MD5")
        while(true){
            if (password.length == 8) break
            val hashed = hasher.digest("$input${i++}".toByteArray(UTF_8)).toHex()
            if (hashed.startsWith("00000")) {
                password += hashed[5]
            }
        }
        return password
    }

    override fun part2(): Any {
        var i = 0
        val password = charArrayOf('_', '_', '_', '_', '_', '_', '_', '_')
        val hasher = MessageDigest.getInstance("MD5")
        while(true){
            if (password.all { it != '_' }) break
            val hashed = hasher.digest("$input${i++}".toByteArray(UTF_8)).toHex()
            if (hashed.startsWith("00000")) {
                val pos = hashed[5].toString().toIntOrNull() ?: -1
                if (pos in 0..7 && password[pos] == '_'){
                    password[pos] = hashed[6]
                }
            }
        }
        return password.joinToString("")
    }
}

