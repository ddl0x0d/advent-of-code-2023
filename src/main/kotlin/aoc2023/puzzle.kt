package aoc2023

import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.readLines

interface Puzzle<INPUT, OUTPUT> {

    val day: Int
    val name: String
    val example: Path get() = Path("examples/day-%02d.txt".format(day))

    fun solve(): Pair<OUTPUT, OUTPUT> {
        val lines = example.readLines()
        val input = parseInput(lines)
        val part1 = solvePart2(input)
        val part2 = solvePart2(input)
        return part1 to part2
    }

    @Suppress("UNCHECKED_CAST")
    fun parseInput(lines: List<String>): INPUT = lines as INPUT
    fun solvePart1(input: INPUT): OUTPUT
    fun solvePart2(input: INPUT): OUTPUT
}
