package season_2016

import BaseDay

/**
 * Created by melquiadess on 01/12/2016.
 * Part 1:
 *  35: too low
 *  307: correct (some moves have 3-digit, eg. R188, but I was taking only first digit
 * Part 2:
 *  165
 */
class Day01 : BaseDay {
    enum class Direction { NORTH, EAST, SOUTH, WEST }
    data class Point(var x: Int, var y: Int, var dir: Direction)

    var visitedIntersections: MutableList<String> = mutableListOf()

    var currentDirection: Direction = Direction.NORTH
    var currentPos = "0:0"

    override fun run() {
        println("Day01")

        processInput(INPUT)

        var distance = getDistanceFromOrigin(currentPos)
        println("Distance from origin is $distance")

        for (pos in visitedIntersections) {
            var visitedTwice = visitedIntersections.filter { it == pos }
            println("$pos ->" + visitedTwice)

            if (visitedTwice.size == 2) {
                println("Found place: $pos")
                println("Distance: ${getDistanceFromOrigin(pos)}")
                break
            }
        }

        println(visitedIntersections)

        println()
    }

    /**
     * 0:0 -> R2 = 2:0 E
     * 2:0 -> R2 = 2:-2 S
     * 2:-2 -> R2 = 0:-2 W
     * 0:-2 -> R2 = 0:0 N
     */

    fun processInput(str: String) {
        var moves = str.split(", ")

        var i=0
        while (i < moves.size) {
            var p = getCurrentPosition(currentPos, currentDirection)
            print("$i -> Current position: $currentPos heading $currentDirection moving ${moves[i]} to ")

            var dist = moves[i].substring(1).toInt()

            when (moves[i].substring(0, 1)) {

                "R" -> {
                    when (currentDirection) {
                        Direction.NORTH -> {
                            currentDirection = Direction.EAST

                            //part 2: add all interim places
                            for (i in 1..dist) {
                                var place = (p.x+i).toString() + ":" + p.y.toString()
                                visitedIntersections.add(place)
                            }

                            p.x += dist
                        }

                        Direction.SOUTH -> {
                            currentDirection = Direction.WEST

                            //part 2: add all interim places
                            for (i in 1..dist) {
                                var place = (p.x-i).toString() + ":" + p.y.toString()
                                visitedIntersections.add(place)
                            }

                            p.x -= dist
                        }

                        Direction.WEST -> {
                            currentDirection = Direction.NORTH

                            //part 2: add all interim places
                            for (i in 1..dist) {
                                var place = (p.x).toString() + ":" + (p.y+i).toString()
                                visitedIntersections.add(place)
                            }

                            p.y += dist
                        }

                        Direction.EAST -> {
                            currentDirection = Direction.SOUTH

                            //part 2: add all interim places
                            for (i in 1..dist) {
                                var place = (p.x).toString() + ":" + (p.y-i).toString()
                                visitedIntersections.add(place)
                            }

                            p.y -= dist
                        }
                    }

                    currentPos = p.x.toString() + ":" + p.y
                    println(currentPos)
                }

                "L" -> {

                    when (currentDirection) {
                        Direction.NORTH -> {
                            currentDirection = Direction.WEST

                            //part 2: add all interim places
                            for (i in 1..dist) {
                                var place = (p.x-i).toString() + ":" + p.y.toString()
                                visitedIntersections.add(place)
                            }

                            p.x -= dist
                        }

                        Direction.SOUTH -> {
                            currentDirection = Direction.EAST

                            //part 2: add all interim places
                            for (i in 1..dist) {
                                var place = (p.x+i).toString() + ":" + p.y.toString()
                                visitedIntersections.add(place)
                            }

                            p.x += dist
                        }

                        Direction.WEST -> {
                            currentDirection = Direction.SOUTH

                            //part 2: add all interim places
                            for (i in 1..dist) {
                                var place = (p.x).toString() + ":" + (p.y-i).toString()
                                visitedIntersections.add(place)
                            }

                            p.y -= dist
                        }

                        Direction.EAST -> {
                            currentDirection = Direction.NORTH

                            //part 2: add all interim places
                            for (i in 1..dist) {
                                var place = (p.x).toString() + ":" + (p.y+i).toString()
                                visitedIntersections.add(place)
                            }

                            p.y += dist
                        }
                    }

                    currentPos = p.x.toString() + ":" + p.y
                    println(currentPos)
                }
            }

            i++
        }
    }

    fun getCurrentPosition(pos: String, dir: Direction): Point {
        var split = pos.split(":")

        var x: Int = split[0].toInt()
        var y: Int = split[1].toInt()

        return Point(x, y, dir)
    }

    fun getDistanceFromOrigin(pos: String): Int {
        var currentPos = getCurrentPosition(pos, currentDirection)

        var distance: Int = Math.abs(currentPos.x) + Math.abs(currentPos.y)

        return distance
    }

    var INPUT = "R1, R3, L2, L5, L2, L1, R3, L4, R2, L2, L4, R2, L1, R1, L2, R3, L1, L4, R2, L5, R3, R4, L1, R2, L1, R3, L4, R5, L4, L5, R5, L3, R2, L3, L3, R1, R3, L4, R2, R5, L4, R1, L1, L1, R5, L2, R1, L2, R188, L5, L3, R5, R1, L2, L4, R3, R5, L3, R3, R45, L4, R4, R72, R2, R3, L1, R1, L1, L1, R192, L1, L1, L1, L4, R1, L2, L5, L3, R5, L3, R3, L4, L3, R1, R4, L2, R2, R3, L5, R3, L1, R1, R4, L2, L3, R1, R3, L4, L3, L4, L2, L2, R1, R3, L5, L1, R4, R2, L4, L1, R3, R3, R1, L5, L2, R4, R4, R2, R1, R5, R5, L4, L1, R5, R3, R4, R5, R3, L1, L2, L4, R1, R4, R5, L2, L3, R4, L4, R2, L2, L4, L2, R5, R1, R4, R3, R5, L4, L4, L5, L5, R3, R4, L1, L3, R2, L2, R1, L3, L5, R5, R5, R3, L4, L2, R4, R5, R1, R4, L3"
    var TEST_INPUT = "R8, R4, R4, R8"
}