package season_2015

import BaseDay
import Stopwatch

/**
 * Created by gregk on 11/11/2016.
 */
class Day10 : BaseDay {
    val INPUT = "1113222113"

    override fun run() {
        println("Day10")

        Stopwatch.startTimer()

        var input = INPUT
        for (i in 1..50) {
            input = printMonoSequences(input)

            when (i) {
                40 -> { print("Length: ${input.length} Done in ${Stopwatch.getElapsedSeconds()} seconds") }
                50 -> { print("Length: ${input.length} Done in ${Stopwatch.getElapsedSeconds()} seconds") }
            }
        }
    }

    fun printMonoSequences(str: String) : String {
        var input = str
        var newInput = ""

        var pattern = getMonoPattern(input)
        while (pattern.isNotEmpty()) {
            newInput += "" + pattern.length + pattern[0]

            input = input.substring(pattern.length)

            pattern = getMonoPattern(input)
        }

        return newInput
    }

    fun getMonoPattern(str: String) : String {
        if (str.length == 0) return ""
        var pattern = "" + str[0]

        var i=0
        while (i < str.length-1) {
            if (str[i+1] in pattern) {
                pattern += str[i]
            }
            else break
            i++
        }

        return pattern
    }
}