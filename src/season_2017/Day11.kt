package season_2017

import AbstractDay
import java.util.*

/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 *  861 - too high
 *
 * Part 2:
 *  892 - too low
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

        var maxDistance = Int.MIN_VALUE

        var mx=0
        var my=0
        moves.forEach { move ->
            println("Before moving $move: $cp")
            cp = moveOnGrid(cp, move)
            println("After moving $move : $cp")
            println()

            // part 2 - find the biggest x,y values for a temp position
            if (Math.abs(cp.first) > Math.abs(mx)) mx = cp.first
            if (Math.abs(cp.second) > Math.abs(my)) my = cp.second
        }

        println("Finished at position: $cp")

        // to get to 176 on y, we need to move 176*2 times (starting at 0,0)
        // so now we're at -352, 176, and now we move to -773 from -352,
        // so first, me move to -772 from -352 in 420 steps, and 1 more to -773
        // so 176*2 + 420 + 1 = 352 + 420 + 1 = 773
        var steps = cp.second * 2 + Math.abs(cp.first + cp.second*2)

        println("Steps needed: $steps")

        println("mx=$mx my=$my") // -1560, 661
        // so max distance is: 1322 + 1560-1322
        maxDistance = my*2 + Math.abs(mx + my*2)

        println("Max distance: $maxDistance")
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
}