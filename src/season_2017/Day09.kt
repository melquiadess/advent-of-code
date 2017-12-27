package season_2017

import AbstractDay
/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 *
 * Part 2:
 */
class Day09 : AbstractDay("Day09") {
    var list: MutableList<String> = mutableListOf()

    override fun part1() {
        println("PART 1")

        getInput()
        testInputs()
        removeGarbage(list[0])

        println(list)
    }

    private fun removeGarbage(line: String): String {
        var parts: MutableList<String> = mutableListOf()

        var current: Char
        var isInGarbage = false
        var index = 0

        while (true) {
            current = line[index]

            val openingCount = line.count { it == '<' }

            val openingIndex = line.indexOf("<")

            if (openingIndex > -1) {
                val endingIndex = line.indexOf(">", openingIndex)
                println(endingIndex)
            }

            if (index == line.length-1) break
        }

        return ""
    }

    private fun getInput() {
        list = readFile("src/season_2017/input/day09-input")
    }

    private fun process(list: MutableList<String>) {
        list.forEach { line ->

        }
    }

    override fun part2() {
        println("PART 2")
    }

    private fun testInputs() {
        list = mutableListOf(
                "<<<<>",
                "{}",
                "{{{}}}",
                "{{},{}}",
                "{{{},{},{{}}}}",
                "{<{},{},{{}}>}",
                "{<a>,<a>,<a>,<a>}",
                "{{<a>},{<a>},{<a>},{<a>}}",
                "{{<!>},{<!>},{<!>},{<a>}}"
        )
    }
}