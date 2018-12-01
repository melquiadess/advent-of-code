package season_2017

import AbstractDay
import com.sun.rowset.internal.Row

/**
 * Created by melquiadess on 03/12/2017.
 *
 * Part 1:
 * 183 - too low
 */
class Day03 : AbstractDay("Day03") {
    var square1: Pair<Int, Int> = Pair(0,0)
    var squareX: Pair<Int, Int> = Pair(0,0)

    val TARGET_SQUARE = 368078

    override fun part1() {
        println(">>>>>>>>>>>>>> PART 1 <<<<<<<<<<<<")

        testManhattanDistance()
        calculateManhattan(TARGET_SQUARE)
    }

    private fun calculateManhattan(target: Int): Int {
        val TARGET_SQUARE = target
        println("Target square: $TARGET_SQUARE")

        // 1. Get top-right corner
        var topRightCorner = getTopRightCornerPositionAndValue(TARGET_SQUARE)
        println("top right corner: $topRightCorner")

        // 2. Check if target square is:

        var BOARD_SIZE = topRightCorner.first + 1
        val topRightCornerValue = topRightCorner.third

        var diff = topRightCornerValue - TARGET_SQUARE
        println("Diff: $diff")

        var iterations = 0
        var subtractValue = 1
        while (diff > BOARD_SIZE - subtractValue) {
            println("$diff > $BOARD_SIZE-$subtractValue")

            println("iterations $iterations subtract $subtractValue")

            diff -= (BOARD_SIZE - subtractValue)
            iterations++

            if (iterations % 2 == 0) subtractValue++
        }

        // diff now has coordinate depending on iterations value
        // 0
        println("Diff left: $diff")

        var WIDTH = 0
        var HEIGHT = 0

        squareX = when (iterations) {
        // right
            0 -> {
                println("RIGHT")
                WIDTH = BOARD_SIZE
                HEIGHT = WIDTH - 1
                Pair(WIDTH-1, diff-1)
            }
        // bottom
            1 -> {
                println("BOTTOM")
                WIDTH = BOARD_SIZE - 1
                HEIGHT = WIDTH - 1
                Pair(WIDTH - diff, HEIGHT)
            }
        // left
            2 -> {
                println("LEFT")
                WIDTH = BOARD_SIZE - 1
                HEIGHT = WIDTH - 1
                Pair(0, BOARD_SIZE - 2 - diff)
            }
        // top
            else -> {
                WIDTH = BOARD_SIZE - 1
                HEIGHT = WIDTH - 2
                println("TOP")
                Pair(diff, 0)
            }
        }
        square1 = Pair((BOARD_SIZE-1) / 2, (BOARD_SIZE-1) / 2)

        println("SquareX: $squareX")
        println("Square1: $square1")

        val result = getManhattanDistance(squareX, square1)

        println("Answer: $result")

        return result
    }

    fun getManhattanDistance(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        return Math.abs(start.first - end.first) + Math.abs(start.second - end.second)
    }

    /**
     * 1 3 13 31 57 91 133 ----> 2 10 18 26 34 42
     */
    fun getTopRightCornerPositionAndValue(targetValue: Int): Triple<Int, Int, Int> {
        var topRightCornerValue = 3
        var size = 2
        var currentAdditionValue = 2

        while (true) {
            if (topRightCornerValue < targetValue) {
                currentAdditionValue += 8
                topRightCornerValue += currentAdditionValue
                size += 2

            } else break
        }

        println("Size: $size")
        println("Current Addition Value: $currentAdditionValue")

        return Triple(size-1, 0, topRightCornerValue)
    }

    fun testManhattanDistance() {
        assert(3 == getManhattanDistance(Pair(2,2), Pair(4,1)))
        assert(2 == getManhattanDistance(Pair(2,2), Pair(2,4)))
        assert(2 == getManhattanDistance(Pair(2,2), Pair(2,4)))

        // bottom
        assert(2 == calculateManhattan(7))
        assert(1 == calculateManhattan(8))
        assert(2 == calculateManhattan(9))

        assert(4 == calculateManhattan(21))
        assert(3 == calculateManhattan(22))
        assert(2 == calculateManhattan(23))
        assert(3 == calculateManhattan(24))
        assert(4 == calculateManhattan(25))
        assert(5 == calculateManhattan(44))
        assert(6 == calculateManhattan(115))

        // right
        assert(3 == calculateManhattan(10))
        assert(2 == calculateManhattan(11))
        assert(3 == calculateManhattan(12))
        assert(4 == calculateManhattan(27))
        assert(3 == calculateManhattan(28))
        assert(4 == calculateManhattan(29))

        // top
        assert(3 == calculateManhattan(14))
        assert(2 == calculateManhattan(15))
        assert(3 == calculateManhattan(16))
        assert(8 == calculateManhattan(137))
        assert(7 == calculateManhattan(138))
        assert(6 == calculateManhattan(139))

        // left
        assert(3 == calculateManhattan(18))
        assert(2 == calculateManhattan(19))
        assert(3 == calculateManhattan(20))

        assert(4 == calculateManhattan(39))
        assert(3 == calculateManhattan(40))
        assert(4 == calculateManhattan(41))

        assert(10 == calculateManhattan(101))

    }

    val squares: MutableMap<Int, MutableList<Int>> = mutableMapOf()

