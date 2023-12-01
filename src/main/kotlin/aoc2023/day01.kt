package aoc2023

import kotlin.io.path.readLines

/**
 * [Day 1: Trebuchet?!](https://adventofcode.com/2023/day/1)
 */
object Day01 : Puzzle<List<String>, Int> {

    override val day: Int = 1
    override val name: String = "⚙🏹🏰 Trebuchet?!"

    /**
     * What is the sum of all of the calibration values?
     */
    override fun solvePart1(input: List<String>): Int = input.sumOf { it.calibrationValue }

    private val String.calibrationValue: Int
        get() = toCharArray()
            .filter { it.isDigit() }
            .let { "${it.first()}${it.last()}" }
            .toInt()

    private val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    /**
     * What is the sum of all of the calibration values?
     */
    override fun solvePart2(input: List<String>): Int = solvePart1(
        input.map { line ->
            words.foldIndexed(line) { index, result, word ->
                // "eightwo" becomes "e8ght2o", not "eigh2"
                val wordWithDigit = word.replaceRange(1..1, "${index + 1}")
                result.replace(word, wordWithDigit)
            }
        }
    )

    override fun solve(): Pair<Int, Int> {
        val (input1, input2) = (1..2).map {
            val example = example.resolveSibling("day-01-$it.txt")
            val lines = example.readLines()
            parseInput(lines)
        }
        val part1 = solvePart1(input1)
        val part2 = solvePart2(input2)
        return part1 to part2
    }
}
