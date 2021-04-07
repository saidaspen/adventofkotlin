package se.saidaspen.aoc.util

fun digits(input: String) = input.filter { it.isDigit() }.map { it.toString().toInt() }.toList()

fun ints(input: String) = "-?\\d+".toRegex(RegexOption.MULTILINE).findAll(input).map { it.value.toInt() }.toList()
fun positives(input: String) = "\\d+".toRegex(RegexOption.MULTILINE).findAll(input).map { it.value.toInt() }.toList()
fun longs(input: String) = "-?\\d+".toRegex(RegexOption.MULTILINE).findAll(input).map { it.value.toLong() }.toList()

fun consecutiveGroups(value: String): List<String> {
    val groups = mutableListOf<String>()
    var curr = ""
    for (c in value.toCharArray()) {
        if (curr.isEmpty() || c == curr.last()) curr += c
        else {
            groups.add(curr)
            curr = c.toString()
        }
    }
    if (curr.isNotEmpty()) groups.add(curr)
    return groups
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

fun String.lastIndexOfBefore(s: String, idx: Int): Int {
    val result =  this.length - this.reversed().indexOf(s, this.length - idx)-1
    return if (result < this.length) result else -1
}
