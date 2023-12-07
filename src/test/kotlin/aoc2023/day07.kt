package aoc2023

import aoc2023.Day07.Hand.Type.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class Day07Tests : StringSpec({
    withData(
        listOf(
            "23456" to HIGH_CARD,
            "2345J" to ONE_PAIR,
            "234JJ" to THREE_OF_A_KIND,
            "23JJJ" to FOUR_OF_A_KIND,
            "2JJJJ" to FIVE_OF_A_KIND,
            "JJJJJ" to FIVE_OF_A_KIND,
            "22456" to ONE_PAIR,
            "2245J" to THREE_OF_A_KIND,
            "224JJ" to FOUR_OF_A_KIND,
            "22JJJ" to FIVE_OF_A_KIND,
            "22446" to TWO_PAIR,
            "2244J" to FULL_HOUSE,
            "22256" to THREE_OF_A_KIND,
            "2225J" to FOUR_OF_A_KIND,
            "222JJ" to FIVE_OF_A_KIND,
            "22255" to FULL_HOUSE,
            "22226" to FOUR_OF_A_KIND,
            "2222J" to FIVE_OF_A_KIND,
            "22222" to FIVE_OF_A_KIND,
        )
    ) { (cards, expectedType) ->
        Day07.type(cards, joker = true) shouldBeEqual expectedType
    }
})