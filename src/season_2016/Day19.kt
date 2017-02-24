package season_2016

import BaseDay
import season_2015.Stopwatch

/**
 * Created by melquiadess on 19/12/2016.
 */
class Day19 : BaseDay {
    val ELF_COUNT = 3017957

    val COUNT = ELF_COUNT

    var elves = mutableListOf<Pair<Int, Int>>()

    override fun run() {
        println("Day19")

        init()

        var watch = Stopwatch()
        watch.startTimer()

        stealPresentsBetter()

        println("Done in ${watch.getElapsedSeconds()} seconds")
    }

    fun stealPresents() {
        var marker = 0

        while (elves.count { it.second > 0 } > 1) {
            // find elf with presents
            if (marker > elves.size-1) marker = 0
            while (elves[marker].second < 1) {
                marker++

                if (marker > elves.size-1) marker = 0
            }

            var i=marker+1
            if (i > elves.size-1) i=0

            //find next elf with present(s)
            while (elves[i].second < 1) {
                i++
                if (i > elves.size-1) {
                    i = 0
                }
            }

            // steal presents
            elves[marker] = Pair(elves[marker].first, elves[marker].second + elves[i].second)
            elves[i] = Pair(elves[i].first, 0)

            marker = i+1

            if (marker % 1000 == 0) println("marker: $marker")
        }

        println(elves.find { it.second > 0 })
    }

    fun stealPresentsBetter() {
        var marker = 0

        while (elves.size > 1) {
            // find elf with presents
            if (marker > elves.size-1) marker = 0
            while (elves[marker].second < 1) {
                marker++

                if (marker > elves.size-1) marker = 0
            }

            var i=marker+1
            if (i > elves.size-1) i=0

            //find next elf with present(s)
            while (elves[i].second < 1) {
                i++
                if (i > elves.size-1) {
                    i = 0
                }
            }

            // steal presents
            elves[marker] = Pair(elves[marker].first, elves[marker].second + elves[i].second)
            elves[i] = Pair(elves[i].first, 0)
            elves.removeAt(i)

            marker = i

            if (marker % 1000 == 0) println("marker: $marker (current size: ${elves.size})")
        }

        println(elves.find { it.second > 0 })
    }

    fun init() {
        for (i in 1..COUNT) {
            elves.add(Pair(i, 1))
        }
    }
}