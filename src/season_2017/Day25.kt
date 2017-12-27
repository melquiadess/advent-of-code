package season_2017

import AbstractDay
import com.marcinmoskala.math.powerset

/**
 * Created by melquiadess on 25/12/2017.
 * Part 1:
 *
 * Part 2:
 */
class Day25 : AbstractDay("Day25") {
    var list: MutableList<String> = mutableListOf()

    data class SubStep(val currentValue: Int, val writeValue: Int, val move: Int, val nextState: String)
    data class State(val name: String, val stepForZero: SubStep, val stepForOne: SubStep)

    var currentStateName = "A"
    var checksumSteps = 12629077
    var currentIndex = 4999

    var tape = Array(10000, { 0 })
    var stateList = mutableListOf<State>()

    private fun getInput() {
        list = readFile("src/season_2017/input/day25-input-test")

//        stateList.add(
//                State("A",
//                        SubStep(0, 1, 1, "B"),
//                        SubStep(1, 0, -1, "B")
//                )
//        )
//        stateList.add(
//                State("B",
//                        SubStep(0, 1, -1, "A"),
//                        SubStep(1, 1, 1, "A")
//                )
//        )

        stateList.add(
                State("A",
                        SubStep(0, 1, 1, "B"),
                        SubStep(1, 0, -1, "B")
                )
        )

        stateList.add(
                State("B",
                        SubStep(0, 0, 1, "C"),
                        SubStep(1, 1, -1, "B")
                )
        )

        stateList.add(
                State("C",
                        SubStep(0, 1, 1, "D"),
                        SubStep(1, 0, -1, "A")
                )
        )

        stateList.add(
                State("D",
                        SubStep(0, 1, -1, "E"),
                        SubStep(1, 1, -1, "F")
                )
        )

        stateList.add(
                State("E",
                        SubStep(0, 1, -1, "A"),
                        SubStep(1, 0, -1, "D")
                )
        )

        stateList.add(
                State("F",
                        SubStep(0, 1, 1, "A"),
                        SubStep(1, 1, -1, "E")
                )
        )

    }

    override fun part1() {
        println("PART 1")
        getInput()

        (0 until checksumSteps).forEach {
            var state = stateList.find { it.name == currentStateName }
            var currentValue = tape[currentIndex]

            when (currentValue) {
                0 -> {
                    tape[currentIndex] = state!!.stepForZero.writeValue
                    currentIndex += state!!.stepForZero.move
                    currentStateName = state!!.stepForZero.nextState
                }
                1 -> {
                    tape[currentIndex] = state!!.stepForOne.writeValue
                    currentIndex += state!!.stepForOne.move
                    currentStateName = state!!.stepForOne.nextState
                }
            }
        }

        tape.forEach { i ->
            print("$i ")
        }

        var checksum = tape.count { it==1 }
        println("\nChecksum = $checksum")
    }

    override fun part2() {
        println()
        println("PART 2")

    }
}