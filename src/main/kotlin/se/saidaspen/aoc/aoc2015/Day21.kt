package se.saidaspen.aoc.aoc2015

import se.saidaspen.aoc.util.Day
import se.saidaspen.aoc.util.ints
import se.saidaspen.aoc.util.words

fun main() {
    Day21.run()
}

object Day21 : Day(2015, 21) {

    data class Player(var hp: Int, val dmg : Int, val armor: Int)
    data class Object(val name: String, val cost: Int, val dmg : Int, val armor: Int)

    val weapons = parseObjects("""Dagger        8     4       0
Shortsword   10     5       0
Warhammer    25     6       0
Longsword    40     7       0
Greataxe     74     8       0""")

    val armors = parseObjects("""NoArmor       0     0       0
Leather      13     0       1
Chainmail    31     0       2
Splintmail   53     0       3
Bandedmail   75     0       4
Platemail   102     0       5""")

    val rings = parseObjects("""NoRing1       0     0       0
NoRing2       0     0       0
Damage+1    25     1       0
Damage+2    50     2       0
Damage+3   100     3       0
Defense+1   20     0       1
Defense+2   40     0       2
Defense+3   80     0       3""")


    private fun parseObjects(s: String): List<Object> {
        return s.lines().map { words(it) }.map { Object(it[0], it[1].toInt(), it[2].toInt(), it[3].toInt())}.toList()
    }

    override fun part1(): Any {
        val wins = mutableMapOf<Int, String>()
        val bossHp = ints(input)[0]
        val bossDmg = ints(input)[1]
        val bossArmor = ints(input)[2]
        for (weapon in weapons) {
            for (armor in armors) {
                for (ringL in rings) {
                    for (ringR in rings) {
                        if (ringL == ringR) continue
                        val cost = weapon.cost + armor.cost + ringL.cost + ringR.cost
                        val boss = Player(bossHp, bossDmg, bossArmor)
                        val player = Player(
                            100,
                            weapon.dmg + armor.dmg + ringL.dmg + ringR.dmg,
                            weapon.armor + armor.armor + ringL.armor + ringR.armor
                        )
                        if (player == play(player, boss)) {
                            wins[cost] = "$weapon, $armor, $ringL, $ringR"
                        }
                    }
                }
            }
        }
        return wins.minByOrNull { it.key }!!.key
    }

    private fun play(player: Player, boss: Player) : Player {
        var attacker = player
        var defender = boss
        while (true) {
            defender.hp -= (attacker.dmg - defender.armor).coerceAtLeast(1)
            if (defender.hp < 1) return attacker
            else {
                attacker = defender.also { defender = attacker }
            }
        }
    }

    override fun part2(): Any {
        val losses = mutableMapOf<Int, String>()
        val bossHp = ints(input)[0]
        val bossDmg = ints(input)[1]
        val bossArmor = ints(input)[2]
        for (weapon in weapons) {
            for (armor in armors) {
                for (ringL in rings) {
                    for (ringR in rings) {
                        if (ringL == ringR) continue
                        val cost = weapon.cost + armor.cost + ringL.cost + ringR.cost
                        val boss = Player(bossHp, bossDmg, bossArmor)
                        val player = Player(
                            100,
                            weapon.dmg + armor.dmg + ringL.dmg + ringR.dmg,
                            weapon.armor + armor.armor + ringL.armor + ringR.armor
                        )
                        if (boss == play(player, boss)) {
                            losses[cost] = "$weapon, $armor, $ringL, $ringR"
                        }
                    }
                }
            }
        }
        return losses.maxByOrNull { it.key }!!.key
    }
}
