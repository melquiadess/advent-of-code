package season_2016

import java.nio.file.Files
import java.nio.file.Paths

import BaseDay

/**
 * Created by melquiadess on 08/12/2016.
 *
 * Part 1:
 * 21 - too low
 */
class Day08 : BaseDay {
    var list: MutableList<String> = mutableListOf()

    private val WIDTH = 50
    private val HEIGHT = 6

    var LCD = Array(WIDTH, { Array(HEIGHT, { it -> 0 }) })

    override fun run() {
        println("Day08")

        test()

        readFile()

        println("Lit pixels count: ${getLitPixelsCount()}")

        findRotatedPattern(LCD);

        println("Lit pixels after lcd manipulations: ${countPixels(LCD)}");
    }

    /**
     * Since rect adds lit pixels, and they wrap as row/column manipulations,
     * we can just count how many pixels have been added by all rects
     */
    fun getLitPixelsCount(): Int =
            list
                    .filter { it.contains("rect") }
                    .map { it.split(" ") }
                    .map { it[1].split("x") }
                    .sumBy { it[0].toInt() * it[1].toInt() }

    /**
     * Old method, counts lit pixels by iterating over the whole array
     */
    fun countPixels(lcd: Array<Array<Int>>): Int {
        var count = 0

        for (x in 0..WIDTH-1) {
            for (y in 0..HEIGHT-1) {
                if (lcd[x][y] == 1) count++
            }
        }

        return count
    }

    fun findRotatedPattern(lcd: Array<Array<Int>>) {
        for (line in list) {
            if (line.contains("rect")) {
                var s = line.split(" ")
                var ss = s[1].split("x")
                rect(ss[0].toInt(), ss[1].toInt(), lcd)
            }
            else {
                val parts = line.split(" ")

                val moveString = parts[2]
                val move = moveString.split("=")[1].toInt()
                val delta = parts[4].toInt()

                when (parts[1]) {
                    "row" -> rotateY(move, delta, lcd)
                    "column" -> rotateX(move, delta, lcd)
                }
            }
            printLCD(lcd)
        }
    }

    fun rect(A: Int, B: Int, lcd: Array<Array<Int>>) {
        for (x in 0..A-1) {
            for (y in 0..B-1) {
                lcd[x][y] = 1
            }
        }
    }

    fun rotateX(col: Int, delta: Int, lcd: Array<Array<Int>>) {
        var column = ""

        for (y in 0..HEIGHT-1) {
            column += lcd[col][y]
        }

        var shiftedColumn = shiftRight(column, delta)

        for (y in 0..HEIGHT-1) {
            lcd[col][y] = if (shiftedColumn[y].equals('0')) 0 else 1
        }
    }

    fun rotateY(row: Int, delta:Int, lcd: Array<Array<Int>>) {
        var rows = ""

        for (x in 0..WIDTH-1) {
            rows += lcd[x][row]
        }

        var shiftedRow = shiftRight(rows, delta)

        for (x in 0..WIDTH-1) {
            lcd[x][row] = if (shiftedRow[x].equals('0')) 0 else 1
        }
    }

    fun printLCD(lcd: Array<Array<Int>>) {
        for (y in 0..HEIGHT-1) {
            for (x in 0..WIDTH-1) {
                if (x % 5 == 0) print(" ")
                print("${if (lcd[x][y] == 0) " " else "\u2588"}")
                //print(lcd[x][y])
            }
            println()
        }
        println()
    }

    fun shiftRight(line: String, shift: Int): String {
        var i = shift % line.length
        return line.substring(line.length-i) + line.substring(0, line.length-i)
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day08-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }

    fun test() {
        assert(shiftRight("00001111", 4).equals("11110000"))
        assert(shiftRight("110000", 1).equals("011000"))
        assert(shiftRight("01010101", 1).equals("10101010"))
        assert(shiftRight("000111", 3).equals("111000"))
    }
}