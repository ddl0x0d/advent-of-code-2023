package aoc2023

import com.github.tomakehurst.wiremock.WireMockServer
import com.marcinziolo.kotlin.wiremock.equalTo
import com.marcinziolo.kotlin.wiremock.get
import com.marcinziolo.kotlin.wiremock.returns
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.datatest.WithDataTestName
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

data class TestCase(val path: String, val expected: Day) : WithDataTestName {
    override fun dataTestName(): String = path
}

class WebTests : ExpectSpec({
    val wiremock = WireMockServer()
    beforeTest { wiremock.start() }
    afterTest { wiremock.stop() }

    context("day title and example scraping") {
        withData(
            //@formatter:off
            TestCase(path = "day1.html", Day("Calorie Counting", "1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000")),
            TestCase(path = "day2.html", Day("Rock Paper Scissors", "A Y\nB X\nC Z")),
            TestCase(path = "day3.html", Day("Rucksack Reorganization", "vJrwpWtwJgWrhcsFMMfFFhFp\njqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\nPmmdzqPrVvPwwTWBwg\nwMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\nttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw")),
            TestCase(path = "day4.html", Day("Camp Cleanup", "2-4,6-8\n2-3,4-5\n5-7,7-9\n2-8,3-7\n6-6,4-6\n2-6,4-8")),
            TestCase(path = "day5.html", Day("Supply Stacks", "    [D]\n[N] [C]\n[Z] [M] [P]\n 1   2   3\n\nmove 1 from 2 to 1\nmove 3 from 1 to 3\nmove 2 from 2 to 1\nmove 1 from 1 to 2")),
            TestCase(path = "day6.html", Day("Tuning Trouble", "mjqjpqmgbljsphdztnvjfqwrcgsmlb")),
            TestCase(path = "day7.html", Day("No Space Left On Device", "\$ cd /\n\$ ls\ndir a\n14848514 b.txt\n8504156 c.dat\ndir d\n\$ cd a\n\$ ls\ndir e\n29116 f\n2557 g\n62596 h.lst\n\$ cd e\n\$ ls\n584 i\n\$ cd ..\n\$ cd ..\n\$ cd d\n\$ ls\n4060174 j\n8033020 d.log\n5626152 d.ext\n7214296 k")),
            TestCase(path = "day8.html", Day("Treetop Tree House", "30373\n25512\n65332\n33549\n35390")),
            TestCase(path = "day9.html", Day("Rope Bridge", "R 4\nU 4\nL 3\nD 1\nR 4\nD 1\nL 5\nR 2")),
            //@formatter:on
        ) { (path, expected) ->
            val html = javaClass.classLoader.getResourceAsStream(path)
                ?.bufferedReader()?.readText() ?: error("Couldn't load HTML from $path")
            wiremock.get { urlPath equalTo "/" }.returns { body = html }

            val result = scrape(url = "http://localhost:${wiremock.port()}")

            result shouldBeEqual expected
        }
    }
})
