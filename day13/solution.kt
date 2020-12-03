// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()

    val earliestBus = getEarliestBus(input)
    println("Earliest bus ID * minutes waiting = ${earliestBus.first * earliestBus.second}")

    val perfectTime = findPerfectTime(input)
    println("Perfect timestamp: $perfectTime")
}

// returns Pair(busID, minutesToWait)
fun getEarliestBus(input: List<String>): Pair<Int,Int> {
    val timestamp = input[0].toInt()
    val buses = input[1].split(',').filterNot { it == "x" }.map { it.toInt() }
    return buses
        .map { Pair(it, it * ((timestamp / it)+1) - timestamp) }
        .sortedBy { it.second }
        .first()
}

fun findPerfectTime(input: List<String>): Long =
    find(input[1].split(',').map { if (it == "x") 1L else it.toLong() })

fun find(buses: List<Long>): Long {
    var timestamp = 0L
    var inc = 1L
    for (i in buses.indices) {
        timestamp = next(buses.take(i+1), timestamp, inc)
        inc *= buses[i]
    }
    return timestamp
}

fun next(buses: List<Long>, timestamp: Long, inc: Long): Long = when {
    buses.size == 1 -> buses[0]
    buses.filterIndexed { i, bus -> (timestamp + i) % bus != 0L }.isEmpty() -> timestamp
    else -> next(buses, timestamp + inc, inc)
}
