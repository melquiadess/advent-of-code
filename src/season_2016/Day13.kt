package season_2016

import BaseDay

import java.util.LinkedList
import java.util.ArrayList

/**
 * Created by melquiadess on 12/12/2016.
 */
class Day13 : BaseDay {
    var WIDTH = 200
    var HEIGHT = 200
    var SECRET = 1350

    var MAZE = Array(WIDTH, { Array(HEIGHT, { it -> "." }) })

    var start  = Pair(1,1)
    var finish = Pair(31,39)

    override fun run() {
        println("Day13")

        buildMaze(SECRET)

//        println()
//        printMaze(MAZE)

//        solvePartOne()
        var locations = solvePartTwo()
        println(locations)

    }

    fun solvePartOne() {
        var path = findShortestPath(start, finish)
        println()
        println(path)
        println(path.size)
    }

    fun solvePartTwo() {
        var steps: LinkedList<Pair<Int, Int>> = LinkedList()

        while (true) {
            var x = (Math.random() * WIDTH).toInt()
            var y = (Math.random() * HEIGHT).toInt()

            var endPosition = Pair(x, y)

            steps = findShortestPath(start, endPosition)
            if (steps.size == 50) break

        }

//        outer@ for (y in 20..100) {
//            for (x in 20..100) {
//                var endPosition = Pair(x, y)
//
//                steps = findShortestPath(start, endPosition)
//                if (steps.size == 50) break@outer
//            }
//        }

        println(steps)

    }

    fun buildMaze(secret: Int): Array<Array<String>> {
        var sum: Int

        for (y in 0..HEIGHT-1) {
            for (x in 0..WIDTH-1) {
                sum = x*x + 3*x + 2*x*y + y + y*y
                sum += secret

                var binary = Integer.toBinaryString(sum)

                var oneCount = binary.count { it.equals('1') }

                MAZE[x][y] = if (oneCount % 2 == 0) "." else "#"

                println("sum: $sum binary: $binary 1s: $oneCount")
            }
        }

        return MAZE
    }

    fun printMaze(maze: Array<Array<String>>) {
        for (y in 0..HEIGHT-1) {
            print(y.toString() + " ")
            for (x in 0..WIDTH-1) {
                print(maze[x][y])
            }
            println()
        }
    }

    fun isInMaze(x: Int, y:Int): Boolean = x>=0 && x<WIDTH && y>=0 && y<HEIGHT

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
                if (MAZE[x][y].equals("."))
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
            possibleMoves.add(Pair<Int, Int>(pointToAdd.first + 1, pointToAdd.second))
            // Down Move
            possibleMoves.add(Pair<Int, Int>(pointToAdd.first, pointToAdd.second - 1))
            // Move Left
            possibleMoves.add(Pair<Int, Int>(pointToAdd.first - 1, pointToAdd.second))
            // Move Up
            possibleMoves.add(Pair<Int, Int>(pointToAdd.first, pointToAdd.second + 1))

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

}