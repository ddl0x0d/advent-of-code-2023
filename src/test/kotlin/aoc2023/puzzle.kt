package aoc2023

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

val answers = listOf(
    Answers(Day01, 142, 281),
    Answers(Day02, 8, 2286),
    Answers(Day03, 4361, 467835),
    Answers(Day04, 13, 30),
    Answers(Day05, 35, 46),
    Answers(Day06, 288, 71503),
    Answers(Day07, 6440, 5905),
    Answers(Day08, 2, 6),
    Answers(Day09, 114, 2),
    Answers(Day10, 8, 10),
)

data class Answers<T>(
    val puzzle: Puzzle<*, T>,
    val answer1: T, val answer2: T,
) : WithDataTestName {
    override fun dataTestName(): String = "Day ${puzzle.day}: ${puzzle.name}"
}

class PuzzleTests : StringSpec({
    withData(answers) { (puzzle, answer1, answer2) ->
        val (part1, part2) = puzzle.solve()
        assertSoftly {
            withClue("Part 1 answer") { part1 shouldBe answer1 }
            withClue("Part 2 answer") { part2 shouldBe answer2 }
        }
    }
})
