package se.saidaspen.aoc.util

fun digits(input: String) = input.filter { it.isDigit() }.map { it.toString().toInt() }.toList()
fun ints(input: String) = "-?\\d+".toRegex(RegexOption.MULTILINE).findAll(input).map { it.value.toInt() }.toList()
fun positives(input: String) = "\\d+".toRegex(RegexOption.MULTILINE).findAll(input).map { it.value.toInt() }.toList()
fun longs(input: String) = "-?\\d+".toRegex(RegexOption.MULTILINE).findAll(input).map { it.value.toLong() }.toList()
fun words(input: String) = input.trim().split("\\s+".toRegex())

fun String.replaceAll(needle: String, replacement: String): String {
    var ret = this
    for (c in needle.e()) {
        ret = ret.replace(c.toString(), replacement)
    }
    return ret
}

fun freqMap(chars: String): Map<Char, Int> {
    val freq: MutableMap<Char, Int> = HashMap()
    for (c in chars) {
        freq.putIfAbsent(c, 0)
        freq[c] = freq[c]!! + 1
    }
    return freq
}

fun String.tr(transSource: String, transTarget: String): String {
    assert(transSource.length == transTarget.length)
    val translation = transSource.toCharArray().mapIndexed { i, v -> P(v, transTarget[i]) }.toMap()
    return tr(translation)
}

fun String.tr(translations: Map<Char, Char>) =  this.toCharArray().map { translations.getOrDefault(it, it) }.joinToString("")

fun String.e() = this.toList()

fun String.lastIndexOfBefore(s: String, idx: Int): Int {
    val result =  this.length - this.reversed().indexOf(s, this.length - idx)-1
    return if (result < this.length) result else -1
}

fun String.sortChars() = this.toList().sorted().joinToString("")

operator fun String.div(s: String) = (this.e() / s.e()).joinToString()

infix fun String.notin(coll: Collection<String>) = !coll.contains(this)
infix fun String.isIn(coll: Collection<String>) = coll.contains(this)