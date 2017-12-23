package season_2017

import AbstractDay

/**
 * Created by melquiadess on 21/12/2017.
 * Part 1:
 * Part 2:
 */
class Day22 : AbstractDay("Day21") {
    var list: MutableList<String> = mutableListOf()

    val ANSI_RESET = "\u001B[0m"
    val ANSI_GREEN = "\u001B[32m"

    var width = 2000
    var height = 2000

    var grid = Array(width, { Array(height, {"."}) })

    data class Virus(var x: Int, var y: Int, var dir: Day19.Dir = Day19.Dir.UP) {
        fun move(steps: Int) {
            when (dir) {
                Day19.Dir.UP ->     y -= steps
                Day19.Dir.RIGHT ->  x += steps
                Day19.Dir.DOWN ->   y += steps
                Day19.Dir.LEFT ->   x -= steps
            }
        }

        fun turn(direction: Day19.Dir) {
            when (direction) {
                Day19.Dir.LEFT -> dir = when (dir) {
                    Day19.Dir.UP -> Day19.Dir.LEFT
                    Day19.Dir.RIGHT -> Day19.Dir.UP
                    Day19.Dir.DOWN -> Day19.Dir.RIGHT
                    Day19.Dir.LEFT -> Day19.Dir.DOWN
                }
                Day19.Dir.RIGHT -> dir = when (dir) {
                    Day19.Dir.UP -> Day19.Dir.RIGHT
                    Day19.Dir.RIGHT -> Day19.Dir.DOWN
                    Day19.Dir.DOWN -> Day19.Dir.LEFT
                    Day19.Dir.LEFT -> Day19.Dir.UP
                }
            }
        }

        fun reverse() {
            dir = when (dir) {
                Day19.Dir.UP -> Day19.Dir.DOWN
                Day19.Dir.RIGHT -> Day19.Dir.LEFT
                Day19.Dir.DOWN -> Day19.Dir.UP
                Day19.Dir.LEFT -> Day19.Dir.RIGHT
            }
        }
    }

    var virus: Virus = Virus(0, 0)

    private fun getInput() {
        list.clear()
        list = readFile("src/season_2017/input/day22-input")
//        list = readFile("src/season_2017/input/day22-input-test")

        val minWidth = list[0].length
        val minHeight = list.size

        val xOffset = width/2 - minWidth/2
        val yOffset = height/2 - minHeight/2

        virus = Virus(width/2, height/2, Day19.Dir.UP)

        println("x: $xOffset y: $yOffset")

        grid = Array(width, { Array(height, {"."}) })

        list.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                grid[y+yOffset][x+xOffset] = c.toString()
            }
        }
    }

    private fun printGrid() {
        println()
        grid.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (virus.x == x && virus.y == y) {
                    print(ANSI_GREEN + c + ANSI_RESET)
                } else {
                    print(c)
                }
            }
            println()
        }
    }

    override fun part1() {
        println("PART 1")
        getInput()

        //printGrid()

        var burstsThatInfect = 0

        var currentNode: String = ""

        for (i in 0 until 10000) {
            currentNode = grid[virus.y][virus.x]
            //println("Virus is here: $virus")

            // if current node is infected - turn right, else turn left
            // if current node is clean - infect it, if it's infected - clean it
            if (currentNode == "#") {
                virus.turn(Day19.Dir.RIGHT)
                grid[virus.y][virus.x] = "."
            } else {
                virus.turn(Day19.Dir.LEFT)
                grid[virus.y][virus.x] = "#"
                burstsThatInfect++
            }

            // move forward one step
            virus.move(1)

            //printGrid()
        }

        //printGrid()
        println("After 10000 bursts, $burstsThatInfect caused infection")
    }

    override fun part2() {
        println()
        println("PART 2")

        getInput()

        //printGrid()

        var burstsThatInfect = 0

        var currentNode: String = ""

        for (i in 0 until 10000000) {
            currentNode = grid[virus.y][virus.x]
            //println("Virus is here: $virus")

            // if current node is infected - turn right, else turn left
            // if current node is clean - infect it, if it's infected - clean it
            when (currentNode) {
                "#" -> {
                    virus.turn(Day19.Dir.RIGHT)
                    grid[virus.y][virus.x] = "F"
                }
                "." -> {
                    virus.turn(Day19.Dir.LEFT)
                    grid[virus.y][virus.x] = "W"
                }
                "W" -> {
                    grid[virus.y][virus.x] = "#"
                    burstsThatInfect++
                }
                "F" -> {
                    grid[virus.y][virus.x] = "."
                    virus.reverse()
                }
            }

            // move forward one step
            virus.move(1)

            //printGrid()
        }

        //printGrid()
        println("After 10000 bursts, $burstsThatInfect caused infection")
    }
}