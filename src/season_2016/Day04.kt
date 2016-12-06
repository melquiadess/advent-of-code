package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.comparisons.compareByDescending

/**
 * Created by gregk on 03/12/2016.
 *
 * Part 1:
 * 191663 - too high
 * 158835
 */
class Day04 : BaseDay {
    var sumOfId: Int = 0
    var freqMap: MutableMap<String, Int> = mutableMapOf()
    var list: MutableList<String> = mutableListOf()

    var realRooms: MutableList<String> = mutableListOf()
    var decodedRooms: MutableList<String> = mutableListOf()

    override fun run() {
        println("Day04")

        readFile()

        countRealIdSums(list)

        println("Sum is $sumOfId")

        decodeRooms(list)

        testDecode()

        println()
    }

    fun decodeRooms(list: MutableList<String>) {
        for (room in list) {
            var p = room.split("-")

            var id = p.last().split("[")[0].toInt()

            var code = p.dropLast(1).joinToString("-")

            var r = decode(code, id)
            decodedRooms.add(r)

            if (r.contains("storage") || r.contains("store")) {
                println("Candidate: $r with id=$id")
            }
        }
    }

    /**
     * ASCII a=97 z=122 -=45
     */
    fun decode(str: String, shift: Int): String {
        var code = str.replace("-", " ")

        var key = shift % 26

        var decoded = ""

        for (c in code) {
            when (c) {
                in 'a'..'z' -> {
                    var shifted = c + (key % 26)
                    if (shifted > 'z') shifted -= 26
                    decoded += shifted
                }
                else -> {
                    decoded += c
                }
            }
        }

        return decoded
    }

    fun countRealIdSums(list: MutableList<String>): Int {
        list
                .filter { isReal(it) }
                .forEach { realRooms.add(it) }

        return sumOfId
    }

    /**
     * aaaaa-bbb-z-y-x-123[abxyz]
     *
     * aaaaa-bbb-z-y-x
     */
    fun isReal(room: String): Boolean {
        var p = room.split("-")

        var idAndChecksum = p.last()
        var id = idAndChecksum.split("[")[0].toInt()
        var checksum = idAndChecksum.split("[")[1].replace("]", "")

        var code = p.dropLast(1)



        var ok = matchesChecksum(code.joinToString("-"), checksum)
        if (ok) sumOfId += id

        return ok
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/day04-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }

    /**
     * Adds to a map a letters as key, and it's frequency as values (incrementing if already in the map)
     */
    fun findAndIncrement(c: String) {
        // c is a letter we're looking for
        // so this returns a map with keys==c
        var freq = freqMap.filterKeys { it.equals(c) }

        // if it's not empty, that letter is already in the map
        // so let's take its current frequency, increment by one and add back
        if (!freq.isEmpty()) {
            var v = freq.get(c)
            var v1 = v?.toInt() ?: 0
            freqMap.put(c, v1+1)
        }
        // otherwise add this letter and set freq count as 1
        else {
            freqMap.put(c, 1)
        }
    }

    /**
     * aaaaa-bbb-z-y-x
     * nzydfxpc-rclop-qwzhpc-qtylyntyr
     */
    fun matchesChecksum(str: String, checksum: String): Boolean {
        var line = str.replace("-", "")

        freqMap.clear()

        // loop through each letter in code, and build frequency map
        line.forEach { c -> findAndIncrement(c.toString()) }

        // now we want to have a map as follows:
        // - 5 most frequent letters at the top
        // - if letters have the same frequency, they should be picked up alphabetically
        // The trick was to first sort the map alphabetically
        // then by the frequency field!
        var freqMapSorted = freqMap
                // first sort the map alphabetically
                .toSortedMap()
                .toList()
                .sortedWith(compareByDescending { it.second })
                .take(5)

        // not compare each of 5 letters with the checksum
        for (i in 0..4) {
            var check = checksum[i].toString()
            var mapElement = freqMapSorted[i].first

            if (!check.equals(mapElement)) return false
        }

        return true
    }

    fun test() {
        sumOfId = 0
        assert(isReal("aaaaa-bbb-z-y-x-123[abxyz]"))
        assert(isReal("a-b-c-d-e-f-g-h-987[abcde]"))
        assert(isReal("not-a-real-room-404[oarel]"))
        assert(!isReal("totally-real-room-200[decoy]"))
    }

    fun testDecode() {
        assert(decode("qzmt-zixmtkozy-ivhz", 343).equals("very encrypted name"))
    }
}