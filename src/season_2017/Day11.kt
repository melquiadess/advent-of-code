package season_2017

import AbstractDay
import java.util.*

/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 *  861 - too high
 *
 * Part 2:
 */
class Day11 : AbstractDay("Day11") {
    var WIDTH = 1000
    var HEIGHT = 1000

    val EMPTY_SQUARE = "o"

    var MAZE = Array(WIDTH, { Array(HEIGHT, { it -> EMPTY_SQUARE }) })

    val START_POSITION = Pair(0, 0)
    var cp = START_POSITION

    var list: MutableList<String> = mutableListOf()

    private fun getInput() {
        list = readFile("src/season_2017/input/day11-input")
    }

    override fun part1() {
        println("PART 1")
        getInput()

//        list = mutableListOf("ne,ne,ne")
//        list = mutableListOf("ne,ne,sw,sw")
//        list = mutableListOf("ne,ne,s,s")
//        list = mutableListOf("se,sw,se,sw,sw")

        println(list)

        val moves = list.get(0).split(",")

        moves.forEach { move ->
            println("Before moving $move: $cp")
            cp = moveOnGrid(cp, move)
            println("After moving $move : $cp")
            println()
        }

        println("Finished at position: $cp")

        var steps = 0

        // to get to 176 on y, we need to move 176*2 times (starting at 0,0)
        // so now we're at -352, 176, and now we move to -773 from -352,
        // so first, me move to -772 from -352 in 420 steps, and 1 more to -773
        // so 176*2 + 420 + 1 = 352 + 420 + 1 = 773

        println("Steps needed: $steps")
    }

    override fun part2() {
        println("PART 2")
    }

    private fun moveOnGrid(currentPosition: Pair<Int, Int>, dir: String): Pair<Int, Int> {
        var xOffset: Int = 0
        var yOffset: Int = 0

        val even = currentPosition.first.rem(2) == 0

        when (dir) {
            "n" -> {
                xOffset = 0
                yOffset = -1
            }
            "ne" -> {
                xOffset = 1
                yOffset = if (even) 0 else -1
            }
            "se" -> {
                xOffset = 1
                yOffset = if (even) 1 else 0
            }
            "s" -> {
                xOffset = 0
                yOffset = 1
            }
            "sw" -> {
                xOffset = -1
                yOffset = if (even) 1 else 0
            }
            "nw" -> {
                xOffset = -1
                yOffset = if (even) 0 else -1
            }
        }

        return Pair(currentPosition.first + xOffset, currentPosition.second + yOffset)
    }

    /**
     * BFS algorithm from http://stackoverflow.com/a/30552530/2964945
     * Made into Kotlin
     */
    private fun findShortestPath(startLocation: Pair<Int, Int>, endLocation: Pair<Int, Int>): LinkedList<Pair<Int, Int>> {
        // This double array keeps track of the "level" of each node.
        // The level increments, starting at the startLocation to represent the path
        val levelArray = Array(WIDTH) { IntArray(HEIGHT) }

        // Assign every free space as 0, every wall as -1
        for (y in 0..HEIGHT - 1)
            for (x in 0..WIDTH - 1) {
                if (MAZE[x][y].equals(EMPTY_SQUARE))
                    levelArray[x][y] = 0
                else
                    levelArray[x][y] = -1
            }

        // Keep track of the traversal in a queue
        val queue = LinkedList<Pair<Int, Int>>()
        queue.add(startLocation)

        // Mark starting point as 1
        levelArray[startLocation.first][startLocation.second] = 1

        // Mark every adjacent open node with a numerical level value
        while (!queue.isEmpty()) {
            val point = queue.poll()
            // Reached the end
            if (point.equals(endLocation))
                break

            val level = levelArray[point.first][point.second]
            val possibleMoves = ArrayList<Pair<Int, Int>>()
            // Move N
            possibleMoves.add(Pair(point.first, point.second - 1))
            // Move NE
            possibleMoves.add(Pair(point.first + 1, point.second - 1))
            // Move SE
            possibleMoves.add(Pair(point.first + 1, point.second))
            // Down S
            possibleMoves.add(Pair(point.first, point.second + 1))
            // Move SW
            possibleMoves.add(Pair(point.first - 1, point.second))
            // Move NW
            possibleMoves.add(Pair(point.first - 1, point.second - 1))

            for (potentialMove in possibleMoves) {
                if (isInMaze(potentialMove.first, potentialMove.second)) {
                    // Able to move here if it is labeled as 0
                    if (levelArray[potentialMove.first][potentialMove.second] == 0) {
                        queue.add(potentialMove)
                        // Set this adjacent node as level + 1
                        levelArray[potentialMove.first][potentialMove.second] = level + 1
                    }
                }
            }
        }
        // Couldn't find solution
        if (levelArray[endLocation.first][endLocation.second] == 0)
            return LinkedList()

        val shortestPath = LinkedList<Pair<Int, Int>>()
        var pointToAdd = endLocation

        while (!pointToAdd.equals(startLocation)) {
            shortestPath.push(pointToAdd)
            val level = levelArray[pointToAdd.first][pointToAdd.second]
            val possibleMoves = ArrayList<Pair<Int, Int>>()
            // Move N
            possibleMoves.add(Pair(pointToAdd.first, pointToAdd.second - 1))
            // Move NE
            possibleMoves.add(Pair(pointToAdd.first + 1, pointToAdd.second - 1))
            // Move SE
            possibleMoves.add(Pair(pointToAdd.first + 1, pointToAdd.second))
            // Down S
            possibleMoves.add(Pair(pointToAdd.first, pointToAdd.second + 1))
            // Move SW
            possibleMoves.add(Pair(pointToAdd.first - 1, pointToAdd.second))
            // Move NW
            possibleMoves.add(Pair(pointToAdd.first - 1, pointToAdd.second - 1))

            for (potentialMove in possibleMoves) {
                if (isInMaze(potentialMove.first, potentialMove.second)) {
                    // The shortest level will always be level - 1, from this current node.
                    // Longer paths will have higher levels.
                    if (levelArray[potentialMove.first][potentialMove.second] == level - 1) {
                        pointToAdd = potentialMove
                        break
                    }
                }
            }
        }

        return shortestPath
    }

    fun isInMaze(x: Int, y:Int): Boolean = x>=0 && x<WIDTH && y>=0 && y<HEIGHT
}