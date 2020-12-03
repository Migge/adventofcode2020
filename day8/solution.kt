// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()

    var result = execute(input)
    println("Accumulator when infinite loop: ${result.first}")

    var i = 0
    while (result.second == false) {
        result = execute(input, i++)
    }
    println("Accumulator when correct termination: ${result.first}")
}

fun execute(input: List<String>, modRow: Int = -1): Pair<Int,Boolean> =
    run(0, 0, modRow, emptyList(), input)

fun run(
    acc: Int, row: Int, modRow: Int, visited: List<Int>, input: List<String>
): Pair<Int,Boolean> {

    if (row !in input.indices) return Pair(acc, true)
    else if (row in visited) return Pair(acc, false)

    val line = input[row].split(" ")
    val op = op(line[0], modRow == row)
    val value = line[1].toInt()
    
    return when {
        op == "nop" -> run(acc, row+1, modRow, visited+row, input)
        op == "jmp" -> run(acc, row+value, modRow, visited+row, input)
        op == "acc" -> run(acc+value, row+1, modRow, visited+row, input)
        else -> throw Exception("should not happen")
    }
}

fun op(str: String, mod: Boolean) = when {
    mod && str == "nop" -> "jmp"
    mod && str == "jmp" -> "nop"
    else -> str
}
