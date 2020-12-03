// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// java -jar solution.jar

fun main() {
    // val input = "389125467"
    val input = "219748365"
        .map { it }.map { it.toString() }.map { it.toInt() }
    
    // part 1
    Game(input, 9)
        .play(100)
        .printUntil(1)

    // part 2
    Game(input, 1000000)
        .play(10000000)
        .printProductNextTwo()
}

class Game(input: List<Int>, val max: Int) {
    private val map = mutableMapOf<Int, Cup>()
    private val firstCup: Cup

    init {
        firstCup = this.parseCups(input + ((input.max()!! + 1)..max).toList())
    }

    // returns the cup with label 1
    fun play(rounds: Int): Cup {
        var curr = firstCup
        for (i in 1..rounds) {
            curr = curr.move(max)
        }
        return map[1]!!
    }

    inner class Cup(val label: Int, var next: Cup? = null) {

        fun move(max: Int): Cup {
            val picks = Array<Cup?>(3) { null }
            val picksLabels = Array<Int>(3) { 0 }
            for (i in 0..2) {
                val next = this.next
                this.next = next!!.next
                picks[i] = next
                picksLabels[i] = next.label
            }
            
            val destLabel = when {
                sub(1, max) !in picksLabels -> sub(1, max)
                sub(2, max) !in picksLabels -> sub(2, max)
                sub(3, max) !in picksLabels -> sub(3, max)
                else -> sub(4, max)
            }
            val dest = map[destLabel]!!
            val next = dest.next
            dest.next = picks[0]
            picks[2]!!.next = next
            return this.next!!
        }

        private fun sub(value: Int, max: Int) = when {
            label - value > 0 -> label - value
            else -> max + (label - value)
        }
    }

    private fun parseCups(list: List<Int>): Cup {
        var prev: Cup? = null
        var first: Cup? = null
        for (cup in list) {
            val newCup = Cup(cup)
            if (first == null) first = newCup
            prev?.next = newCup
            prev = newCup
            map[cup] = newCup
        }
        prev!!.next = first

        return first!!
    }
}

fun Game.Cup.printUntil(untilLabel: Int) {
    var curr = next!!
    while (curr.label != untilLabel) {
        print(curr.label)
        curr = curr.next!!
    }
    print("\n")
}

fun Game.Cup.printProductNextTwo() {
    val product = next!!.label.toLong() * next!!.next!!.label.toLong()
    println(product)
}
