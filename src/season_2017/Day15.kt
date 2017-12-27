package season_2017

import AbstractDay
/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 *
 * Part 2:
 */
class Day15 : AbstractDay("Day15") {
    val GENERATOR_A: Long = 116
    val GENERATOR_B: Long = 299

    val GENERATOR_A_FACTOR = 16807
    val GENERATOR_B_FACTOR = 48271

    val DIVIDER = 2147483647

    private fun generateNext(prev: Long, factor: Int): Long {
        return prev * factor % DIVIDER
    }


    override fun part1() {
        println("PART 1")

        var prevA = GENERATOR_A
        var prevB = GENERATOR_B

        var count = 0

        for (i in 0..40_000_000) {
            prevA = generateNext(prevA, GENERATOR_A_FACTOR)
            prevB = generateNext(prevB, GENERATOR_B_FACTOR)

            if (isMatching(getBinary(prevA), getBinary(prevB))) count++
        }

        println("Count: $count")
    }

    private fun getBinary(number: Long): String {
        var numberAsString = Integer.toBinaryString(number.toInt())
        if (numberAsString.length < 32) {
            for (i in 1..32-numberAsString.length) {
                numberAsString = "0" + numberAsString
            }
        }

        return numberAsString
    }

    private fun isMatching(left: String, right: String): Boolean {
        return left.substring(16..31) == right.substring(16..31)
    }

    override fun part2() {
        println("PART 2")

        var prevA = GENERATOR_A
        var prevB = GENERATOR_B

        var listA = arrayListOf<String>()
        var listB = arrayListOf<String>()

        while (true) {
            prevA = generateNext(prevA, GENERATOR_A_FACTOR)
            if (prevA.rem(4) == 0L) {
                listA.add(getBinary(prevA))
            }

            if (listA.size == 5_000_000) break
        }

        while (true) {
            prevB = generateNext(prevB, GENERATOR_B_FACTOR)
            if (prevB.rem(8) == 0L) {
                listB.add(getBinary(prevB))
            }

            if (listB.size == 5_000_000) break
        }

        val count = (0 until listA.size).count { isMatching(listA[it], listB[it]) }

        println("Count: $count")
    }
}