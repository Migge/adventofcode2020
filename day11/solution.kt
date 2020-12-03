// kotlinc-jvm solution.kt -include-runtime -d solution.jar
// cat input | java -jar solution.jar

fun main() {
    val input = generateSequence(::readLine).toList()

    var occupied: Int
    occupied = gameOfLife(Grid(input), tolerance = 4, recurse = false)
    println("Occupied seats with tolerance 4 and without recursion: $occupied")
    occupied = gameOfLife(Grid(input), tolerance = 5, recurse = true)
    println("Occupied seats with tolerance 5 and with recursion: $occupied")
}

fun gameOfLife(grid: Grid, tolerance: Int, recurse: Boolean): Int {
    var newGrid = Grid(grid.width, grid.height)
    for (x in grid.xRange) {
        for (y in grid.yRange) {
            newGrid.set(x, y, grid.decide(x, y, tolerance, recurse))
        }
    }
    return when {
        newGrid == grid -> grid.occupied()
        else -> gameOfLife(newGrid, tolerance, recurse)
    }
}

class Grid {
    constructor(input: List<String>) {
        grid = input.map { it.toCharArray() }.toTypedArray()
    }

    constructor(width: Int, height: Int) {
        grid = init(width, height)
    }

    private fun init(width: Int, height: Int): Array<CharArray> {
        val grid = Array<CharArray>(height) { CharArray(width) { '.' } }
        for (i in grid.indices) {
            grid[i] = CharArray(width)
        }
        return grid
    }

    private val grid: Array<CharArray>
    private val state: String get() = grid.map { it.joinToString() }.joinToString()
    val width get() = grid[0].size
    val height get() = grid.size
    val xRange get() = 0..width-1
    val yRange get() = 0..height-1

    fun get(x: Int, y: Int) = grid[y][x]
    fun set(x: Int, y: Int, ch: Char) = with(ch) { grid[y][x] = this }

    fun occupied() = state.filter { it == '#' }.count()
    
    fun decide(x: Int, y: Int, tolerance: Int, recurse: Boolean): Char = when {
        get(x,y) == '#' && count(x, y, recurse) >= tolerance -> 'L'
        get(x,y) == 'L' && count(x, y, recurse) == 0 -> '#'
        else -> get(x,y)
    }

    fun count(x: Int, y: Int, r: Boolean): Int =
        upLeft(x,y,r) + up(x,y,r) + upRight(x,y,r) + left(x,y,r) + right(x,y,r) + downLeft(x,y,r) + down(x,y,r) + downRight(x,y,r)

    fun upLeft(x: Int, y: Int, recurse: Boolean): Int = when {
        x == 0 || y == 0 -> 0
        get(x-1, y-1) == 'L' -> 0
        get(x-1, y-1) == '#' -> 1
        !recurse -> 0
        else -> upLeft(x-1, y-1, recurse)
    }

    fun up(x: Int, y: Int, recurse: Boolean): Int = when {
        y == 0 -> 0
        get(x, y-1) == 'L' -> 0
        get(x, y-1) == '#' -> 1
        !recurse -> 0
        else -> up(x, y-1, recurse)
    }

    fun upRight(x: Int, y: Int, recurse: Boolean): Int = when {
        x == width-1 || y == 0 -> 0
        get(x+1, y-1) == 'L' -> 0
        get(x+1, y-1) == '#' -> 1
        !recurse -> 0
        else -> upRight(x+1, y-1, recurse)
    }

    fun left(x: Int, y: Int, recurse: Boolean): Int = when {
        x == 0 -> 0
        get(x-1, y) == 'L' -> 0
        get(x-1, y) == '#' -> 1
        !recurse -> 0
        else -> left(x-1, y, recurse)
    }

    fun right(x: Int, y: Int, recurse: Boolean): Int = when {
        x == width-1 -> 0
        get(x+1, y) == 'L' -> 0
        get(x+1, y) == '#' -> 1
        !recurse -> 0
        else -> right(x+1, y, recurse)
    }

    fun downLeft(x: Int, y: Int, recurse: Boolean): Int = when {
        x == 0 || y == height-1 -> 0
        get(x-1, y+1) == 'L' -> 0
        get(x-1, y+1) == '#' -> 1
        !recurse -> 0
        else -> downLeft(x-1, y+1, recurse)
    }

    fun down(x: Int, y: Int, recurse: Boolean): Int = when {
        y == height-1 -> 0
        get(x, y+1) == 'L' -> 0
        get(x, y+1) == '#' -> 1
        !recurse -> 0
        else -> down(x, y+1, recurse)
    }

    fun downRight(x: Int, y: Int, recurse: Boolean): Int = when {
        x == width-1 || y == height-1 -> 0
        get(x+1, y+1) == 'L' -> 0
        get(x+1, y+1) == '#' -> 1
        !recurse -> 0
        else -> downRight(x+1, y+1, recurse)
    }

    override fun equals(other: Any?) = (other is Grid) && state == other.state
}
