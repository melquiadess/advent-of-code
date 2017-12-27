package season_2017

import AbstractDay
import java.util.*

/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 *
 * Part 2:
 */
class Day11 : AbstractDay("Day11") {
    var WIDTH = 1000
    var HEIGHT = 1000

    val EMPTY_SQUARE = "o"

    var MAZE = Array(WIDTH, { Array(HEIGHT, { it -> EMPTY_SQUARE }) })

    var start  = Pair(0, 0)
    var finish = Pair(0, 0)

    var cp = Pair(0, 0)

    var list: MutableList<String> = mutableListOf()

    private fun getInput() {
        list = readFile("src/season_2017/input/day11-input")

        list = mutableListOf("ne,ne,ne")
    }

    override fun part1() {
        println("PART 1")
        getInput()

        println(list)

        val moves = list.get(0).split(",")

        moves.forEach { move ->
            cp = move(cp, move)
        }

        println("Current position: $cp")

        println("Path: ${findShortestPath(Pair(0, 0), cp)}")
        println("Path length: ${findShortestPath(Pair(0, 0), cp).size}")
    }

    override fun part2() {
        println("PART 2")
    }

    private fun move(currentPosition: Pair<Int, Int>, dir: String): Pair<Int, Int> {
        return when (dir) {
            "n" -> Pair(currentPosition.first, currentPosition.second + 1)
            "ne" -> Pair(currentPosition.first + 1, currentPosition.second + 1)
            "e" -> Pair(currentPosition.first - 1, currentPosition.second)
            "se" -> Pair(currentPosition.first + 1, currentPosition.second - 1)
            "s" -> Pair(currentPosition.first, currentPosition.second - 1)
            "sw" -> Pair(currentPosition.first - 1, currentPosition.second - 1)
            "w" -> Pair(currentPosition.first - 1, currentPosition.second)
            "nw" -> Pair(currentPosition.first - 1, currentPosition.second + 1)
            else -> Pair(-11111, -11111)
        }
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
            // Move Up
            possibleMoves.add(Pair(point.first, point.second + 1))
            // Move Left
            possibleMoves.add(Pair(point.first - 1, point.second))
            // Down Move
            possibleMoves.add(Pair(point.first, point.second - 1))
            // Move Right
            possibleMoves.add(Pair(point.first + 1, point.second))

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
            // Move Right
            possibleMoves.add(Pair(pointToAdd.first + 1, pointToAdd.second))
            // Down Move
            possibleMoves.add(Pair(pointToAdd.first, pointToAdd.second - 1))
            // Move Left
            possibleMoves.add(Pair(pointToAdd.first - 1, pointToAdd.second))
            // Move Up
            possibleMoves.add(Pair(pointToAdd.first, pointToAdd.second + 1))

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