// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()

    val ids = input.map {
        bsearch(0, 127, it.take(7)) * 8 + bsearch(0, 7, it.takeLast(3))
    }

    val min = ids.minOrNull()!!
    val max = ids.maxOrNull()!!

    println("Lowest seat ID: $min, highest seat ID: $max")
    for (i in min..max) {
        if (!ids.contains(i)) {
            println("Missing seat ID: $i")
        }
    }
}

fun bsearch(min: Int, max: Int, s: String): Int = when {
    min == max -> min
    s[0] == 'F' || s[0] == 'L' -> bsearch(min, max - (max-min)/2 - (max-min)%2, s.drop(1))
    s[0] == 'B' || s[0] == 'R' -> bsearch(min + (max-min)/2 + (max-min)%2, max, s.drop(1))
    else -> 0
}
