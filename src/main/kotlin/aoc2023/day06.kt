package aoc2023

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.math.truncate

/**
 * [Day 6: Wait For It](https://adventofcode.com/2023/day/6)
 */
object Day06 : Puzzle<List<String>, Long> {

    override val day: Int = 6
    override val name: String = "🚤🏁🏆 Wait For It"

    override fun parseInput(lines: List<String>): List<String> =
        lines.map { it.substringAfter(":").trim() }

    /**
     * What do you get if you multiply these numbers together?
     */
    override fun solvePart1(input: List<String>): Long =
        input.map { line -> line.split("\\s+".toRegex()).map { it.toLong() } }
            .let { (times, distances) -> (times zip distances) }
            .map { (time, distance) -> Race(time, distance) }
            .map { it.waysToBeatRecord() }.reduce(Long::times)

    /**
     * How many ways can you beat the record in this one much longer race?
     */
    override fun solvePart2(input: List<String>): Long =
        input.map { it.replace(" ", "").toLong() }
            .let { (time, distance) -> Race(time, distance) }
            .waysToBeatRecord()

    private fun Race.waysToBeatRecord(): Long {
        // x == holding time
        // x * (time - x) > distance
        // x^2 - time * x + distance < 0
        val b = -time.toDouble()
        val c = record.toDouble()
        val d = b * b - 4 * c
        return if (d > 0) {
            val x1 = (-b - sqrt(d)) / 2
            val x2 = (-b + sqrt(d)) / 2
            val min = ceil(x1).toLong()
            val max = floor(x2).toLong()
            val integers = listOf(x1, x2).count { truncate(it) == it }.toLong()
            max - min + 1 - integers
        } else {
            0
        }
    }

    data class Race(val time: Long, val record: Long)
}
