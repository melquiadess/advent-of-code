package season_2017

import AbstractDay
import sun.security.util.Length

/**
 * Created by melquiadess on 10/12/2017.
 * Part 1:
 *
 * Part 2:
 */
class Day10 : AbstractDay("Day10") {
    var lengths: MutableList<Int> = mutableListOf(199,0,255,136,174,254,227,16,51,85,1,2,22,17,7,192)
//    var lengths: MutableList<Int> = mutableListOf(3, 4, 1, 5)
    val list = IntArray(256, { it })
//    var list = IntArray(5, { it })

    var currentPosition = 0
    var skipSize = 0

    override fun part1() {
        println("PART 1")

        println(lengths)

        lengths.forEach { length ->
            print("Length $length array: ")
            printArray(list)
            println("pos: $currentPosition")
            // Reverse the order of that length of elements in the list, starting with the element at the current position.
            reverseList(length)

            // Move the current position forward by that length plus the skip size.
            currentPosition += (length + skipSize)
            println("pos: $currentPosition was increased by ${length + skipSize}")

            if (currentPosition >= list.size) {
                println("Oops, too long. Wrapping...")
                while (currentPosition >= list.size) {
                    currentPosition -= list.size
                }
            }
            println("pos: $currentPosition")

            // Increase the skip size by one.
            skipSize++

        }

        println("Answer: ${list[0] * list[1]}")
    }

    fun reverseList(length: Int) {
        if (length == 1 || length == 0 || length > list.size) return

        var reversed = IntArray(length, { it })

        var posEnd = currentPosition + length
        while (posEnd >= list.size) {
            posEnd -= list.size
        }
        var index = 0

        if (posEnd > currentPosition) {
            // normal slice of array
            for (i in currentPosition until posEnd) {
                reversed[index++] = list[i]
            }
            reversed.reverse()

            index = 0
            for (i in currentPosition until posEnd) {
                list[i] = reversed[index++]
            }

            print("REVERSED (X): ")
            printArray(list)
        } else {
            // we need to get some elements from the beginning
            for (i in currentPosition until list.size) {
                reversed[index++] = list[i]
            }

            for (i in 0 until posEnd) {
                reversed[index++] = list[i]
            }

            printArray(reversed)
            reversed.reverse()
            printArray(reversed)

            index = 0
            for (i in currentPosition until list.size) {
                list[i] = reversed[index++]
            }

            for (i in 0 until posEnd) {
                list[i] = reversed[index++]
            }

            print("REVERSED: ")
            printArray(list)
        }
    }

    fun printArray(array: IntArray) {
        array.forEach { print("$it, ") }
        println()
    }

    override fun part2() {
        println("PART 2")
    }
}