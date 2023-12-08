package aoc2023

import aoc2023.Day08.Instruction.LEFT
import aoc2023.Day08.Instruction.RIGHT
import aoc2023.Day08.Network
import kotlin.io.path.readLines

/**
 * [Day 8: Haunted Wasteland](https://adventofcode.com/2023/day/8)
 */
object Day08 : Puzzle<Network, Long> {

    override val day: Int = 8
    override val name: String = "👻🧭🏜 Haunted Wasteland"

    private val nodesRegex = "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex()

    override fun parseInput(lines: List<String>): Network =
        Network(
            instructions = lines.first().map { if (it == 'L') LEFT else RIGHT },
            paths = lines.drop(2).associate {
                nodesRegex.matchEntire(it)?.destructured?.let { (from, left, right) ->
                    from to (left to right)
                } ?: error("Couldn't parse line $it")
            }
        )

    /**
     * How many steps are required to reach ZZZ?
     */
    override fun solvePart1(input: Network): Long =
        input.findLoopPath("AAA").withIndex().first { it.value == "ZZZ" }.index.toLong()

    /**
     * How many steps does it take before you're only on nodes that end with Z?
     */
    override fun solvePart2(input: Network): Long {
        val nodes = input.paths.keys.filter { it.endsWith("A") }
        val paths = nodes.map { input.findLoopPath(it) }
        // not a general solution, but input's loop size coincides with xxZ node index
        return paths.map { path ->
            val loopIndex = path.withIndex().first { (_, n) -> n == path.last() }.index
            (path.lastIndex - loopIndex).toLong()
        }.reduce(this::lcm)
    }

    private fun Network.findLoopPath(start: String): List<String> {
        var node = start
        var instructionIndex = 0
        val path = mutableListOf<String>()
        val visited = mutableSetOf<String>()
        do {
            path += node
            val step = "$node-$instructionIndex"
            val instruction = instructions[instructionIndex]
            instructionIndex = (instructionIndex + 1) % instructions.size
            node = paths.getValue(node).let { instruction.nextNode(it) }
        } while (visited.add(step))
        return path
    }

    private fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)

    private fun gcd(a: Long, b: Long): Long {
        val (min, max) = listOf(a, b).sorted()
        return if (min == 0L) max else gcd(min, max % min)
    }

    data class Network(
        val instructions: List<Instruction>,
        val paths: Map<String, Pair<String, String>>
    )

    enum class Instruction(val nextNode: (Pair<String, String>) -> String) {
        LEFT({ it.first }),
        RIGHT({ it.second }),
    }

    override fun solve(): Pair<Long, Long> {
        val (input1, input2) = (1..2).map {
            val example = example.resolveSibling("day-08-$it.txt")
            val lines = example.readLines()
            parseInput(lines)
        }
        val part1 = solvePart1(input1)
        val part2 = solvePart2(input2)
        return part1 to part2
    }
}
