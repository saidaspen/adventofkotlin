package se.saidaspen.aoc.util

import java.io.File
import java.math.BigInteger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

fun inputFromFile(name: String) = File(ClassLoader.getSystemResource(name).file).readText().trim()

fun getInput(year: Int, day: Int): String {
    return getInput(year, day, false)
}

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

fun String.runCommand(workingDir: File) {
    ProcessBuilder(*split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(60, TimeUnit.MINUTES)
}

fun isJUnitTest(): Boolean {
    for (element in Thread.currentThread().stackTrace) {
        if (element.className.startsWith("org.junit.")) {
            return true
        }
    }
    return false
}

fun getInput(year: Int, day: Int, block: Boolean): String {
    if (isJUnitTest()) return ""
//    println("Advent of code $year day $day")
    val relTime = LocalDateTime.of(year, Month.DECEMBER, day, 6, 0)
    if (LocalDateTime.now().isBefore(relTime)) {
        println("⏸️ Waiting to download $year-$day")
    }
    var output: String? = null
    while (block && LocalDateTime.now().isBefore(relTime)) {
        if (output != null) {
            (1..output.length).forEach { _ -> print("\b") }
        }
        val secondsLeft = LocalDateTime.now().until(relTime, ChronoUnit.SECONDS)
        output = "$secondsLeft seconds left."
        print(output)
        Thread.sleep(1000)
    }
    if (output != null) {
        (1..output.length).forEach { _ -> print("\b") }
    }
    if (LocalDateTime.now().isBefore(relTime))
        throw RuntimeException("Problem has not been released yet.")
    val inputsFolder = inputFolder()
    val fileName = "$year${"%02d".format(day)}"
    val fResource = inputsFolder.resolve(fileName)
    if (!fResource.exists()) {
        println("Downloading input for $year $day")
        val text = download(year, day)
        if (text.contains("Please don't repeatedly")) {
            throw RuntimeException("Too early to request input")
        }
        fResource.writeText(text)
    }
    return fResource.readText().trimEnd()
}

private fun inputFolder(): File {
    val inputsFolder = File(System.getProperty("user.home").plus(File.separator).plus(".aoc"))
    if (!inputsFolder.exists()) {
        inputsFolder.mkdir()
    } else if (!inputsFolder.isDirectory) {
        throw RuntimeException("Directory ${inputsFolder.absolutePath} is not a directory.")
    } else if (!inputsFolder.canWrite()) {
        throw RuntimeException("Directory ${inputsFolder.absolutePath} is not writeable.")
    }
    return inputsFolder
}

private fun download(year: Int, day: Int): String {
    val sessionCookie = getSession()
    if (sessionCookie == "" || sessionCookie == null) {
        throw RuntimeException("No session environment variable set.")
    } else {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .setHeader("Cookie", "session=$sessionCookie")
            .setHeader("User-Agent", "github.com/saidaspen/adventofkotlin, Said Aspen, aoc@saidaspen.se")
            .uri(URI.create("https://adventofcode.com/${year}/day/${day}/input"))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }
}

fun getSession() :String {
    val inputsFolder = inputFolder()
    val fileName = "session"
    val fResource = inputsFolder.resolve(fileName)
    return if (!fResource.exists()) {
        System.getenv("session")
    } else {
        fResource.readText().trim()
    }
}

fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun <E> MutableList<E>.removeFirst(length: Int): MutableList<E> {
    val firstN = subList(0, length).toMutableList()
    if (length in 1..size) {
        subList(0, length).clear()
    }
    return firstN
}

fun <E> MutableList<E>.removeLast(length: Int): MutableList<E> {
    val lastN = subList(size - length, this.size).toMutableList()
    if (length in 1..size) {
        subList(size - length, this.size).clear()
    }
    return lastN
}

fun readFileInputNoTrim(name: String) = File(ClassLoader.getSystemResource(name).file).readText()
