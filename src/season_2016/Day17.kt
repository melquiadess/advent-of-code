package season_2016

import BaseDay
import java.security.MessageDigest

/**
 * Created by melquiadess on 17/12/2016.
 */
class Day17 : BaseDay {
    private val WIDTH = 9
    private val HEIGHT = 9

    val WALL = '#'
    val DOOR_VERTICAL = '|'
    val DOOR_HORIZONTAL = '-'
    val SPACE = ' '
    val WTF = '*'

    var START = Position(1, 1)
    val VAULT = Position(7, 7)
    var currentPos = START

    var ROOM = Array(WIDTH, { Array(HEIGHT, { it -> '#' }) })

    var SECRET_CODE = "hijkl"

    var moves: MutableList<String> = mutableListOf()
    var badPaths: MutableList<String> = mutableListOf()

    data class Position(var x: Int, var y: Int)

    fun getMd5(str: String): String {
        return md5(str).toHexString().take(4)
    }

    fun initializeRoom() {
        for (y in 0..HEIGHT-1) {
            for (x in 0..WIDTH-1) {
                ROOM[x][y] =
                when(y) {
                    0 -> WALL
                    HEIGHT-1 -> WALL
                    1,3,5,7 -> {
                        when(x) {
                            0 -> WALL
                            WIDTH-1 -> WALL
                            else -> {
                                if (x % 2 == 0) DOOR_VERTICAL else SPACE
                            }
                        }
                    }
                    2,4,6 -> {
                        when(x) {
                            0,2,4,6,8 -> WALL
                            1,3,5,7 -> if (x % 2 == 1) DOOR_HORIZONTAL else SPACE
                            else -> WTF
                        }
                    }
                    else -> WTF
                }
            }
        }

        // remove few walls
        ROOM[8][8] = 'V'
        ROOM[7][8] = SPACE
        ROOM[8][7] = SPACE
    }

    fun isInVault(pos: Position): Boolean = pos == VAULT

    fun isOpen(door: Char): Boolean {
        when (door) {
            'b', 'c', 'd', 'e', 'f' -> return true
            else -> return false
        }
    }

    fun canGo(dir: Char, pos: Position): Boolean {
        when (dir) {
            'U' -> {
                return pos.y >= 3
            }
            'D' -> {
                return pos.y <= 5
            }
            'L' -> {
                return pos.x >= 3
            }
            'R' -> {
                return pos.x <= 5
            }
        }

        return false
    }

    fun makeOppositeMove(dir: Char, pos: Position): Position {
        when (dir) {
            'U' -> {
                return Position(pos.x, pos.y-2)
            }
            'D' -> {
                return Position(pos.x, pos.y+2)
            }
            'L' -> {
                return Position(pos.x+2, pos.y)
            }
            'R' -> {
                return Position(pos.x-2, pos.y)
            }
        }

        return START
    }


    fun solvePart1() {
        var md5 = ""
        var move = ""

        var prevMove = ""

        var wasLocked = false

        while (!isInVault(currentPos)) {
            if (wasLocked) {
                // last move was bad
                if (moves.size == 0) {
                    println("No moves left")
                    break
                }
                badPaths.addAll(moves)

                prevMove = moves.removeAt(moves.size-1)
                currentPos = makeOppositeMove(move[move.length-1], currentPos)
                move = move.take(move.length-1)

                wasLocked = false
            }

            while (true) {
                md5 = getMd5(SECRET_CODE + move)

                var U = md5[0]
                var D = md5[1]
                var L = md5[2]
                var R = md5[3]

                if (isOpen(U) && canGo('U', currentPos) && !badPaths.contains(move)) {
                    moves.add("U")
                    move += "U"
                    currentPos.y -= 2
                }

                else if (isOpen(D) && canGo('D', currentPos) && !badPaths.contains(move)) {
                    moves.add("D")
                    move += "D"
                    currentPos.y += 2
                }

                else if (isOpen(L) && canGo('L', currentPos) && !badPaths.contains(move)) {
                    moves.add("L")
                    move += "L"
                    currentPos.x -= 2
                }

                else if (isOpen(R) && canGo('R', currentPos) && !badPaths.contains(move)) {
                    moves.add("R")
                    move += "R"
                    currentPos.x += 2
                }

                else {
                    //locked
                    if (isInVault(currentPos)) {
                        println("Path: $moves in position $currentPos")
                        break
                    }
                    else {
                        println("Locked in position $currentPos after $moves")
                        wasLocked = true
                        break
                    }
                }
            }

        }
    }

    override fun run() {
        println("Day17")

        initializeRoom()

        printRoom(ROOM)

//        println(isInVault(pos))
//
        solvePart1()

    }

    fun printRoom(room: Array<Array<Char>>): Unit {
        for (y in 0..HEIGHT-1) {
            for (x in 0..WIDTH - 1) {
                print(room[x][y])
            }
            println()
        }
    }



    val digester: MessageDigest? = MessageDigest.getInstance("MD5")
    private fun md5(text: String) : ByteArray {
        return digester?.digest(text.toByteArray()) as ByteArray
    }

    /**
     *  Set of chars for a half-byte.
     */
    private val CHARS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     *  Returns the string of two characters representing the HEX value of the byte.
     */
    internal fun Byte.toHexString() : String {
        val i = this.toInt()
        val char2 = CHARS[i and 0x0f]
        val char1 = CHARS[i shr 4 and 0x0f]
        return "$char1$char2"
    }

    /**
     *  Returns the HEX representation of ByteArray data.
     */
    internal fun ByteArray.toHexString() : String {
        val builder = StringBuilder()
        for (b in this) {
            builder.append(b.toHexString())
        }
        return builder.toString()
    }
}