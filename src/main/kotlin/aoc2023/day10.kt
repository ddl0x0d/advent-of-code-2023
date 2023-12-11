package aoc2023

import aoc2023.Day10.Tile
import aoc2023.Day10.Tile.Symbol.GROUND
import aoc2023.Day10.Tile.Symbol.START
import aoc2023.Direction.*
import kotlin.io.path.readLines

/**
 * [Day 10: Pipe Maze](https://adventofcode.com/2023/day/10)
 */
object Day10 : Puzzle<Grid<Tile>, Int> {

    override val day: Int = 10
    override val name: String = "Pipe Maze"

    override fun parseInput(lines: List<String>): Grid<Tile> =
        Grid.parse(lines) { row, col, char ->
            Tile(row, col, symbol = Tile.Symbol.from(char))
        }

    /**
     * How many steps along the loop does it take to get from the starting
     * position to the point farthest from the starting position?
     */
    override fun solvePart1(input: Grid<Tile>): Int {
        val start = input.first { it.value.symbol == START }
        val tunnel = input.findTunnel(start.value)
        return (tunnel.size - 1) / 2
    }

    /**
     * How many tiles are enclosed by the loop?
     */
    override fun solvePart2(input: Grid<Tile>): Int {
        val start = input.first { it.value.symbol == START }
        val tunnel = input.findTunnel(start.value)
        // TODO
        return 10
    }

    private fun Grid<Tile>.findTunnel(start: Tile): List<Tile> {
        val result = mutableListOf(start)
        var nextMove: Direction? = null
        var tile = start
        do {
            nextMove = tile.symbol.connectors.first {
                val neighbour = getOrNull(tile.row - it.vy, tile.col + it.vx)
                neighbour != null && it != nextMove?.opposite?.invoke()
                        && it.opposite() in neighbour.value.symbol.connectors
            }
            tile = get(tile.row - nextMove.vy, tile.col + nextMove.vx).value
            result += tile
        } while (tile != start)
        return result
    }

    data class Tile(val row: Int, val col: Int, val symbol: Symbol) {

        enum class Symbol(val char: Char, val connectors: Set<Direction>) {
            VERTICAL('|', setOf(NORTH, SOUTH)),
            HORIZONTAL('-', setOf(EAST, WEST)),
            NE_BEND('L', setOf(NORTH, EAST)),
            NW_BEND('J', setOf(NORTH, WEST)),
            SW_BEND('7', setOf(SOUTH, WEST)),
            SE_BEND('F', setOf(SOUTH, EAST)),
            GROUND('.', emptySet()),
            START('S', setOf(NORTH, EAST, SOUTH, WEST));

            companion object {
                fun from(char: Char): Symbol =
                    entries.firstOrNull { it.char == char }
                        ?: error("Couldn't find symbol for char '$char'")
            }
        }
    }

    override fun solve(): Pair<Int, Int> {
        val (input1, input2) = (1..2).map {
            val example = example.resolveSibling("day-10-$it.txt")
            val lines = example.readLines()
            parseInput(lines)
        }
        val part1 = solvePart1(input1)
        val part2 = solvePart2(input2)
        return part1 to part2
    }
}
