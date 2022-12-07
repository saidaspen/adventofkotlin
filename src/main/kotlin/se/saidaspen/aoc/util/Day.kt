package se.saidaspen.aoc.util

import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

abstract class Day(private val year: Int, private val day: Int) {

    var input = getInput(year, day, true)

    abstract fun part1(): Any
    abstract fun part2(): Any

    fun part1(input: String): Any {
        this.input = input
        return part1()
    }

    fun part2(input: String): Any {
        this.input = input
        return part2()
    }

    fun run() {
        val result1 = part1().toString()
        if (result1.isEmpty()) return
        println("Part 1: $result1")
        val complete1 = handleSubmit(result1, PART.ONE)
        val result2 = part2().toString()
        if (result2.isEmpty()) return
        if (complete1 && result2.isNotEmpty()) {
            println("Part 2: $result2 ")
            handleSubmit(result2, PART.TWO)
        }
    }

    public fun prevCorrectValue(part: PART): String? {
        val status = getStatus(part)
        val prevRight = status.submissions.firstOrNull { it.result == Result.RIGHT }
        return prevRight?.value
    }

    private fun handleSubmit(value: String, part: PART): Boolean {
        val status = getStatus(part)

        val prevRight = status.submissions.firstOrNull { it.result == Result.RIGHT }
        if (prevRight != null) {
            if (prevRight.value != value) {
                println("Value $value NOT EQUAL TO previous right value: ${prevRight.value}")
            }
            return true
        }
        if (status.submissions.any { it.value == value && it.result == Result.RIGHT })
            return true

        if (status.submissions.any { it.value == value }) {
            println("Cannot submit. Already submitted value $value for $year-$day Part $part")
            return false
        }
        val range = status.getRange()
        if (range != null && value.toLongOrNull() == null) {
            println("Cannot submit. $value is not a numeric value. Expected due to previous submissions. Answer will be in range: $range for $year-$day Part $part")
            return false
        }
        if (range != null && !range.contains(value.toLong())) {
            val rangeString = when {
                range.first == Long.MIN_VALUE +1
                -> "less than ${range.last}"
                range.last == Long.MAX_VALUE -> "greater than ${range.first}"
                else -> "in range $range"
            }
            println("Cannot submit $value is not in expected range: $rangeString for $year-$day Part $part")
            return false
        }
        if (status.submitEarliest.isAfter(LocalDateTime.now())) {
            println("Cannot submit value until ${status.submitEarliest}. Do you want to submit $value for $year-$day Part $part as soon as it is allowed?")
            readLine()
            while (status.submitEarliest.isAfter(LocalDateTime.now())) {
                Thread.sleep(500)
            }
        } else {
            println("Submit $value for $year-$day Part $part?")
            readLine()
        }
        val response = submit(part, value)
        val (submission, earliestSubmit) = handleResponse(value, response)
        if (submission.result == Result.RIGHT){
            println("Submission $${value} for Part $part is:  RIGHT ✅ ")
        } else {
            println("Submission $value for Part $part is: WRONG ❌ ")
        }
        val until = LocalDateTime.now().until(earliestSubmit, ChronoUnit.SECONDS)
        if (until > 0)
            println("Please wait $until seconds before trying again.")
        val submissions = status.submissions.toMutableList()
        submissions.add(submission)
        ProblemStatus(earliestSubmit, submissions).toFile(statusFile(part))
        return true
    }

    private fun handleResponse(value: String, response: String): Pair<Submission, LocalDateTime> {
        val result = when {
            response.contains("your answer is too high") -> Result.TOO_HIGH
            response.contains("your answer is too low") -> Result.TOO_LOW
            response.contains("That's not the right answer") -> Result.WRONG
            response.contains("That's the right answer") -> Result.RIGHT
            else -> {
                println(response)
                throw RuntimeException("Result type undefined for value $value.")
            }
        }

        var earliestNextSubmission = LocalDateTime.now()
        val matchResult = """Please wait (.+) before trying again.""".toRegex().find(response)
        if (matchResult != null) {
            val (waitString: String) = matchResult.destructured
            if (waitString == "one minute") earliestNextSubmission = LocalDateTime.now().plusMinutes(1)
            else {
                println(response)
                throw RuntimeException("Wait time undefined for value '$waitString'.")
            }
        }
        return Pair(Submission(value, result), earliestNextSubmission)
    }

