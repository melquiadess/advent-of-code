package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Created by melquiadess on 24/12/2016.
 */
class Day24 : BaseDay {
    var list: MutableList<String> = mutableListOf()

    var WIDTH = 185
    var HEIGHT = 39

    var MAZE = Array(WIDTH, { Array(HEIGHT, { it -> "." }) })

    var start  = Pair(1,1)
    var finish = Pair(31,39)

    data class Point(var x: Int, var y: Int, var value: Int = -1)

    var points = mutableListOf<Point>()

    override fun run() {
        println("Day24")

        readFile()

        buildMaze()

//        printMaze(MAZE)
//        println(points)
//        println(points.size)
//
//        permutePoints(points, 0)
//        printlnPermutations(permutations)
//        println(permutations.size)
        
        println(findShortestPath(points[0], points[1]))

    }

    private fun buildMaze() {
        for ((y, line) in list.withIndex()) {
            for (x in 0..WIDTH - 1) MAZE[x][y] = when (line[x]) {
                '#' -> "#"
                '.' -> "."
                else -> {
                    // extracts position and value of checkpoint
                    var point = line[x].toString().toInt()
                    var p = Point(x, y, point)
                    points.add(p)
                    "."
                }
            }
        }
    }

    fun printlnPermutations(list: MutableList<MutableList<Point>>) {
        var i=1
        for (p in list) {
            println("${i++} -> $p")
        }
    }

    var permutations = mutableListOf<MutableList<Point>>()

    fun permutePoints(points: MutableList<Point>, k: Int) {
        for (i in k..points.size - 1) {
            java.util.Collections.swap(points, i, k)
            permutePoints(points, k + 1)
            java.util.Collections.swap(points, k, i)
        }
        if (k == points.size - 1) {
            permutations.add(points)
        }
    }

    fun printMaze(maze: Array<Array<String>>) {
        for (y in 0..HEIGHT-1) {
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
    private fun findShortestPath(startLocation: Point, endLocation: Point): LinkedList<Point> {
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
        val queue = LinkedList<Point>()
        queue.add(startLocation)

        // Mark starting point as 1
        levelArray[startLocation.x][startLocation.y] = 1

        // Mark every adjacent open node with a numerical level value
        while (!queue.isEmpty()) {
            val point = queue.poll()
            // Reached the end
            if (point.equals(endLocation))
                break

            val level = levelArray[point.x][point.y]
            val possibleMoves = ArrayList<Point>()
            // Move Up
            possibleMoves.add(Point(point.x, point.y + 1))
            // Move Left
            possibleMoves.add(Point(point.x - 1, point.y))
            // Down Move
            possibleMoves.add(Point(point.x, point.y - 1))
            // Move Right
            possibleMoves.add(Point(point.x + 1, point.y))

            for (potentialMove in possibleMoves) {
                if (isInMaze(potentialMove.x, potentialMove.y)) {
                    // Able to move here if it is labeled as 0
                    if (levelArray[potentialMove.x][potentialMove.y] == 0) {
                        queue.add(potentialMove)
                        // Set this adjacent node as level + 1
                        levelArray[potentialMove.x][potentialMove.y] = level + 1
                    }
                }
            }
        }
        // Couldn't find solution
        if (levelArray[endLocation.x][endLocation.y] == 0)
            return LinkedList()

        val shortestPath = LinkedList<Point>()
        var pointToAdd = endLocation

        while (!pointToAdd.equals(startLocation)) {
            shortestPath.push(pointToAdd)
            val level = levelArray[pointToAdd.x][pointToAdd.y]
            val possibleMoves = ArrayList<Point>()
            // Move Right
            possibleMoves.add(Point(pointToAdd.x + 1, pointToAdd.y))
            // Down Move
            possibleMoves.add(Point(pointToAdd.x, pointToAdd.y - 1))
            // Move Left
            possibleMoves.add(Point(pointToAdd.x - 1, pointToAdd.y))
            // Move Up
            possibleMoves.add(Point(pointToAdd.x, pointToAdd.y + 1))

            for (potentialMove in possibleMoves) {
                if (isInMaze(potentialMove.x, potentialMove.y)) {
                    // The shortest level will always be level - 1, from this current node.
                    // Longer paths will have higher levels.
                    if (levelArray[potentialMove.x][potentialMove.y] == level - 1) {
                        pointToAdd = potentialMove
                        break
                    }
                }
            }
        }

        return shortestPath
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day24-input"))
        list.clear()

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }
}