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

    var programs = IntArray(16, { it -> 97+it })

    var firstLetter = 'd'
    var splits: List<String> = listOf()
    var posA = 0
    var posB = 0
    var a = 0
    var b = 0

    private fun getInput() {
        list = readFile("src/season_2017/input/day16-input")
    }

    data class Move(val type: Char = 'q', val spin: Int = -1, val posA: Int = -1, val posB: Int = -1, val a: Int = -1, val b: Int = -1)

    val cachedMoves: Array<Move> = Array(10000, { Move() })

    private inline fun doMove(move: Move) {
        when (move.type) {
            's' -> spin(move.spin)
            'x' -> exchange(move.posA, move.posB)
            'q' -> programs
            else -> partner(move.a, move.b)
        }
    }

    override fun part1() {
        println("PART 1")
        getInput()

        println(list)

        //runTests()

        val moves = list[0].split(",")

        Stopwatch.startTimer()

        // cache moves
        moves.forEachIndexed { index, move ->
            firstLetter = move[0]
            when (firstLetter) {
                's' -> {
                    cachedMoves[index] = Move(firstLetter, move.substring(1).toInt())
                }
                'x' -> {
                    // x13/14
                    splits = move.split("/")
                    posA = splits[0].substring(1).toInt()
                    posB = splits[1].toString().toInt()

                    cachedMoves[index] = Move(firstLetter, posA = posA, posB = posB)
                }
                'p' -> {
                    // pp/n
                    a = move[1].toInt()
                    b = move[3].toInt()

                    cachedMoves[index] = Move(firstLetter, a = a, b = b)
                }
            }
        }


        var prevTime = 0L
        var currTime = 0L

        val LIMIT = 1_000_000_000

        var cachedStrings = mutableListOf<String>()
        var currentSequence: String

        for (i in 0 until LIMIT) {
            if (i.rem(100000) == 0) {
                currTime = Stopwatch.getElapsedSeconds()
                println("----> $i moves in ${currTime - prevTime} seconds (total: $currTime secs)")
                prevTime = currTime
            }

            for (i in 0 until cachedMoves.size) {
               doMove(cachedMoves[i])
            }

            currentSequence = getAnswer()
            if (cachedStrings.contains(currentSequence)) {
                println(" BOOM!!! Repeated string $currentSequence at index ${cachedStrings.size}, which is our frequency!")

                var ind = LIMIT.rem(cachedStrings.size) - 1
                println("Sequence at $ind is ${cachedStrings[ind]}")
                break
            }
            else {
                cachedStrings.add(currentSequence)
            }

        }

        println("FINAL programs: ${getAnswer()} in ${Stopwatch.getElapsedSeconds()} seconds")
    }

    private fun getAnswer(): String {
        var answer = ""
        programs.forEach { letter -> answer += letter.toChar() }

        return answer
    }

    private fun runTests() {
        testSpin()
        testExchange()
        testPartner()
    }

    var tempSpinArray: IntArray = IntArray(16, { 0 } )
    private inline fun spin(moves: Int) {
        tempSpinArray = IntArray(16, { 0 } )
        val offset = programs.size - moves

        for (i in 0 until moves) {
            tempSpinArray[i] = programs[offset + i]
        }
        for (i in 0..offset-1) {
            tempSpinArray[programs.size - offset + i] = programs[i]
        }

        programs = tempSpinArray
    }

    private fun testSpin() {
//        val original = arrayOf('a'.toInt(), 'b'.toInt(), 'c'.toInt(), 'd'.toInt(), 'e'.toInt())
//        val spinned = spin(original, 1)
//        val target = arrayOf('e'.toInt(), 'a'.toInt(), 'b'.toInt(), 'c'.toInt(), 'd'.toInt())
//        assert(spinned.contentEquals(target))
//
//        assert(spin(original, 0).contentEquals(original))
//        assert(spin(original, 5).contentEquals(original))
    }

    private inline fun exchange(posA: Int, posB: Int) {
        a = programs[posA]
        b = programs[posB]

        programs[posA] = b
        programs[posB] = a

    }

    private fun testExchange() {
//        assert(exchange(arrayOf(1,2,3,4,5), 0, 4).contentEquals(arrayOf(5,2,3,4,1)))
//        assert(exchange(arrayOf(1,2,3,4,5), 3, 4).contentEquals(arrayOf(1,2,3,5,4)))
    }

    private inline fun partner(progA: Int, progB: Int) {
        posA = programs.indexOf(progA)
        posB = programs.indexOf(progB)

        programs[posA] = progB
        programs[posB] = progA
    }

    private fun testPartner() {
//        assert(partner(arrayOf(1,2,3,4,5), 1, 5).contentEquals(arrayOf(5,2,3,4,1)))
//        assert(partner(arrayOf(100, 200), 100, 200).contentEquals(arrayOf(200, 100)))
    }

    override fun part2() {
        println("PART 2")
    }
}