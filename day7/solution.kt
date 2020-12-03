// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()
    val rules = input.map(::buildEntry).toMap()

    val count1 = rules.keys.filter { hasShinyBag(it, rules) }.count()
    println("How many bags has a shiny gold bag in them? $count1")

    val count2 = bags("shiny gold", rules)
    println("How many bags are inside a shiny gold bag? $count2")
}

fun hasShinyBag(bag: String, rules: Map<String, List<Pair<Int,String>>>): Boolean = when {
    bag == "shiny gold" -> false
    rules[bag]!!.filter { it.second == "shiny gold" }.count() > 0 -> true
    else -> rules[bag]!!.filter { hasShinyBag(it.second, rules) }.count() > 0
}

fun bags(bag: String, rules: Map<String, List<Pair<Int,String>>>): Int = rules[bag]!!
    .map { it.first + it.first * bags(it.second, rules) }
    .fold(0) { acc, e -> acc + e }

fun buildEntry(line: String): Pair<String, List<Pair<Int,String>>> {
    val tokenized = line.replace(",", "").split(" ")
    val key = tokenized[0] + " " + tokenized[1]
    val value = mutableListOf<Pair<Int,String>>()
    if (line.contains("contain no other"))
        return key to emptyList()
    for (i in 4..(tokenized.size-1) step 4) {
        value += Pair(tokenized[i].toInt(), tokenized[i+1] + " " + tokenized[i+2])
    }
    return key to value
}
