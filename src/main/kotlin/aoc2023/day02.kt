package aoc2023

import aoc2023.Day02.Color
import aoc2023.Day02.Color.*
import kotlin.math.max

typealias CubeSet = Map<Color, Int>

/**
 * [Day 2: Cube Conundrum](https://adventofcode.com/2023/day/2)
 */
object Day02 : Puzzle<List<Day02.Game>, Int> {

    override val day: Int = 2
    override val name: String = "🟥🟩🟦 Cube Conundrum"

    override fun parseInput(lines: List<String>): List<Game> =
        lines.map { line ->
            val (game, cubeSets) = line.split(": ")
            Game(
                id = game.removePrefix("Game ").toInt(),
                cubeSets = cubeSets.split("; ").map { cubeSet ->
                    cubeSet.split(", ").associate { cubes ->
                        val (amount, color) = cubes.split(" ")
                        valueOf(color.uppercase()) to amount.toInt()
                    }
                }
            )
        }

    /**
     * What is the sum of the IDs of those games?
     */
    override fun solvePart1(input: List<Game>): Int {
        val bag = mapOf(RED to 12, GREEN to 13, BLUE to 14)
        return input.filter { it.isPossible(bag) }.sumOf { it.id }
    }

    private fun Game.isPossible(bag: CubeSet): Boolean =
        cubeSets.all { cubeSet -> cubeSet.all { it.value <= (bag[it.key] ?: 0) } }

    /**
     * What is the sum of the power of these sets?
     */
    override fun solvePart2(input: List<Game>): Int =
        input.map { it.minCubeSet() }.sumOf { it.power() }

    private fun Game.minCubeSet(): CubeSet =
        cubeSets.fold(mutableMapOf()) { minCubeSet, cubeSet ->
            cubeSet.entries.fold(minCubeSet) { nestedMinCubeSet, (color, cubes) ->
                nestedMinCubeSet.apply { this[color] = max(this[color] ?: 0, cubes) }
            }
        }

    private fun CubeSet.power(): Int = values.reduce(Int::times)

    data class Game(val id: Int, val cubeSets: List<CubeSet>)
    enum class Color { RED, GREEN, BLUE }
}
