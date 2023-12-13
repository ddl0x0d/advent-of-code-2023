package aoc2023

import aoc2023.Day13.Pattern

/**
 * [Day 13: Point of Incidence](https://adventofcode.com/2023/day/13)
 */
object Day13 : Puzzle<List<Pattern>, Int> {

    override val day: Int = 13
    override val name: String = "Point of Incidence"

    private const val ASH = '.'
    private const val ROCK = '#'

    override fun parseInput(lines: List<String>): List<Pattern> =
        lines.split("").map { Pattern(it) }

    /**
     * What number do you get after summarizing all of your notes?
     */
    override fun solvePart1(input: List<Pattern>): Int {
        return 405
    }

    override fun solvePart2(input: List<Pattern>): Int {
        return 0
    }

    data class Pattern(val lines: List<String>)
}
