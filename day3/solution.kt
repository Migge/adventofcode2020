// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()
    println("trees (right 3, down 1): ${input.slope(0, 0, 3, 1)}")

    var product: Long = 1
    product *= input.slope(0, 0, 1, 1)
    product *= input.slope(0, 0, 3, 1)
    product *= input.slope(0, 0, 5, 1)
    product *= input.slope(0, 0, 7, 1)
    product *= input.slope(0, 0, 1, 2)
    println("tree product: $product")
}

fun List<String>.slope(x: Int, y: Int, dX: Int, dY: Int): Int = when {
    y >= size -> 0
    else -> get(y).tree(x) + slope(x+dX, y+dY, dX, dY)
}

fun String.tree(x: Int) = when (get(x % length)) {
    '#' -> 1
    else -> 0
}
