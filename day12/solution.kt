// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()
    val instructions = input.map { Pair(it[0], it.substring(1).toInt()) }

    // E - x>0, W - x<0; S - y>0, N - y<0

    var pos = navigate(1, 0, withWP=false, instructions)
    var distance = Math.abs(pos.x) + Math.abs(pos.y) 
    println("Manhattan distance without waypoint: $distance")

    pos = navigate(10, -1, withWP=true, instructions)
    distance = Math.abs(pos.x) + Math.abs(pos.y) 
    println("Manhattan distance with waypoint: $distance")
}

// withWP==true means moving waypoint, withWP==false means moving ship
fun navigate(dirX: Int, dirY: Int, withWP: Boolean, instructions: List<Pair<Char,Int>>): Point {
    var wp = Point(dirX, dirY)
    var pos = Point(0,0)

    instructions.forEach {
        when (it.first) {
            'N', 'S', 'E', 'W' -> {
                if (withWP) wp = parseDir(it.first, wp, it.second)
                else pos = parseDir(it.first, pos, it.second)
            }
            'L' -> wp = wp.turnLeft(it.second)
            'R' -> wp = wp.turnRight(it.second)
            'F' -> pos = pos.move(wp.x * it.second, wp.y * it.second)
            else -> throw Exception("should never happen")
        }
    }

    return pos
}

fun parseDir(dir: Char, point: Point, distance: Int) = when (dir) {
    'N' -> point.up(distance)
    'S' -> point.down(distance)
    'E' -> point.right(distance)
    'W' -> point.left(distance)
    else -> throw Exception("should not happen")
}

// E - x>0, W - x<0; S - y>0, N - y<0
data class Point(val x: Int, val y: Int) {
    fun left(distance: Int) = Point(x - distance, y)
    fun right(distance: Int) = Point(x + distance, y)
    fun up(distance: Int) = Point(x, y - distance)
    fun down(distance: Int) = Point(x, y + distance)

    fun move(xDist: Int, yDist: Int) = Point(x + xDist, y + yDist)

    fun turnLeft(degrees: Int): Point = when {
        degrees == 0 -> this
        else -> Point(y, -x).turnLeft(degrees-90)
    }

    fun turnRight(degrees: Int): Point = when {
        degrees == 0 -> this
        else -> Point(-y, x).turnRight(degrees-90)
    }
}

fun turnLeft(wp: Point, degrees: Int): Point = when {
    degrees == 0 -> wp
    else -> turnLeft(Point(wp.y, -wp.x), degrees-90)
}

fun turnRight(wp: Point, degrees: Int): Point = when {
    degrees == 0 -> wp
    else -> turnRight(Point(-wp.y, wp.x), degrees-90)
}

fun turnLeft(wp: Pair<Int,Int>, degrees: Int): Pair<Int,Int> = when {
    degrees == 0 -> wp
    else -> turnLeft(Pair(wp.second, -wp.first), degrees-90)
}

fun turnRight(wp: Pair<Int,Int>, degrees: Int): Pair<Int,Int> = when {
    degrees == 0 -> wp
    else -> turnRight(Pair(-wp.second, wp.first), degrees-90)
}

