package aoc2023

import aoc2023.Day13.Line.HORIZONTAL
import aoc2023.Day13.Line.VERTICAL

/**
 * [Day 13: Point of Incidence](https://adventofcode.com/2023/day/13)
 */
object Day13 : Puzzle<List<Grid<Char>>, Int> {

    override val day: Int = 13
    override val name: String = "Point of Incidence"

    private const val ASH = '.'
    private const val ROCK = '#'

    override fun parseInput(lines: List<String>): List<Grid<Char>> =
        lines.split("").map { Grid.parse(it) { _, _, char -> char } }

    /**
     * What number do you get after summarizing all of your notes?
     */
    override fun solvePart1(input: List<Grid<Char>>): Int = input.sumOf {
        it.findReflection().let { (line, number) -> line.multiplier * number }
    }

    override fun solvePart2(input: List<Grid<Char>>): Int {
        return 0
    }

    private fun Grid<Char>.findReflection(): Pair<Line, Int> {
        (1..<cols).forEach { col ->
            if (isReflection(VERTICAL, col)) {
                return VERTICAL to col
            }
        }
        (1..<rows).forEach { row ->
            if (isReflection(HORIZONTAL, row)) {
                return HORIZONTAL to row
            }
        }
        error("Couldn't find any reflections")
    }

    private fun Grid<Char>.isReflection(line: Line, number: Int): Boolean {
        return false
    }

    enum class Line(val multiplier: Int) {
        HORIZONTAL(1),
        VERTICAL(100),
    }
}
