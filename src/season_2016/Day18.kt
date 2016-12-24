package season_2016

import BaseDay
/**
 * Created by melquiadess on 18/12/2016.
 */
class Day18 : BaseDay {
    val INPUT = "....^^^^^..^...^...^^^^^^...^.^^^.^.^.^^.^^^.....^.^^^...^^^^^^.....^.^^...^^^^^...^.^^^.^^......^^^^."
    val ROW_COUNT = 400000

    var board = mutableListOf<String>()

    val TEST_INPUT = "...^^.."
    var TEST_ROW_COUNT = 3
    var testBoard = mutableListOf(
            "...^^.."
    )

//    var testBoard = mutableListOf(
//            ".^^.^.^^^^",
//            "^^^...^..^",
//            "^.^^.^.^^.",
//            "..^^...^^^",
//            ".^^^^.^^.^",
//            "^^..^.^^..",
//            "^^^^..^^^.",
//            "^..^^^^.^^",
//            ".^^^..^.^^",
//            "^^.^^^..^^"
//    )

    override fun run() {
        println("Day18")

        // initialize board with first line
        board.add(INPUT)

        //test input for safe tiles
        var count = getSafeTilesCount(board)
        println(count)

        solvePart1()
    }

    fun solvePart1() {
        var nextRow = INPUT
        for (i in 0..ROW_COUNT-2) {
            nextRow = '.' + getNextRow(nextRow) + '.'
            board.add(nextRow)
        }

        println("Safe tiles: ${getSafeTilesCount(board)}")
    }

    fun getNextRow(row: String): String {
        var newRow = ""

        for (x in 1..row.length-2) {
            newRow += if (isTrap(row.substring(x-1, x+2))) '^' else '.'
        }

        return newRow
    }

    fun isTrap(str: String): Boolean {
        return (str[0]=='^' && str[1] == '^' && str[2] == '.')
        || (str[0]== '.' && str[1] == '^' && str[2] == '^')
        || (str[0] == '^' && str[1] == '.' && str[2] =='.')
        || (str[0] == '.' && str[1] == '.' && str[2] == '^')
    }

    /**
     * Sum safe tiles, and subtract wall tiles (added as safe tiles on both ends)
     */
    fun getSafeTilesCount(board: MutableList<String>) = board.sumBy { row -> row.count { it.equals('.') } } - board.size * 2
}