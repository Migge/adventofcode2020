// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList().map { it.toInt() }.sorted()
    // first half
    var last = 0
    var ones = 0
    var threes = 0
    for (i in input.indices) {
        val value = input[i]
        if (value - last == 1) ++ones
        else if (value - last == 3) ++threes
        last = value
    }
    ++threes
    println("1-jolt diffs * 3-jolt diffs = ${ones * threes}")

    // second half
    println("Total nr of distinct arrangements: ${fib(listOf(0) + input)}")
}

val cache = HashMap<Int,Long>()

// fibonacci-like recursion with cache
fun fib(l: List<Int>): Long {
    cache[l.v0()]?.let { return it }
    val value = when {
        l.v3() - l.v0() <= 3 -> fib(l.drop(1)) + fib(l.drop(2)) + fib(l.drop(3))
        l.v2() - l.v0() <= 3 -> fib(l.drop(1)) + fib(l.drop(2))
        l.v1() - l.v0() <= 3 -> fib(l.drop(1))
        else -> 1
    }
    return value.also { cache[l.v0()] = it }
}
    
fun List<Int>.v0() = if (size < 1) Int.MAX_VALUE else get(0)
fun List<Int>.v1() = if (size < 2) Int.MAX_VALUE else get(1)
fun List<Int>.v2() = if (size < 3) Int.MAX_VALUE else get(2)
fun List<Int>.v3() = if (size < 4) Int.MAX_VALUE else get(3)


// alternative solution
// backwards iteration with accumulator
/*
fun fib(input: List<Int>): Long {
    var array = LongArray(input.size)
    for (i in (input.size-1) downTo 0) {
        val trimmed = input.drop(i+1).filter { it - input[i] <= 3 }.count()
        if (trimmed == 3) array[i] = array[i+1] + array[i+2] + array[i+3]
        if (trimmed == 2) array[i] = array[i+1] + array[i+2]
        if (trimmed == 1) array[i] = array[i+1]
        if (trimmed == 0) array[i] = 1L
    }
    return array[0]
}
*/
