package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints

fun main() {
    Day22.run()
}

object Day22 : Day(2015, 22) {
    private val costs = mapOf(
        'M' to 53,
        'D' to 73,
        'S' to 113,
        'P' to 173,
        'R' to 229
    )

    private val bossHp = ints(input)[0]
    private val bossDmg = ints(input)[1]
    private const val hp = 50
    private const val mana = 500

    data class Result(val seq: String, val cost: Int)
    data class Effect(val spell: Char, var left: Int)

    override fun part1(): Any {
        val results = mutableMapOf<String, Int>()
        while(results.values.minOrNull() == null) {
            for (i in 0..4_000_000) {
                val r = play(false)
                if (r.cost < Int.MAX_VALUE) {
                    results.putIfAbsent(r.seq, r.cost)
                }
            }
        }
        return results.values.minOrNull()!!
    }

    private fun play(hardMode : Boolean): Result {
        var hpLeft = hp
        var manaLeft = mana
        var bossHpLeft = bossHp
        var playerArmor = 0
        var seq = ""
        val effects = mutableListOf<Effect>()
        var turn = 0
        while (true) {
            val effectsIt = effects.iterator()
            while (effectsIt.hasNext()) {
                val effect = effectsIt.next()
                effect.left--
                when (effect.spell) {
                    'R' -> {
                        manaLeft += 101
                    }
                    'P' -> {
                        bossHpLeft -= 3
                        if (bossHpLeft < 1) return Result(seq, seq.toCharArray().map { costs[it]!! }.sum())
                    }
                }
                if (effect.left == 0) {
                    effectsIt.remove()
                    if (effect.spell == 'S') {
                        playerArmor -= 7
                    }
                }
            }
            if (turn % 2 == 0) {
                if (hardMode) {
                    hpLeft--
                    if (hpLeft < 1) {
                        return Result(seq, Int.MAX_VALUE)
                    }
                }
                val activeEffects = effects.map { e -> e.spell }.toList()
                val candidateSpells = costs.filter { it.value <= manaLeft }.map { it.key }.filter { !activeEffects.contains(it) }.toList()
                if (candidateSpells.isEmpty()) {
                    return Result(seq, Int.MAX_VALUE)
                }
                val s = candidateSpells.random()
                seq += s
                when (s) {
                    'M' -> {
                        bossHpLeft -= 4
                        if (bossHpLeft < 1) return Result(seq, seq.toCharArray().map { costs[it]!! }.sum())
                        manaLeft -= 53
                    }
                    'D' -> {
                        bossHpLeft -= 2
                        if (bossHpLeft < 1) return Result(seq, seq.toCharArray().map { costs[it]!! }.sum())
                        hpLeft += 2
                        manaLeft -= 73
                    }
                    'S' -> {
                        playerArmor += 7
                        effects.add(Effect('S', 6))
                        manaLeft -= 113
                    }
                    'P' -> {
                        effects.add(Effect('P', 6))
                        manaLeft -= 173
                    }
                    'R' -> {
                        effects.add(Effect('R', 5))
                        manaLeft -= 229
                    }
                }
            } else {
                hpLeft -= (bossDmg - playerArmor)
                if (hpLeft < 1) {
                    return Result(seq, Int.MAX_VALUE)
                }
            }
            turn ++
        }
    }

    override fun part2(): Any {
        val results = mutableMapOf<String, Int>()
        while(results.values.minOrNull() == null) {
            for (i in 0..4_000_000) {
                val r = play(true)
                if (r.cost < Int.MAX_VALUE) {
                    results.putIfAbsent(r.seq, r.cost)
                }
            }
        }
        return results.values.minOrNull()!!
    }
}
