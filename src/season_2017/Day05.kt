package season_2017

import AbstractDay
/**
 * Created by melquiadess on 05/12/2017.
 */
class Day05 : AbstractDay("Day05") {
    var listString: MutableList<String> = mutableListOf()

    var list: MutableList<Int> = mutableListOf()

    override fun part1() {
        println("PART 1")

        listString = readFile("src/season_2017/input/day05-input")
        list = listString.map { it -> it.toInt() }.toMutableList()

        println("Answer: ${howManyMovesToExit(list)}")

        println(list)
    }

    fun howManyMovesToExit(list: MutableList<Int>): Int {
        var moves = 0
        var index = 0

        var currentValue = list[0]
        var previousIndex = 0
        while(index < list.size) {
            previousIndex = index

            // move
            index += list[index]

            // increment value at last index
            list[previousIndex] = list[previousIndex] + 1

            moves++
        }

        return moves
    }

    fun howManyMovesToExitExtra(list: MutableList<Int>): Int {
        var moves = 0
        var index = 0

        var previousIndex = 0
        var increaseValue = 1
        var offset = 0

        while(index < list.size) {
            previousIndex = index

            // move
            offset = list[index]
            increaseValue = if (offset >= 3) -1 else 1

            index += offset

            // increment value at last index
            list[previousIndex] = list[previousIndex] + increaseValue

            moves++
        }

        return moves
    }

    override fun part2() {
        println("PART 2")

        listString = readFile("src/season_2017/input/day05-input")
        list = listString.map { it -> it.toInt() }.toMutableList()

        println(list)
        println("Answer: ${howManyMovesToExitExtra(list)}")
    }
}