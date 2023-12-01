package aoc2023

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.article
import it.skrape.selects.html5.h2
import it.skrape.selects.html5.main

data class Day(
    val title: String,
    val example: String,
)

private val headerRegex = "--- Day \\d+: ([\\w\\s?!]+) ---".toRegex()

fun scrape(day: Int): Day = scrape("https://adventofcode.com/$YEAR/day/$day")

fun scrape(url: String): Day = skrape(HttpFetcher) {
    request { this.url = url }
    response {
        htmlDocument {
            main {
                article {
                    findFirst {
                        val header = findFirst { h2 { findFirst { text } } }
                        val (title) = headerRegex.matchEntire(header)?.destructured
                            ?: error("Couldn't parse $header")
                        val example = children.dropWhile {
                            !it.text.lowercase().contains("for example")
                        }.first { it.tagName == "pre" }.element.wholeText().trimEnd()
                        Day(title, example)
                    }
                }
            }
        }
    }
}
