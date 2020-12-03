// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()
    val groups = parseMultiline(input)

    val sum1 = groups
        .map { it.replace(" ", "").toSet().size }
        .reduce { acc, e -> acc + e }

    val sum2 = groups
        .map { it.trim().split(" ").map { it.toSet() } }  // List<List<Set>>
        .map { it.fold(('a'..'z').toSet()) { acc, e -> acc intersect e } }  // List<Set>
        .map { it.size }  // List<Int>
        .reduce { acc, e -> acc + e }

    println("Sum of questions where _anyone_ answered yes: $sum1")
    println("Sum of questions where _everyone_ answered yes: $sum2")
}

fun parseMultiline(input: List<String>): List<String> {
    var str = ""
    var multiline = mutableListOf<String>()
   
    input.forEach {
        if (it.trim().isEmpty()) {
            multiline.add(str.trim())
            str = ""
        } else {
            str += " $it"
        }
    }
    if (str.isNotEmpty()) multiline.add(str)
    return multiline
}
