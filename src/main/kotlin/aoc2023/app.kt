package aoc2023

const val YEAR = 2023

val puzzles = listOf(
    Day01,
    Day02,
    Day03,
    Day04,
    Day05,
    Day06,
    Day07,
    Day08,
    Day09,
)

fun main() {
    println("🎄 Advent of Code $YEAR 🎄\n")
    puzzles.forEach { puzzle ->
        println("--- Day ${puzzle.day}: ${puzzle.name} ---")
        val (part1, part2) = puzzle.solve()
        println("Part 1 answer = $part1")
        println("Part 2 answer = $part2")
        println()
    }
}
