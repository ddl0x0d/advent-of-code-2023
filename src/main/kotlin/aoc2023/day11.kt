package aoc2023

import aoc2023.Day11.Space
import aoc2023.Day11.Space.EMPTY
import aoc2023.Day11.Space.GALAXY
import kotlin.math.abs

/**
 * [Day 11: Cosmic Expansion](https://adventofcode.com/2023/day/11)
 */
object Day11 : Puzzle<Grid<Space>, Long> {

    override val day: Int = 11
    override val name: String = "Cosmic Expansion"

    override fun parseInput(lines: List<String>): Grid<Space> =
        Grid.parse(lines) { _, _, char -> Space.from(char) }

    override fun solvePart1(input: Grid<Space>): Long = sumOfShortestDistance(input, 1)

    override fun solvePart2(input: Grid<Space>): Long = sumOfShortestDistance(input, 1_000_000)

    private fun sumOfShortestDistance(input: Grid<Space>, expansionRate: Int): Long {
        val emptyRows = (0..<input.rows).mapIndexedNotNull { row, _ ->
            if (input.getRow(row).all { it.value == EMPTY }) row else null
        }
        val emptyCols = (0..<input.cols).mapIndexedNotNull { col, _ ->
            if (input.getCol(col).all { it.value == EMPTY }) col else null
        }
        val galaxies = input.filter { it.value == GALAXY }
        val pairs = galaxies.toPairs()
        val result = pairs.sumOf { pair ->
            val rowRange = pair.toList().map { it.row }.sorted().let { (r1, r2) -> r1..r2 }
            val colRange = pair.toList().map { it.col }.sorted().let { (c1, c2) -> c1..c2 }
            val extraRows = emptyRows.count { it in rowRange }
            val extraCols = emptyCols.count { it in colRange }
            val (g1, g2) = pair
            val distance = abs(g1.row - g2.row) + extraRows * maxOf(1, expansionRate - 1) +
                    abs(g1.col - g2.col) + extraCols * maxOf(1, expansionRate - 1)
            distance.toLong()
        }
        return result
    }

    private fun <T> List<T>.toPairs(): List<Pair<T, T>> {
        val result = mutableListOf<Pair<T, T>>()
        forEachIndexed { index, item1 ->
            slice(index + 1..lastIndex).forEach { item2 ->
                result += item1 to item2
            }
        }
        return result
    }

    enum class Space(val char: Char) {
        EMPTY('.'),
        GALAXY('#');

        companion object {
            fun from(char: Char): Space = entries.first { it.char == char }
        }
    }
}
