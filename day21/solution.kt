// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()

    val foods = input.map { Pair(ingredients(it), allergens(it)) }
    val map = mutableMapOf<String, List<String>>()  // (allergen, [ingredients])

    for (food in foods) {
        val ingredients = food.first
        val allergens = food.second
        allergens.forEach {
            if (it !in map) map[it] = ingredients
            else map[it] = (map[it]!!.toSet() intersect ingredients.toSet()).toList()
        }
    }

    val all = foods.map { it.first }.reduce { acc, e -> acc + e }
    val possible = map.values.reduce { acc, e -> acc + e }
    val notPossible = all.toSet() subtract possible.toSet()
    val count = notPossible.toList()
        .map { ingredient -> all.count { it == ingredient } }
        .reduce { acc, e -> acc + e }

    println("Ingredients without allergents appear this many times: $count\n")

    // [ (allergen, ingredient), ... ]
    var list: List<Pair<String,String>> = emptyList()
    var pair = map.findOne()
    while (pair != null) {
        val ingredient = pair.second
        for ((key, value) in map.entries) {
            //println("$key, $value")
            map[key] = value - ingredient
        }
        list += pair
        pair = map.findOne()
    }
    val ingredients = list
        .sortedBy { it.first }
        .map { it.second }
        .joinToString(separator = ",")

    println("Dangerous ingredients: $ingredients")
}

fun Map<String,List<String>>.findOne(): Pair<String,String>? {
    return entries.find { it.value.size == 1 }?.let { Pair(it.key,it.value[0]) }
}

fun ingredients(food: String): List<String> {
    val index = food.indexOf("(")
    return if (index < 0) food.split(' ')
    else food.substring(0, index-1).split(' ')
}

fun allergens(food: String): List<String> {
    val start = food.indexOf("(")
    val end = food.indexOf(")")
    return if (start < 0) emptyList()
    else food.substring(start+10, end).split(", ")
}
