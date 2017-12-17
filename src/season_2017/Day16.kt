package season_2017

import AbstractDay
import Stopwatch

/**
 * Created by melquiadess on 16/12/2017.
 * Part 1:
 *  aklbdihfncopemgj - wrong
 *
 * Part 2:
 */
class Day16 : AbstractDay("Day16") {
    var list: MutableList<String> = mutableListOf()

    var programs = "abcdefghijklmnop"

    private fun getInput() {
        list = readFile("src/season_2017/input/day16-input")
    }

    override fun part1() {
        println("PART 1")
        getInput()

        println(list)

        runTests()

        val moves = list[0].split(",")

        val timer = Stopwatch()
        timer.startTimer()

        var firstLetter = 'd'
        var splits: List<String>
        var posA = 0
        var posB = 0
        var a: String
        var b: String

//        for (i in 0..1000000000) {
//            if (i.rem(1000) == 0) {
//                println("$i moves in ${timer.getElapsedSeconds()} seconds")
//            }
            moves.forEachIndexed { index, move ->
                firstLetter = move[0]
                when (firstLetter) {
                    's' -> programs = spin(programs, move.substring(1).toInt())
                    'x' -> {
                        // x13/14
                        splits = move.split("/")
                        posA = splits[0].substring(1).toInt()
                        posB = splits[1].toString().toInt()
                        programs = exchange(programs, posA, posB)
                    }
                    'p' -> {
                        // pp/n
                        splits = move.split("/")
                        a = splits[0].substring(1)
                        b = splits[1]
                        programs = partner(programs, a.single(), b.single())
                    }
                }
            }
//        }

        println("FINAL programs: $programs in ${timer.getElapsedSeconds()} seconds")
    }

    private fun runTests() {
        testSpin()
        testExchange()
        testPartner()
    }

    private fun spin(programs: String, moves: Int): String {
        val offset = programs.length - moves
        val left = programs.substring(0 until offset)
        val right = programs.substring(offset)

        return right + left
    }

    private fun testSpin() {
        assert(spin("abcde", 3) == "cdeab")
        assert(spin("abcde", 1) == "eabcd")
        assert(spin("abcde", 0) == "abcde")
        assert(spin("abcde", 5) == "abcde")
        assert(spin("abcde", 5) != "abcdf")
    }

    private fun exchange(programs: String, posA: Int, posB: Int): String {
        var array = programs.toCharArray()
        val a = array[posA]
        val b = array[posB]

        array[posA] = b
        array[posB] = a

        return array.joinToString("")
    }

    private fun testExchange() {
        assert(exchange("eabcd", 3, 4) == "eabdc")
        assert(exchange("abcde", 0, 4) == "ebcda")
        assert(exchange("abcde", 3, 1) == "adcbe")
    }

    private fun partner(programs: String, progA: Char, progB: Char): String {
        var array = programs.toCharArray()
        val posA = programs.indexOf(progA)
        val posB = programs.indexOf(progB)

        array[posA] = progB
        array[posB] = progA

        return array.joinToString("")
    }

    private fun testPartner() {
        assert(partner("eabdc", 'e', 'b') == "baedc")
    }

    override fun part2() {
        println("PART 2")
    }
}