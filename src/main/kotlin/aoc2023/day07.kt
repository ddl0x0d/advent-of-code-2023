package aoc2023

import aoc2023.Day07.Hand
import aoc2023.Day07.Hand.Type.*

/**
 * [Day 7: Camel Cards](https://adventofcode.com/2023/day/7)
 */
object Day07 : Puzzle<List<Hand>, Long> {

    override val day: Int = 7
    override val name: String = "🏜🐫🃏 Camel Cards"

    override fun parseInput(lines: List<String>): List<Hand> =
        lines.map { it.split(" ").let { (cards, bid) -> Hand(cards, bid.toInt()) } }

    /**
     * What are the total winnings?
     */
    override fun solvePart1(input: List<Hand>): Long = input.totalWinnings("23456789TJQKA", joker = false)

    /**
     * What are the new total winnings?
     */
    override fun solvePart2(input: List<Hand>): Long = input.totalWinnings("J23456789TQKA", joker = true)

    private fun List<Hand>.totalWinnings(cardsByStrength: String, joker: Boolean = false): Long {
        val strengthMap: Map<Char, Char> = cardsByStrength.mapIndexed { index, card -> card to 'a' + index }.toMap()
        val comparator = compareBy<Hand> { type(it.cards, joker) }.thenBy { strength(it, strengthMap) }
        return sortedWith(comparator).mapIndexed { index, hand -> hand.bid * (index + 1) }.sumOf { it.toLong() }
    }

    fun type(cards: String, joker: Boolean): Hand.Type {
        val cardCount: Map<Char, Int> = cards.groupingBy { it }.eachCount()
        val jokers = if (joker) cardCount['J'] ?: 0 else 0
        val pairs = cardCount.count { it.value == 2 }
        return when {
            cardCount.containsValue(5) -> FIVE_OF_A_KIND
            cardCount.containsValue(4) -> when {
                jokers > 0 -> FIVE_OF_A_KIND
                else -> FOUR_OF_A_KIND
            }

            cardCount.containsValue(3) -> when {
                jokers == 3 && pairs == 1 -> FIVE_OF_A_KIND
                jokers == 3 -> FOUR_OF_A_KIND
                jokers == 2 -> FIVE_OF_A_KIND
                jokers == 1 -> FOUR_OF_A_KIND
                pairs == 1 -> FULL_HOUSE
                else -> THREE_OF_A_KIND
            }

            pairs == 2 -> when (jokers) {
                2 -> FOUR_OF_A_KIND
                1 -> FULL_HOUSE
                else -> TWO_PAIR
            }

            pairs == 1 -> when {
                jokers > 0 -> THREE_OF_A_KIND
                else -> ONE_PAIR
            }

            jokers == 1 -> ONE_PAIR

            else -> HIGH_CARD
        }
    }

    private fun strength(hand: Hand, strengthMap: Map<Char, Char>): String =
        hand.cards.map { strengthMap.getValue(it) }.joinToString("")

    data class Hand(val cards: String, val bid: Int) {
        enum class Type {
            HIGH_CARD,
            ONE_PAIR,
            TWO_PAIR,
            THREE_OF_A_KIND,
            FULL_HOUSE,
            FOUR_OF_A_KIND,
            FIVE_OF_A_KIND,
        }
    }
}