    private fun submit(part: PART, answer: String): String {
        val sessionCookie = getSession()
        if (sessionCookie == "") {
            throw RuntimeException("No session environment variable set.")
        } else {
            val client = HttpClient.newBuilder().build()
            val body = "level=${if (part == PART.ONE) 1 else 2}&answer=$answer"
            val request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .setHeader("Cookie", "session=$sessionCookie")
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create("https://adventofcode.com/${year}/day/${day}/answer"))
                    .build()
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            return response.body()
        }
    }

    private fun getStatus(part: PART): ProblemStatus {
        val fResource = statusFile(part)
        return if (!fResource.exists()) getStatusOnline(part) else ProblemStatus.fromFile(fResource)
    }

    private fun statusFile(part: PART): File {
        val inputsFolder = File(System.getProperty("user.home").plus(File.separator).plus(".aoc"))
        if (!inputsFolder.exists()) {
            inputsFolder.mkdir()
        } else if (!inputsFolder.isDirectory) {
            throw RuntimeException("Directory ${inputsFolder.absolutePath} is not a directory.")
        } else if (!inputsFolder.canWrite()) {
            throw RuntimeException("Directory ${inputsFolder.absolutePath} is not writeable.")
        }
        val fileName = "$year${"%02d".format(day)}.$part.status"
        return inputsFolder.resolve(fileName)
    }

    private fun getStatusOnline(part: PART): ProblemStatus {
        val sessionCookie = getSession()
        if (sessionCookie == "" ) {
            throw RuntimeException("No session environment variable set.")
        } else {
            val client = HttpClient.newBuilder().build()
            val request = HttpRequest.newBuilder()
                    .setHeader("Cookie", "session=$sessionCookie")
                    .uri(URI.create("https://adventofcode.com/${year}/day/${day}"))
                    .build()
            val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
            val results = response.lines()
                    .filter { it.contains("Your puzzle answer was ") }
                    .map {
                        val (code) = """<code>(.+?)</code>""".toRegex().find(it)!!.destructured
                        code
                    }

            when {
                results.isEmpty() -> {
                    val status = ProblemStatus(LocalDateTime.now(), listOf())
                    status.toFile(statusFile(PART.ONE))
                    status.toFile(statusFile(PART.TWO))
                    return status
                }
                results.size == 1 -> {
                    val status1 = ProblemStatus(LocalDateTime.now(), listOf(Submission(results[0], Result.RIGHT)))
                    val status2 = ProblemStatus(LocalDateTime.now(), listOf())
                    status1.toFile(statusFile(PART.ONE))
                    status2.toFile(statusFile(PART.TWO))
                    return status1
                }
                else -> {
                    val status1 = ProblemStatus(LocalDateTime.now(), listOf(Submission(results[0], Result.RIGHT)))
                    val status2 = ProblemStatus(LocalDateTime.now(), listOf(Submission(results[1], Result.RIGHT)))
                    status1.toFile(statusFile(PART.ONE))
                    status2.toFile(statusFile(PART.TWO))
                    return if (part == PART.ONE) status1 else status2
                }
            }
        }
    }

    data class ProblemStatus(val submitEarliest: LocalDateTime, val submissions: List<Submission>) {

        companion object {
            fun fromFile(file: File): ProblemStatus {
                val lines = file.readLines()
                val submitEarliest = LocalDateTime.parse(lines.first().split("=")[1])
                val submissions = lines.drop(1).map { it.split("=") }.map { Submission(it[0], Result.valueOf(it[1])) }.toList()
                return ProblemStatus(submitEarliest, submissions)
            }
        }

        fun getRange(): LongRange? {
            if (!submissions.any { it.value.toLongOrNull() != null }) return null
            val lo = submissions.filter { it.result == Result.TOO_LOW }.maxOfOrNull { it.value.toLong() }
                ?: Long.MIN_VALUE
            val hi = submissions.filter { it.result == Result.TOO_HIGH }.minOfOrNull { it.value.toLong() }
                ?: Long.MAX_VALUE
            return LongRange(lo + 1, hi - 1)
        }

        fun toFile(file: File) {
            val sb = StringBuilder()
            sb.append("submitEarliest=$submitEarliest").append("\n")
            for (sub in submissions) {
                sb.append("${sub.value}=${sub.result}").append("\n")
            }
            file.writeText(sb.toString())
        }
    }

    data class Submission(val value: String, val result: Result)

    enum class Result { RIGHT, WRONG, TOO_LOW, TOO_HIGH }
    enum class PART { ONE, TWO }
}
