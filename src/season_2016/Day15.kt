package season_2016

import BaseDay
/**
 * Created by melquiadess on 15/12/2016.
 */
class Day15 : BaseDay {
    var list = mutableListOf(
            "Disc #1 has 13 positions; at time=0, it is at position 1",
            "Disc #2 has 19 positions; at time=0, it is at position 10",
            "Disc #3 has 3 positions; at time=0, it is at position 2",
            "Disc #4 has 7 positions; at time=0, it is at position 1",
            "Disc #5 has 5 positions; at time=0, it is at position 3",
            "Disc #6 has 17 positions; at time=0, it is at position 5"
    )

    var testList = mutableListOf(
            "Disc #1 has 5 positions; at time=0, it is at position 4",
            "Disc #2 has 2 positions; at time=0, it is at position 1."
    )

    var discs = mutableListOf(
            Disk(1, 13, 1),
            Disk(2, 19, 10),
            Disk(3, 3, 2),
            Disk(4, 7, 1),
            Disk(5, 5, 3),
            Disk(6, 17, 5),
            Disk(7, 11, 0)
    )

    var testDiscs = mutableListOf(
            Disk(1, 5, 4),
            Disk(2, 2, 1)
    )

    data class Disk(var number: Int, var positions: Int, var startingPosition: Int)


    override fun run() {
        println("Day15")

//        println(getPositionAtTime(testDiscs[0], 1))
//        println(getPositionAtTime(testDiscs[0], 2))
//        println(getPositionAtTime(testDiscs[1], 2))

        println(findTime())
    }

    fun findTime(): Int {
        var pressTime = 0

        while (true) {
            var d1 = getPositionAtTime(discs[0], pressTime+1)
            var d2 = getPositionAtTime(discs[1], pressTime+2)
            var d3 = getPositionAtTime(discs[2], pressTime+3)
            var d4 = getPositionAtTime(discs[3], pressTime+4)
            var d5 = getPositionAtTime(discs[4], pressTime+5)
            var d6 = getPositionAtTime(discs[5], pressTime+6)
            var d7 = getPositionAtTime(discs[6], pressTime+7)

            if (d1 == d2 && d1 == d3 && d1 == d4 && d1 == d5 && d1 == d6 && d1 == d7) return pressTime

            pressTime++
        }

        return -1
    }

    fun getPositionAtTime(disk: Disk, time: Int): Int {
        return (disk.startingPosition + time) % disk.positions
    }
}