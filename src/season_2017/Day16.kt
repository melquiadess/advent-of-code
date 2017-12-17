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

    var programs = arrayOf('a'.toInt(), 'b'.toInt(), 'c'.toInt(), 'd'.toInt(), 'e'.toInt(), 'f'.toInt(), 'g'.toInt(), 'h'.toInt(), 'i'.toInt(), 'j'.toInt(), 'k'.toInt(), 'l'.toInt(), 'm'.toInt(), 'n'.toInt(), 'o'.toInt(), 'p'.toInt())
//    var programs = arrayOf('a'.toInt(), 'b'.toInt(), 'c'.toInt(), 'd'.toInt(), 'e'.toInt())

    private fun getInput() {
        list = readFile("src/season_2017/input/day16-input")
    }

    data class Move(val type: Char, val spin: Int = -1, val posA: Int = -1, val posB: Int = -1, val a: Char = 'z', val b: Char = 'z')

    val moveList: MutableList<Move> = mutableListOf()

    private fun doMove(programs: Array<Int>, move: Move): Array<Int> {
        return when (move.type) {
            's' -> spin(programs, move.spin)
            'x' -> exchange(programs, move.posA, move.posB)
            else -> partner(programs, move.a.toInt(), move.b.toInt())
        }
    }

    override fun part1() {
        println("PART 1")
        getInput()

        println(list)

        //runTests()

        val moves = list[0].split(",")

        val timer = Stopwatch()
        timer.startTimer()

        var firstLetter = 'd'
        var splits: List<String>
        var posA = 0
        var posB = 0
        var a: Char
        var b: Char

        // cache moves
        moves.forEachIndexed { index, move ->
            firstLetter = move[0]
            when (firstLetter) {
                's' -> {
                    moveList.add(Move(firstLetter, move.substring(1).toInt()))
                }
                'x' -> {
                    // x13/14
                    splits = move.split("/")
                    posA = splits[0].substring(1).toInt()
                    posB = splits[1].toString().toInt()

                    moveList.add(Move(firstLetter, posA = posA, posB = posB))
                }
                'p' -> {
                    // pp/n
                    a = move[1]
                    b = move[3]

                    moveList.add(Move(firstLetter, a = a, b = b))
                }
            }
        }


        for (i in 0..1000000000) {
            if (i.rem(100000) == 0) {
                println("$i moves in ${timer.getElapsedSeconds()} seconds")
            }
            moveList.forEach { move ->
                programs = doMove(programs, move)
            }
        }

        var answer = ""
        programs.forEach { letter -> answer += letter.toChar() }

        println("FINAL programs: ${answer} in ${timer.getElapsedSeconds()} seconds")
    }

    private fun runTests() {
        testSpin()
        testExchange()
        testPartner()
    }

    var tempSpinArray: Array<Int> = Array(16, { 0 } )
    private fun spin(programs: Array<Int>, moves: Int): Array<Int> {
        tempSpinArray = Array(16, { 0 } )
        val offset = programs.size - moves

        for (i in 0 until moves) {
            tempSpinArray[i] = programs[offset + i]
        }
        for (i in 0 until offset) {
            tempSpinArray[programs.size - offset + i] = programs[i]
        }

        return tempSpinArray
    }

    private fun testSpin() {
        val original = arrayOf('a'.toInt(), 'b'.toInt(), 'c'.toInt(), 'd'.toInt(), 'e'.toInt())
        val spinned = spin(original, 1)
        val target = arrayOf('e'.toInt(), 'a'.toInt(), 'b'.toInt(), 'c'.toInt(), 'd'.toInt())
        assert(spinned.contentEquals(target))

        assert(spin(original, 0).contentEquals(original))
        assert(spin(original, 5).contentEquals(original))
    }

    private fun exchange(programs: Array<Int>, posA: Int, posB: Int): Array<Int> {
        val a = programs[posA]
        val b = programs[posB]

        programs[posA] = b
        programs[posB] = a

        return programs
    }

    private fun testExchange() {
        assert(exchange(arrayOf(1,2,3,4,5), 0, 4).contentEquals(arrayOf(5,2,3,4,1)))
        assert(exchange(arrayOf(1,2,3,4,5), 3, 4).contentEquals(arrayOf(1,2,3,5,4)))
    }

    private fun partner(programs: Array<Int>, progA: Int, progB: Int): Array<Int> {
        val posA = programs.indexOf(progA)
        val posB = programs.indexOf(progB)

        programs[posA] = progB
        programs[posB] = progA

        return programs
    }

    private fun testPartner() {
        assert(partner(arrayOf(1,2,3,4,5), 1, 5).contentEquals(arrayOf(5,2,3,4,1)))
        assert(partner(arrayOf(100, 200), 100, 200).contentEquals(arrayOf(200, 100)))
    }

    override fun part2() {
        println("PART 2")
    }
}