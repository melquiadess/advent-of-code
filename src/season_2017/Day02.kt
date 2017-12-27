package season_2017

import AbstractDay

/**
 * Created by melquiadess on 02/12/2017.
 */
class Day02 : AbstractDay("Day02") {
    val INPUT = ""

    var list: MutableList<String> = mutableListOf()

    override fun part1() {
        println("PART 1")

        list = readFile("src/season_2017/input/day02-input")

        println("Checksum: ${getChecksum(list)}")
    }

    override fun part2() {
        println("PART 2")

        list = readFile("src/season_2017/input/day02-input")

        println("Checksum: ${getChecksumExtra(list)}")
    }


    /*****************************************************************
     * PART 1
     *****************************************************************/

    fun getChecksum(list: MutableList<String>): Int {
        var sum = 0
        for (row in list) {
            sum += getDifference(row)
        }

        return sum
    }

    fun getDifference(row: String): Int {
        val splitList = row.split("\t").map { it -> it.toInt() }.sorted()

        return splitList.last() - splitList.first()
    }

    /*****************************************************************
     * PART 2
     *****************************************************************/

    fun getChecksumExtra(list: MutableList<String>): Int {
        var sum = 0

        for (row in list) {
            var pair = getTwoEvenlyDivisibleNumbers(row)
            sum += pair.first / pair.second
        }

        return sum
    }

    fun getTwoEvenlyDivisibleNumbers(row: String): Pair<Int, Int> {
        val splitList = row.split("\t").map { it -> it.toInt() }.sorted()

        var divided: Int
        for (left in splitList) {
            for (right in splitList.reversed()) {
                if (left == right) continue
                if (left > right ) {
                    divided = left % right
                    if (divided == 0) return Pair(left, right)
                } else {
                    divided = right % left
                    if (divided == 0) return Pair(right, left)
                }
            }
        }

        return Pair(0,1)
    }
}