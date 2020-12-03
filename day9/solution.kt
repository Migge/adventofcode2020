// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList().map { it.toLong() }
    val ps = 25
    var invalidNr = -1L
    for (i in ps..(input.size-1)) {
        val preamble = input.drop(i-ps).take(ps)//.sorted()
        if (!match(input[i], preamble)) {
            invalidNr = input[i]
            break
        }
    }
    println("invalid nr: $invalidNr")

    var sum = -1L
    for (i in input.indices) {
        sum = findContiguousSet(invalidNr, input.drop(i))
        if (sum > -1L) break
    }
    println("sum: $sum")
}

fun findContiguousSet(nr: Long, list: List<Long>): Long {
    var sum = 0L
    for (i in list.indices) {
        sum += list[i]
        if (sum == nr) {
            val range = list.take(i+1).sorted()
            println(range)
            return range.first() + range.last()
        }
    }
    return -1
}

fun match(nr: Long, preamble: List<Long>): Boolean {
    for (i in preamble.indices) {
        if (match(nr, preamble[i], preamble.drop(i+1))) return true
    }
    return false
}

fun match(nr: Long, current: Long, list: List<Long>): Boolean = when {
    list.isEmpty() -> false
    current + list[0] == nr -> true
    else -> match(nr, current, list.drop(1))
}
