package aoc2023

import aoc2023.Day05.Almanac

/**
 * [Day 5: If You Give A Seed A Fertilizer](https://adventofcode.com/2023/day/5)
 */
object Day05 : Puzzle<Almanac, Long> {

    override val day: Int = 5
    override val name: String = "🌱🧭🌾 If You Give A Seed A Fertilizer"

    override fun parseInput(lines: List<String>) = Almanac(
        seeds = lines.first().removePrefix("seeds: ").split(" ").map { it.toLong() },
        maps = lines.drop(1).split("").map { almanacMap ->
            almanacMap.drop(1).map { almanacMapEntry: String ->
                almanacMapEntry.split(" ").map { it.toLong() }
                    .let { (dstRangeStart, srcRangeStart, rangeLength) ->
                        val range = srcRangeStart..<(srcRangeStart + rangeLength)
                        val offset = dstRangeStart - srcRangeStart
                        range to offset
                    }
            }.sortedBy { it.first.first }.toMap()
        }
    )

    /**
     * What is the lowest location number that corresponds to any of the initial seed numbers?
     */
    override fun solvePart1(input: Almanac): Long =
        input.seeds.minOf { input.seedToLocation(it) }

    private fun Almanac.seedToLocation(seed: Long): Long =
        maps.fold(seed) { value, map ->
            value + (map.entries.firstOrNull { value in it.key }?.value ?: 0)
        }

    /**
     * What is the lowest location number that corresponds to any of the initial seed numbers?
     */
    override fun solvePart2(input: Almanac): Long {
        val seedRanges: List<LongRange> = input.seeds.chunked(2)
            .map { (start, end) -> start..<(start + end) }
            .sortedBy { it.first }
        return input.seedsToLocations(seedRanges).minOf { it.first }
    }

    private fun Almanac.seedsToLocations(seeds: List<LongRange>): List<LongRange> =
        maps.fold(seeds) { ranges, map ->
            ranges.flatMap { explode(it, map) }.sortedBy { it.first }
        }

    private fun explode(range: LongRange, map: Map<LongRange, Long>): List<LongRange> =
        map.mapKeys { (it.key intersection range) }
            .filterNot { it.key.isEmpty() }
            .takeIf { it.isNotEmpty() }
            ?.let { intersectionOffsets ->
                (range explode intersectionOffsets.keys.toList()).map {
                    intersectionOffsets[it]?.let { offset -> it + offset } ?: it
                }
            } ?: listOf(range)

    private infix fun LongRange.intersection(other: LongRange): LongRange =
        maxOf(first, other.first)..minOf(last, other.last)

    private infix fun LongRange.explode(ranges: List<LongRange>): List<LongRange> {
        val first = first..<ranges.first().first
        val middle = ranges.windowed(2).flatMap { (r1, r2) ->
            listOf(r1, (r1.last + 1)..<r2.first, r2)
        }.takeIf { it.isNotEmpty() } ?: ranges
        val last = (ranges.last().last + 1)..last
        return (listOf(first) + middle + listOf(last)).filterNot { it.isEmpty() }
    }

    private operator fun LongRange.plus(offset: Long) = (first + offset)..(last + offset)

    class Almanac(val seeds: List<Long>, val maps: List<Map<LongRange, Long>>)
}
