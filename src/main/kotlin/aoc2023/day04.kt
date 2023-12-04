package aoc2023

/**
 * [Day 4: Scratchcards](https://adventofcode.com/2023/day/4)
 */
object Day04 : Puzzle<List<Int>, Int> {

    override val day: Int = 4
    override val name: String = "🎫🔑💰 Scratchcards"

    override fun parseInput(lines: List<String>): List<Int> =
        lines.map { line ->
            line.split(":", " |").drop(1)
                .map { it.chunked(3).toSet() }
                .let { (winning, ours) -> winning intersect ours }.size
        }

    /**
     * How many points are they worth in total?
     */
    override fun solvePart1(input: List<Int>): Int =
        input.sumOf { wins -> if (wins > 0) 1 shl wins - 1 else 0 }

    /**
     * How many total scratchcards do you end up with?
     */
    override fun solvePart2(input: List<Int>): Int {
        val instances = MutableList(input.size) { 1 }
        input.forEachIndexed { index, wins ->
            (1..wins)
                .map { it + index }
                .filter { it < instances.size }
                .forEach { instances[it] += instances[index] }
        }
        return instances.sum()
    }
}
