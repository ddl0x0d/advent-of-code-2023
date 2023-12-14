package aoc2023

import aoc2023.Day14.Tile
import aoc2023.Day14.Tile.*
import aoc2023.Direction.*

/**
 * [Day 14: Parabolic Reflector Dish](https://adventofcode.com/2023/day/14)
 */
object Day14 : Puzzle<Grid<Tile>, Int> {

    override val day: Int = 14
    override val name: String = "Parabolic Reflector Dish"

    override fun parseInput(lines: List<String>): Grid<Tile> =
        Grid.parse(lines) { _, _, char -> Tile.from(char) }

    /**
     * What is the total load on the north support beams?
     */
    override fun solvePart1(input: Grid<Tile>): Int = input.tilt(NORTH).load(NORTH)

    private const val CYCLES = 1_000_000_000

    /**
     * What is the total load on the north support beams?
     */
    override fun solvePart2(input: Grid<Tile>): Int {
        var counter = 0
        var platform = input
        val hashes = mutableSetOf<String>()

        do {
            platform = platform.spin()
            val repeated = hashes.add(platform.hash()).not()
        } while (++counter < CYCLES && !repeated)

        hashes.clear()
        do {
            platform = platform.spin()
            val repeated = hashes.add(platform.hash()).not()
        } while (++counter < CYCLES && !repeated)

        val cycleSize = hashes.size
        val remaining = (CYCLES - counter) % cycleSize
        repeat(remaining) { platform = platform.spin() }

        return platform.load(NORTH)
    }

    private fun Grid<Tile>.spin(): Grid<Tile> = listOf(NORTH, WEST, SOUTH, EAST)
        .fold(this) { platform, direction -> platform.tilt(direction) }

    private fun Grid<Tile>.tilt(direction: Direction): Grid<Tile> {
        val cells = cells.toMutableList()
        val startCells = when (direction) {
            NORTH -> getRow(0)
            EAST -> getCol(cols - 1)
            SOUTH -> getRow(rows - 1)
            WEST -> getCol(0)
        }
        startCells.forEach { startCell ->
            var lastEmpty: Pair<Int, Int>? = null
            val cellRange = when (direction) {
                NORTH -> getCol(startCell.col)
                EAST -> getRow(startCell.row).reversed()
                SOUTH -> getCol(startCell.col).reversed()
                WEST -> getRow(startCell.row)
            }
            cellRange.forEach { cell ->
                val (row, col, tile) = cell
                when (tile) {
                    ROUND_ROCK -> if (lastEmpty != null) {
                        val (emptyRow, emptyCol) = lastEmpty!!
                        cells[row * cols + col] = cells[row * cols + col].copy(value = EMPTY_SPACE)
                        cells[emptyRow * cols + emptyCol] = cells[emptyRow * cols + emptyCol].copy(value = ROUND_ROCK)
                        lastEmpty = emptyRow + direction.vy to emptyCol - direction.vx
                    }
                    SQUARE_ROCK -> lastEmpty = null
                    EMPTY_SPACE -> if (lastEmpty == null) lastEmpty = row to col
                }
            }
        }
        return Grid(rows, cols, cells.toList())
    }

    private fun Grid<Tile>.hash(): String = cells.joinToString("") { "${it.value.char}" }

    private fun Grid<Tile>.load(direction: Direction): Int =
        cells.filter { it.value == ROUND_ROCK }.sumOf { (row, col, _) ->
            when (direction) {
                NORTH -> rows - row
                EAST -> col + 1
                SOUTH -> row + 1
                WEST -> cols - col
            }.toInt()
        }

    enum class Tile(val char: Char) {
        ROUND_ROCK('O'),
        SQUARE_ROCK('#'),
        EMPTY_SPACE('.');

        companion object {
            fun from(char: Char): Tile = entries.firstOrNull { it.char == char } ?: error("Unknown tile '$char'")
        }
    }
}
