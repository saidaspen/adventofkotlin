import se.saidaspen.aoc.util.getInput
import se.saidaspen.aoc.util.ints


var input = getInput(2022, 1, true)
var sum = input.split("\n\n").map { ints(it).sum() }.maxOf { it }

sum