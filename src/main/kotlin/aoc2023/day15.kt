package aoc2023

/**
 * [Day 15: Lens Library](https://adventofcode.com/2023/day/15)
 */
object Day15 : Puzzle<List<String>, Int> {

    override val day: Int = 15
    override val name: String = "Lens Library"

    override fun parseInput(lines: List<String>): List<String> = lines.first().split(",")

    /**
     * What is the sum of the results?
     */
    override fun solvePart1(input: List<String>): Int = input.sumOf { it.hash() }

    override fun solvePart2(input: List<String>): Int {
        val boxes = List(256) { mutableListOf<Pair<String, Int>>() }
        input.forEach { step ->
            val tokens = step.split('=', '-')
            if (tokens.size == 1) { // remove
                val label = tokens.first()
                // TODO
            } else {
                val (label, focalStrength) = tokens
                // TODO
            }
        }
        return 0
    }

    private fun String.hash(): Int =
        toCharArray().fold(0) { acc, char ->
            (acc + char.code) * 17 % 256
        }
}
