package season_2017

import AbstractDay
import Stopwatch

/**
 * Created by melquiadess on 17/12/2017.
 * Part 1:
 *
 * Part 2:
 */
class Day17 : AbstractDay("Day17") {

    override fun part1() {
        println("PART 1")

        val FORWARD_MOVES = 363
        val TOTAL_REPEATS = 2017
        var currentPosition = 0
        var currentNumber = 1

        var buffer: MutableList<Int> = mutableListOf(0)

        (0..TOTAL_REPEATS).forEach { _ ->
            var pos = currentPosition + FORWARD_MOVES
            while (pos >= buffer.size) {
                pos -= buffer.size
            }

            currentPosition = pos + 1
            buffer.add(currentPosition, currentNumber++)

            println(" ==> $buffer")
        }

        println("Answer: ${buffer[buffer.indexOf(2017) + 1]}")
    }

    override fun part2() {
        println("PART 2")

        val FORWARD_MOVES = 363
        val TOTAL_REPEATS = 2017
        var currentPosition = 0
        var currentNumber = 1

        var buffer: MutableList<Int> = mutableListOf(0)

        while (true) {
            var pos = currentPosition + FORWARD_MOVES
            while (pos >= buffer.size) {
                pos -= buffer.size
            }

            currentPosition = pos + 1
            buffer.add(currentPosition, currentNumber++)

//            println(" ==> $buffer")
            if (buffer.size == 50000000) break
            if (buffer.size % 100000 == 0) {
                println("buffer: ${buffer.size}")
            }
        }

        println("Answer2: ${buffer[buffer.indexOf(0) + 1]}")
    }
}