// kotlinc solution.kt -o solution
// cat input | ./solution.kexe

fun main() {
    val input = readLine()!!.split('\n').map(String::toInt)
    val foundPair = find2020Pair(input)
    println("$foundPair => ${foundPair.first * foundPair.second}")
    val foundTriple = find2020Triple(input)
    println("$foundTriple => ${foundTriple.first * foundTriple.second * foundTriple.third}")
}

fun find2020Pair(list: List<Int>): Pair<Int,Int> {
    val pair_2020 = createPairs(list).find { it.first + it.second == 2020 }
    return pair_2020 ?: throw Exception("Cannot find 2020")
}

fun createPairs(list: List<Int>): List<Pair<Int,Int>> = when (list.size) {
    0, 1 -> emptyList()
    2 -> listOf(Pair(list[0], list[1]))
    else -> list.drop(1).map { Pair(list[0], it) } + createPairs(list.drop(1))
}

fun find2020Triple(list: List<Int>): Triple<Int,Int,Int> {
    val triple_2020 = createTriples(list).find { it.first + it.second + it.third == 2020 }
    return triple_2020 ?: throw Exception("Cannot find 2020")
}

fun createTriples(list: List<Int>): List<Triple<Int,Int,Int>> = when (list.size) {
    0, 1, 2 -> emptyList()
    3 -> listOf(Triple(list[0], list[1], list[2]))
    else -> createPairs(list.drop(1)).map { Triple(list[0], it.first, it.second) } + createTriples(list.drop(1))
}
