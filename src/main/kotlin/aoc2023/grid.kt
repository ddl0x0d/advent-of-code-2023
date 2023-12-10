package aoc2023

class Grid<CELL>(
    val rows: Int,
    val cols: Int,
    val cells: List<CELL>,
) : Iterable<CELL> {

    fun getOrNull(row: Int, col: Int): CELL? = getOrNull(row)?.getOrNull(col)
    operator fun get(row: Int, col: Int): CELL = get(row)[col]
    fun getOrNull(row: Int): List<CELL>? = if (row < 0 || row >= rows) null else get(row)
    operator fun get(row: Int): List<CELL> {
        val start = row * cols
        return cells.slice(start..<(start + cols))
    }

    override fun iterator(): Iterator<CELL> = cells.iterator()

    fun print(transform: (CELL) -> Char) {
        cells.chunked(cols).forEach { cells ->
            println(cells.joinToString("") { transform(it).toString() })
        }
    }

    companion object {
        fun <CELL> parse(lines: List<String>, transform: (row: Int, col: Int, char: Char) -> CELL): Grid<CELL> {
            val rows = lines.size
            val cols = lines.first().length
            val cells = List(rows * cols) { i ->
                val row = i / cols
                val col = i % cols
                val char = lines[row][col]
                transform(row, col, char)
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
