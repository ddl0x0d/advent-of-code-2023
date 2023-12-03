package aoc2023

import aoc2023.Day03.Engine

/**
 * [Day 3: Gear Ratios](https://adventofcode.com/2023/day/3)
 */
object Day03 : Puzzle<Engine, Int> {

    override val day: Int = 3
    override val name: String = "🛠⚙🚡 Gear Ratios"

    private val partRegex = "(\\d+|[^\\d.])".toRegex()

    override fun parseInput(lines: List<String>): Engine {
        val (numbers, symbols) = lines.flatMapIndexed { row, line ->
            partRegex.findAll(line).map { match ->
                match.value.toIntOrNull()
                    ?.let { Number(it, row, match.range) }
                    ?: Symbol(match.value[0], row, match.range)
            }
        }.partition { it is Number }
        @Suppress("UNCHECKED_CAST")
        return Engine(numbers as List<Number>, symbols as List<Symbol>)
    }

    /**
     * What is the sum of all of the part numbers in the engine schematic?
     */
    override fun solvePart1(input: Engine): Int = input.partNumbers().sum()

    private fun Engine.partNumbers(): List<Int> {
        val rowSymbols = symbols.groupBy { it.row }
        return numbers.filter { number ->
            ((number.row - 1)..(number.row + 1)).any { row ->
                rowSymbols.getOrDefault(row, emptyList()).any { symbol ->
                    adjacent(number, symbol)
                }
            }
        }.map { it.number }
    }

    /**
     * What is the sum of all of the gear ratios in your engine schematic?
     */
    override fun solvePart2(input: Engine): Int = input.gearRatios().sum()

    private fun Engine.gearRatios(): List<Int> {
        val rowNumbers = numbers.groupBy { it.row }
        return symbols.filter { it.symbol == '*' }.map { symbol ->
            ((symbol.row - 1)..(symbol.row + 1)).flatMap { row ->
                rowNumbers.getOrDefault(row, emptyList()).filter { number ->
                    adjacent(number, symbol)
                }
            }.takeIf { it.size == 2 }?.map { it.number }?.reduce(Int::times) ?: 0
        }
    }

    private fun adjacent(number: Number, symbol: Symbol): Boolean =
        symbol.cols.first in ((number.cols.first - 1)..(number.cols.last + 1))

    data class Engine(val numbers: List<Number>, val symbols: List<Symbol>)
    data class Number(val number: Int, val row: Int, val cols: IntRange)
    data class Symbol(val symbol: Char, val row: Int, val cols: IntRange)
}
