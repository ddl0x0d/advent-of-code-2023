package aoc2023

fun List<String>.split(delimiter: String): List<List<String>> {
    val result = mutableListOf<List<String>>()
    val section = mutableListOf<String>()
    forEach { line ->
        if (line != delimiter) {
            section += line
        } else {
            if (section.isNotEmpty()) {
                result += section.toList()
            }
            section.clear()
        }
    }
    if (section.isNotEmpty()) {
        result += section.toList()
    }
    return result.toList()
}