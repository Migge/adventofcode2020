// kotlinc-jvm day2.kt -include-runtime -d day2.jar
// cat input_day2 | java -jar day2.jar

import kotlin.text.MatchResult.Destructured

fun main() {
    val input = generateSequence(::readLine).toList()
    println("Nr of valid passwords 1: ${validPasswords(input)}")
    println("Nr of valid passwords 2: ${validPasswords2(input)}")
}

val regex = """(\d+)-(\d+) (\w): (\w+)""".toRegex()

// First half
fun validPasswords(input: List<String>) = input.filter { valid(it) }.count()
fun valid(passw: String) = valid(regex.find(passw)!!.destructured)
fun valid(min: Int, max: Int, ch: Char, passw: String) = occurrences(ch, passw) in min..max
fun occurrences(ch: Char, str: String) = str.filter { it == ch }.count()

fun valid(d: Destructured) = valid(d.min(), d.max(), d.char(), d.passw())
fun Destructured.min() = component1().toInt()
fun Destructured.max() = component2().toInt()
fun Destructured.char() = component3().single()
fun Destructured.passw() = component4()

// Second half
fun validPasswords2(input: List<String>) = input.filter { valid2(it) }.count()
fun valid2(passw: String) = valid2(regex.find(passw)!!.destructured)
fun valid2(min: Int, max: Int, ch: Char, passw: String) = (passw[min-1] == ch) xor (passw[max-1] == ch)

fun valid2(d: Destructured) = valid2(d.min(), d.max(), d.char(), d.passw())
