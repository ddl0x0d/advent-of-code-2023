package aoc2023

/**
 * [Day 9: Mirage Maintenance](https://adventofcode.com/2023/day/9)
 */
object Day09 : Puzzle<List<List<Int>>, Int> {

    override val day: Int = 9
    override val name: String = "🏝🔮⏳ Mirage Maintenance"

    override fun parseInput(lines: List<String>): List<List<Int>> =
        lines.map { line -> line.split(" ").map { it.toInt() } }

    /**
     * What is the sum of these extrapolated values?
     */
    override fun solvePart1(input: List<List<Int>>): Int =
        input.sumOf { history -> findDiffs(history).sumOf { it.last() } }

    /**
     * What is the sum of these extrapolated values?
     */
    override fun solvePart2(input: List<List<Int>>): Int =
        input.sumOf { history ->
            findDiffs(history)
                .map { it.first() }
                .reversed()
                .reduce { acc, next -> next - acc }
                .toInt()
        }

    private fun findDiffs(history: List<Int>): List<List<Int>> =
        generateSequence(history) {
            it.zipWithNext { a, b -> b - a }
        }.takeWhile { diffs ->
            diffs.any { it != 0 }
        }.toList()
}
