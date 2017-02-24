package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by melquiadess on 20/12/2016.
 */
class Day20 : BaseDay {
//    var list = mutableListOf<String>()
    var list = mutableListOf(
            "5-8",
            "0-2",
            "4-7"
    )

    var validLow = 0L
    var validHigh = 4294967295L // 2^32 - 1

    data class Range(var low: Long, var high: Long)

    var ordered = mutableListOf<Range>()

    override fun run() {
        println("Day20")
        println(Int.MAX_VALUE) // 2^31 - 1
        println(Long.MAX_VALUE)

        readFile()

        println(list)

        println(rangesFromList(list))

        ordered.sortBy { it.low }
        println(ordered)
        println(ordered.size)

        var whitelisted = findWhitelisted(ordered)

        var countIps = 0

        var i=0
        while (i < whitelisted.size-1) {
            var diff = whitelisted[i+1].low - whitelisted[i].high

            if (diff > 1) {
                countIps++
            }
            i++
        }
        for (range in whitelisted) {
            println("${i++} " + range.low + " " + range.high)
        }

        println("IPs: " + countIps)

    }

    fun findWhitelisted(ranges: MutableList<Range>): MutableList<Range> {
        var currentIndex = 0

        var combinedRanges = mutableListOf<Range>()

        while (currentIndex < ranges.size) {
            var currentRange = Range(ranges[currentIndex].low, ranges[currentIndex].high)

            while (currentIndex + 1 < ranges.size && ranges[currentIndex+1].low <= currentRange.high) {
                currentIndex++

                if (ranges[currentIndex].high > currentRange.high) {
                    currentRange.high = ranges[currentIndex].high
                }
            }

            combinedRanges.add(currentRange)
            currentIndex++
        }

        return combinedRanges
    }

    fun rangesFromList(list: MutableList<String>) {
        for (line in list) {
            var parts = line.split("-")

            var low = parts[0].toLong()
            var high = parts[1].toLong()

            ordered.add(Range(low, high))
        }
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day20-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }
}