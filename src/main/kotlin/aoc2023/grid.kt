package aoc2023

class Grid<T>(
    val rows: Int,
    val cols: Int,
    val cells: List<Cell<T>>,
) : Iterable<Grid.Cell<T>> {

    fun getOrNull(row: Int, col: Int): Cell<T>? = getOrNull(row)?.getOrNull(col)
    operator fun get(row: Int, col: Int): Cell<T> = getRow(row)[col]
    fun getOrNull(row: Int): List<Cell<T>>? = if (row < 0 || row >= rows) null else getRow(row)

    fun getRow(row: Int): List<Cell<T>> {
        val start = row * cols
        return cells.slice(start..<(start + cols))
    }

    fun getCol(col: Int): List<Cell<T>> =
        cells.filterIndexed { index, _ -> index % cols == col }

    override fun iterator(): Iterator<Cell<T>> = cells.iterator()

    fun print(transform: (Cell<T>) -> Char) {
        cells.chunked(cols).forEach { cells ->
            println(cells.joinToString("") { transform(it).toString() })
        }
    }

    data class Cell<T>(val row: Int, val col: Int, val value: T)

    companion object {
        fun <CELL> parse(lines: List<String>, transform: (row: Int, col: Int, char: Char) -> CELL): Grid<CELL> {
            val rows = lines.size
            val cols = lines.first().length
            val cells = List(rows * cols) { i ->
                val row = i / cols
                val col = i % cols
                val char = lines[row][col]
                Cell(row, col, transform(row, col, char))
            }
            return Grid(rows, cols, cells)
        }
    }
}

enum class Direction(val vx: Int, val vy: Int, val opposite: () -> Direction) {
    NORTH(0, 1, { SOUTH }),
    EAST(1, 0, { WEST }),
    SOUTH(0, -1, { NORTH }),
    WEST(-1, 0, { EAST }),
}
