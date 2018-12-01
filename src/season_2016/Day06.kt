package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by melquiadess on 06/12/2016.
 */
class Day06 : BaseDay {
    var list: MutableList<String> = mutableListOf()
    var freqList: List<MutableMap<Char, Int>> = mutableListOf(
        mutableMapOf(),
        mutableMapOf(),
        mutableMapOf(),
        mutableMapOf(),
        mutableMapOf(),
        mutableMapOf(),
        mutableMapOf(),
        mutableMapOf()
    )

    var TEST_DATA: MutableList<String> = mutableListOf(
            "eedadn",
            "drvtee",
            "eandsr",
            "raavrd",
            "atevrs",
            "tsrnev",
            "sdttsa",
            "rasrtv",
            "nssdts",
            "ntnada",
            "svetve",
            "tesnvt",
            "vntsnd",
            "vrdear",
            "dvrsen",
            "enarar"
    )

    override fun run() {
        println("Day06")

        readFile()
        analyze(list)

        for (map in freqList) {
            print(map.maxBy { it.value }?.component1() )
        }

        println()

        for (map in freqList) {
            print(map.minBy { it.value }?.component1() )
        }

        println()
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day01-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }

    fun analyze(list: MutableList<String>) {
        var i: Int
        for (line in list) {
            i = 0
            for (c in line) {
                findAndIncrement(c, i++)
            }
        }
    }

    /**
     * Adds to a map a letters as key, and it's frequency as values (incrementing if already in the map)
     */
    fun findAndIncrement(c: Char, i: Int) {
        // c is a letter we're looking for
        // so this returns a map with keys==c
        var freq = freqList[i].filterKeys { it.equals(c) }

        // if it's not empty, that letter is already in the map
        // so let's take its current frequency, increment by one and add back
        if (!freq.isEmpty()) {
            var v = freq.get(c)
            var v1 = v?.toInt() ?: 0
            freqList[i].put(c, v1+1)
        }
        // otherwise add this letter and set freq count as 1
        else {
            freqList[i].put(c, 1)
        }
    }
}