package season_2017

import AbstractDay

/**
 * Created by melquiadess on 19/12/2017.
 * Part 1:
 *  UICRK - wrong
 *  UICRNSDOK
 * Part 2:
 */
class Day19 : AbstractDay("Day19") {
    var list: MutableList<String> = mutableListOf()

    val EMPTY_SQUARE = "x"

    val ANSI_RESET = "\u001B[0m"
    val ANSI_BLACK = "\u001B[30m"
    val ANSI_RED = "\u001B[31m"
    val ANSI_GREEN = "\u001B[32m"
    val ANSI_YELLOW = "\u001B[33m"
    val ANSI_BLUE = "\u001B[34m"
    val ANSI_PURPLE = "\u001B[35m"
    val ANSI_CYAN = "\u001B[36m"
    val ANSI_WHITE = "\u001B[37m"

    val ANSI_BLACK_BACKGROUND = "\u001B[40m"
    val ANSI_RED_BACKGROUND = "\u001B[41m"
    val ANSI_GREEN_BACKGROUND = "\u001B[42m"
    val ANSI_YELLOW_BACKGROUND = "\u001B[43m"
    val ANSI_BLUE_BACKGROUND = "\u001B[44m"
    val ANSI_PURPLE_BACKGROUND = "\u001B[45m"
    val ANSI_CYAN_BACKGROUND = "\u001B[46m"
    val ANSI_WHITE_BACKGROUND = "\u001B[47m"

    var tube =Array(1, { Array(1, {"x"})})

    var width = 0
    var height = 0

    private fun getInput() {
        list = readFile("src/season_2017/input/day19-input")
//        list = readFile("src/season_2017/input/day19-input-test")

        width = list.maxBy { it.length }!!.length
        height = list.size

        tube = Array(height, { Array(width, { EMPTY_SQUARE }) })

        list.forEachIndexed { y, line ->
            var lineExtra = line
            if (line.length < width) {
                for (i in 0 until width-line.length) {
                    lineExtra += " "
                }
            }
            lineExtra.forEachIndexed { x, character ->
                tube[y][x] = character.toString()
            }
        }
    }

    private fun printTube() {
        tube.forEach { y ->
            println()
            y.forEach { x ->
                if (x in "|-") {
                    print(x)
                } else if (x in "+") {
                    print(ANSI_GREEN + x + ANSI_RESET)
                } else {
                    print(ANSI_YELLOW + x + ANSI_RESET)
                }
            }
        }
    }

    enum class Dir { LEFT, RIGHT, UP, DOWN }
    var dir: Dir = Dir.DOWN

    override fun part1() {
        println("PART 1")
        getInput()

        printTube()

        var start = tube[0].indexOf("|")
        var pos: Pair<Int, Int> = Pair(0, start)
        var current = tube[pos.first][pos.second]

        println("\n\n")

        var verticalDirection = 0
        var horizontalDirection = 0

        var next = ""
        var steps = 0

        var letters = ""

        loop@ while (true) {
            if (current.isLetter()) letters += current

            println("current: $current")
            steps++
            when (current) {
                "|", "-" -> {
                    if (dir == Dir.DOWN && pos.first < height-1) {
                        verticalDirection = 1
                        horizontalDirection = 0

                        pos = Pair(pos.first + verticalDirection, pos.second + horizontalDirection)
                        next = tube[pos.first][pos.second]
                        current = next
                        continue@loop
                    }
                    if (dir == Dir.UP && pos.first > 0) {
                        verticalDirection = -1
                        horizontalDirection = 0

                        pos = Pair(pos.first + verticalDirection, pos.second + horizontalDirection)
                        next = tube[pos.first][pos.second]
                        current = next
                        continue@loop
                    }
                    if (dir == Dir.LEFT && pos.second > 0) {
                        horizontalDirection = -1
                        verticalDirection = 0

                        pos = Pair(pos.first + verticalDirection, pos.second + horizontalDirection)
                        next = tube[pos.first][pos.second]
                        current = next
                        continue@loop
                    }
                    if (dir == Dir.RIGHT && pos.second < width-1) {
                        horizontalDirection = 1
                        verticalDirection = 0

                        pos = Pair(pos.first + verticalDirection, pos.second + horizontalDirection)
                        next = tube[pos.first][pos.second]
                        current = next
                    }
                }
                "+" -> {
                    if (dir == Dir.DOWN || dir == Dir.UP) {
                        // check left
                        if (pos.second > 0) {
                            next = tube[pos.first][pos.second-1]
                            if (next != " ") {
                                current = next
                                dir = Dir.LEFT
                                pos = Pair(pos.first, pos.second-1)

                                verticalDirection = 0
                                horizontalDirection = -1

                                continue@loop
                            }
                        }
                        if (pos.second < width-1) {
                            // right
                            next = tube[pos.first][pos.second+1]

                            if (next != " ") {
                                current = next
                                dir = Dir.RIGHT
                                pos = Pair(pos.first, pos.second+1)

                                verticalDirection = 0
                                horizontalDirection = 1

                                continue@loop
                            }
                        }
                    }
                    if (dir == Dir.LEFT || dir == Dir.RIGHT) {
                        if (pos.first > 0) {
                            // up
                            next = tube[pos.first-1][pos.second]

                            if (next != " ") {
                                current = next
                                dir = Dir.UP
                                pos = Pair(pos.first-1, pos.second)

                                verticalDirection = -1
                                horizontalDirection = 0

                                continue@loop
                            }
                        }
                        if (pos.first < height-1) {
                            // down
                            next = tube[pos.first+1][pos.second]

                            if (next != " ") {
                                current = next
                                dir = Dir.DOWN
                                pos = Pair(pos.first+1, pos.second)

                                verticalDirection = 1
                                horizontalDirection = 0

                                continue@loop
                            }
                        }
                    }
                }
                // letter
                else -> {
                    // check if something is behind
                    if (dir == Dir.DOWN) {
                        if (pos.first < height-1) {
                            verticalDirection = 1
                            horizontalDirection = 0

                            pos = Pair(pos.first + verticalDirection, pos.second + horizontalDirection)
                            next = tube[pos.first][pos.second]
                            current = next

                            if (next == " ") break@loop
                        }
                    }
                    if (dir == Dir.UP) {
                        if (pos.first > 0) {
                            verticalDirection = -1
                            horizontalDirection = 0

                            pos = Pair(pos.first + verticalDirection, pos.second + horizontalDirection)
                            next = tube[pos.first][pos.second]
                            current = next

                            if (next == " ") break@loop
                        }
                    }
                    if (dir == Dir.LEFT) {
                        if (pos.second > 0) {
                            horizontalDirection = -1
                            verticalDirection = 0

                            pos = Pair(pos.first + verticalDirection, pos.second + horizontalDirection)
                            next = tube[pos.first][pos.second]
                            current = next

                            if (next == " ") break@loop
                        }
                    }
                    if (dir == Dir.RIGHT) {
                        if (pos.second < width-1) {
                            horizontalDirection = 1
                            verticalDirection = 0

                            pos = Pair(pos.first + verticalDirection, pos.second + horizontalDirection)
                            next = tube[pos.first][pos.second]
                            current = next

                            if (next == " ") break@loop
                        }
                    }
                }
            }
        }

        println("Letters collected: $letters in $steps steps")
    }

    private fun String.isLetter() = this.isNotEmpty() && this in "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    override fun part2() {
        println("PART 2")
    }
}