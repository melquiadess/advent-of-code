package season_2017

import AbstractDay

/**
 * Created by melquiadess on 21/12/2017.
 * Part 1:
 * Part 2:
 */
class Day21 : AbstractDay("Day21") {
    var list: MutableList<String> = mutableListOf()

    var width = 0
    var height = 0

    var square = ".#./..#/###"

    /**
     *  ../.# => ##./#../...
        .#./..#/### => #..#/..../..../#..#
     */
    data class Pattern(val patternLine: String) {
        var patterns = mutableListOf<String>()

        val size = patterns.size

        fun matches(pattern: String) = patterns.contains(pattern)

        init {
            // first, split
            val splits = patternLine.split("/")
            if (splits.size == 2) {
                // normal
                // ..
                // .#
                patterns.add(patternLine)

                // rotated 90 right
                // ..
                // #.
                patterns.add("${splits[0]}/${splits[1].reversed()}")

                // rotated 180 right
                // #.
                // ..
                patterns.add("${splits[1].reversed()}/${splits[0]}")

                // rotated 270 right
                // .#
                // ..
                patterns.add("${splits[1]}/${splits[0]}")

                // flipped-horizontally = rotated 90 right
                // ..
                // #.
                //patterns.add("${splits[0]}/${splits[1].reversed()}")

            } else if (splits.size == 3) {
                // normal
                // .#.
                // ..#
                // ###
                patterns.add(patternLine)

                // rotate 90 right
                // #..
                // #.#
                // ##.
                patterns.add("${splits[2][0]}${splits[1][0]}${splits[0][0]}/${splits[2][1]}${splits[1][1]}${splits[0][1]}/${splits[2][2]}${splits[1][2]}${splits[0][2]}")

                // rotate 180 right
                // ###
                // #..
                // .#.
                patterns.add("${splits[2].reversed()}/${splits[1].reversed()}/${splits[0].reversed()}")

                // rotate 270 right
                // .##
                // #.#
                // ..#
                patterns.add("${splits[0][2]}${splits[1][2]}${splits[2][2]}/${splits[0][1]}${splits[1][1]}${splits[2][1]}/${splits[0][0]}${splits[1][0]}${splits[2][0]}")

                // flip horizontally
                // .#.
                // #..
                // ###
                patterns.add("${splits[0].reversed()}/${splits[1].reversed()}/${splits[2].reversed()}")

                // flip horizontally + rotate 90 right
                // ##.
                // #.#
                // #..
                patterns.add("${splits[2][2]}${splits[0][2]}${splits[2][2]}/${splits[2][1]}${splits[1][1]}${splits[0][1]}/${splits[2][0]}${splits[1][0]}${splits[0][0]}")

                // flip horizontally + rotate 180 right == flip vertically
                // ###
                // ..#
                // .#.
                patterns.add("${splits[2]}/${splits[1]}/${splits[0]}")

                // flip horizontally + rotate 270 right
                // ..#
                // #.#
                // .##
                patterns.add("${splits[0][0]}${splits[1][0]}${splits[2][0]}/${splits[0][1]}${splits[1][1]}${splits[2][1]}/${splits[0][2]}${splits[1][2]}${splits[2][2]}")
            }
        }
    }

    data class Rule(val input: String, val output: String) {
        private var pattern: Pattern = Pattern(input.trim())

        fun isMatching(str: String) = pattern.matches(str)
        fun getOutputSize() = pattern.size
    }

    val rules: MutableList<Rule> = mutableListOf()

    private fun getInput() {
//        list = readFile("src/season_2017/input/day21-input")
        list = readFile("src/season_2017/input/day21-input-test")

        // ../.# => ##./#../...
        // .#./..#/### => #..#/..../..../#..#
        list.forEach { line ->
            var splits = line.split(" => ")
            var matching = splits[0].trim()
            var output = splits[1].trim()

            rules.add(Rule(matching, output))
        }
    }

    private fun printGrid() {
        var splits = square.split("/")
        splits.forEach { println(it) }
    }

    override fun part1() {
        println("PART 1")
        getInput()

        printGrid()

        for (i in 0..4) {
            var size = square.count { it == '/' } + 1
            var splits = square.split("/")

            var newSquare = ""
            if (size.rem(2) == 0) {
                for (y in 0 until size/2) {
                    for (x in 0 until  size/2) {
                        newSquare += square
                    }
                }
                rules.forEach { rule ->
                    if (rule.isMatching(square)) {
                        square = rule.output
                    }
                }

            } else if (size.rem(3) == 0) {
                var howManySquares = size / 3 * 2
                for (s in 0 until size/3) {
                    var smallSquare = square
                }
            }
        }
    }

    override fun part2() {
        println()
        println("PART 2")
    }
}