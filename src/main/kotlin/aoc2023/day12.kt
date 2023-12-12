package aoc2023

import aoc2023.Day12.ConditionRecord

/**
 * [Day 12: Hot Springs](https://adventofcode.com/2023/day/12)
 */
object Day12 : Puzzle<List<ConditionRecord>, Int> {

    override val day: Int = 12
    override val name: String = "Hot Springs"

    private const val OPERATIONAL = '.'
    private const val DAMAGED = '#'
    private const val UNKNOWN = '?'
    private val nonDamagedRegex = "[^#]+".toRegex()

    override fun parseInput(lines: List<String>): List<ConditionRecord> =
        lines.map { line ->
            line.split(" ").let { (springs, damaged) ->
                ConditionRecord(
                    springs = springs,
                    damaged = damaged.split(",").map { it.toInt() }
                )
            }
        }

    /**
     * What is the sum of those counts?
     */
    override fun solvePart1(input: List<ConditionRecord>): Int = input.sumOf { it.arrangements() }

    override fun solvePart2(input: List<ConditionRecord>): Int = input.sumOf { it.unfold().arrangements() }

    private fun ConditionRecord.unfold() =
        ConditionRecord(
            springs = List(5) { springs }.joinToString("?"),
            damaged = List(5) { damaged }.flatten()
        )

    private fun ConditionRecord.arrangements(): Int =
        if (isComplete()) {
            if (isCorrect()) 1 else 0
        } else {
            val operational = copy(springs = springs.replaceFirst(UNKNOWN, OPERATIONAL))
            val damaged = copy(springs = springs.replaceFirst(UNKNOWN, DAMAGED))
            operational.arrangements() + damaged.arrangements()
        }

    private fun ConditionRecord.isCorrect(): Boolean =
        damaged == springs.split(nonDamagedRegex)
            .filter { it.isNotEmpty() }.map { it.length }

    private fun ConditionRecord.isComplete(): Boolean = UNKNOWN !in springs

    data class ConditionRecord(val springs: String, val damaged: List<Int>)
}
