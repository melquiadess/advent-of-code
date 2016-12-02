package season_2015

import BaseDay
/**
 * Created by gregk on 13/11/2016.
 */
class Day11 : BaseDay {
    val INPUT = "cqjxjnds"

    val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

    override fun run() {
        println("Day11")

        val first = getNextPassword(INPUT)
        val second = getNextPassword(first)

        println("First: ${first} Second: ${second}")

        println()
    }

    fun getNextPassword(str: String): String {
        var next = incrementPassword(str, str.length-1)

        while (!isPasswordSecure(next)) {
            next = incrementPassword(next, next.length-1)
        }

        return next
    }

    fun incrementPassword(input: String, pos: Int): String {
        var str = input
        var index = ALPHABET.indexOf(str[pos])

        when (index) {
            // last letter
            ALPHABET.length-1 -> {
                var left = str.substring(0, pos)
                var right = str.substring(pos+1)

                str = left + "a" + right
                str = incrementPassword(str, pos-1)
            }
            else -> {
                var left = str.substring(0, pos)
                var right = str.substring(pos+1)

                var incremented = ALPHABET[index+1]
                str = left + incremented + right
            }
        }

        return str
    }

    fun isPasswordSecure(str: String) = hasStraightTriple(str) && noForbiddenLetters(str) && hasTwoDoubles(str)

    fun hasStraightTriple(str: String) : Boolean {
        var i = 0
        while (i < str.length-2) {
            var triple = "" + str[i] + str[i+1] + str[i+2]

            if (triple in ALPHABET) return true
            i++
        }

        return false
    }

    fun noForbiddenLetters(str: String) = 'i' !in str && 'o' !in str && 'l' !in str

    fun hasTwoDoubles(str: String) : Boolean {
        var count = 0

        for (c in ALPHABET) {
            when ("" + c + c) {
                in str -> count++
            }
        }

        return count >= 2
    }
}