    override fun part2() {
        println(">>>>>>>>>>>>>> PART 2 <<<<<<<<<<<<")

        // given position of square, what are the adjacent squares and their sum?

        // build squares
        squares.put(1, mutableListOf(1))
        squares.put(2, mutableListOf(1, 2, 4, 5, 10, 11, 23, 25))
        squares.put(3, mutableListOf(
                26, 54, 57, 59,
                122, 133, 142, 147,
                304, 330, 351, 362,
                747, 806, 880, 931))

//        println("Level 1: ${getSquareNumbers(1)}")
//        println("Level 2: ${getSquareNumbers(2)}")
//        println("Level 3: ${getSquareNumbers(3)}")
        println("Level 4: ${getSquareNumbers(4)}")
        println("Level 5: ${getSquareNumbers(5)}")
        println("Level 6: ${getSquareNumbers(6)}")

        val level5 = getSquareNumbers(5)
        val solution = level5.first { it > TARGET_SQUARE }

        println("Solution: $solution")

    }

    private fun getSquareCountForLevel(level: Int) = if (level == 1) 1 else 8 * (level-1)

    private fun getSquareNumbers(level: Int): List<Int> {
        if (squares.containsKey(level)) { return squares.getValue(level) }

        val previousSquare = squares.getValue(level-1)

        val squareNumbers = mutableListOf<Int>()

        var nextNumber = 0
        var current = 0
        var previous = 0

        val squareCount = getSquareCountForLevel(level)

        val COLUMN_LIMIT = squareCount/4 - 1
        val ROW_LIMIT = squareCount/4 + 1

        /**
         *  6591 6444  6155  5733  5336 5022    2450
            13486 147   142   133   122   59    2391
            14267 304     5     4     2   57    2275
            15252 330    10     1     1   54    2105
            16295 351    11    23    25   26    1968
            17008 362   747   806   880   931   957   98098
            17370 35487 37402 39835 42452 45220 47108 48065
         */

        for (i in 0 until squareCount) {
            if (i < COLUMN_LIMIT) {
                if (i == 0) {
                    nextNumber = current + previousSquare.last() + previousSquare[0]
                }
                else if (i == 1) {
                    nextNumber = current + previousSquare.last() + previousSquare[0] + previousSquare[1]
                }
                else if (i >= 2 && i <= COLUMN_LIMIT-2) {
                    nextNumber = current + previousSquare[i-2] + previousSquare[i-1] + previousSquare[i-0]
                }
                else {
                    nextNumber = current + previousSquare[i-2] + previousSquare[i-1]
                }

                previous = current
                current = nextNumber;

                squareNumbers.add(nextNumber)

            }
            // TOP ROW
            else if (i < COLUMN_LIMIT + ROW_LIMIT) {
                val CORNER = COLUMN_LIMIT - 2

                if (i == COLUMN_LIMIT) {
                    // top-right corner
                    nextNumber = current + previousSquare[CORNER]
                }
                else if (i == COLUMN_LIMIT + 1) {
                    nextNumber = current + previous + previousSquare[CORNER] + previousSquare[CORNER + 1]
                }
                else if (i > COLUMN_LIMIT + 1 && i < COLUMN_LIMIT + ROW_LIMIT - 2) {
                    //println(" with 3: $i $previousSquare")
                    nextNumber = current + previousSquare[i-4] + previousSquare[i-3] + previousSquare[i-2]
                    //println("$nextNumber = $current + ${previousSquare[i-4]} + ${previousSquare[i-3]} + ${previousSquare[i-2]}")
                }
                else if (i == COLUMN_LIMIT + ROW_LIMIT - 2) {
                    nextNumber = current + previousSquare[i-4] + previousSquare[i-3]
                }
                else {
                    nextNumber = current + previousSquare[CORNER + (COLUMN_LIMIT-1)]
                }

                previous = current
                current = nextNumber;
                squareNumbers.add(nextNumber)

            }
            // left column
            else if (i < COLUMN_LIMIT*2 + ROW_LIMIT) {
                if (i == COLUMN_LIMIT + ROW_LIMIT) {
                    nextNumber = current + previous + previousSquare[i-5] + previousSquare[i-4]
                }
                else if (i > COLUMN_LIMIT + ROW_LIMIT && i < COLUMN_LIMIT*2+ROW_LIMIT-1) {
                    nextNumber = current + previousSquare[i-6] + previousSquare[i-5] + previousSquare[i-4]
                    //println("$nextNumber = $current + ${previousSquare[i-6]} + ${previousSquare[i-5]} + ${previousSquare[i-4]}")
                }
                else if (i == COLUMN_LIMIT*2+ROW_LIMIT-1) {
                    nextNumber = current + previousSquare[i-6] + previousSquare[i-5]
                }

                previous = current
                current = nextNumber;
                squareNumbers.add(nextNumber)
            }
            // bottom row
            else {
                val CORNER = COLUMN_LIMIT - 2 + (COLUMN_LIMIT-1) * 2
                if (i == COLUMN_LIMIT*2 + ROW_LIMIT) {
                    nextNumber = current + previousSquare[CORNER]
                }
                else if (i == COLUMN_LIMIT*2 + ROW_LIMIT + 1) {
                    nextNumber = current + previous + previousSquare[CORNER] + previousSquare[CORNER+1]
                }
                else if (i > COLUMN_LIMIT*2 + ROW_LIMIT + 1 && i < COLUMN_LIMIT*2 + ROW_LIMIT*2 - 2) {
                    nextNumber = current + previousSquare[i-8] + previousSquare[i-7] + previousSquare[i-6]
                }
                else if (i == COLUMN_LIMIT*2 + ROW_LIMIT*2 - 2) {
                    nextNumber = current + previousSquare[i-8] + previousSquare[i-7] + squareNumbers.first()
                }
                else {
                    nextNumber = current + previousSquare[CORNER + COLUMN_LIMIT-1] + squareNumbers.first()
                }

                previous = current
                current = nextNumber;
                squareNumbers.add(nextNumber)
            }
        }

        squares.put(level, squareNumbers)
        return squareNumbers
    }
}