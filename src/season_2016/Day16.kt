package season_2016

import BaseDay
import season_2015.Stopwatch

/**
 * Created by melquiadess on 15/12/2016.
 */
class Day16 : BaseDay {
    var INPUT = "10001001100000001"
    var LENGTH = 35651584

    var TEST_INPUT = "110010110100"
    var TEST_LENGTH = 12

    var CHUNK_COUNT = 1088
    var CHUNK_SIZE = LENGTH / CHUNK_COUNT

    override fun run() {
        println("Day16")

        testGenerate()
        testChecksum()
        test()

        solvePart1()
    }

    fun solvePart1() {
        var input = generateRandomData(INPUT, LENGTH)
        assert(input.length == LENGTH)

        println("Generated data with ${input.length} length")

        var watch = Stopwatch()
        watch.startTimer()

        var checksum = ""

        var t=0

        // getting checksum of giant string won't work, so let's get the string in chunks
        // and calc their checksums then combine
        for (i in 0..(CHUNK_COUNT -1)) {
            checksum += getChecksum(input.substring(i*CHUNK_SIZE, i*CHUNK_SIZE + CHUNK_SIZE))
            println("${t++} [${i*CHUNK_SIZE}, ${i*CHUNK_SIZE+CHUNK_SIZE}] -> ${checksum.length}")
        }

        // we now have checksum with length of 1088
        // which we can easily run through checksum algo again to get the final result
        checksum = getChecksum(checksum)

        println("Checksum is $checksum")

        println("\nDone in ${watch.getElapsedSeconds()} seconds")
    }

    private fun testGenerate() {
        assert(generateRandomData("1", TEST_LENGTH).length >= TEST_LENGTH)
        assert(generateRandomData("0", TEST_LENGTH).length >= TEST_LENGTH)
        assert(generateRandomData("11111", TEST_LENGTH).length >= TEST_LENGTH)
        assert(generateRandomData("111100001010", TEST_LENGTH).length >= TEST_LENGTH)
    }

    private fun testChecksum() {
        assert(getChecksum(TEST_INPUT).equals("100"))
    }

    private fun test() {
        var input = generateRandomData("10000", 20)
        assert(input.length == 20)

        var checksum = getChecksum(input)
        assert(checksum.equals("01100"))
    }

    /**
     *  - Call the data you have at this point "a".
        - Make a copy of "a"; call this copy "b".
        - Reverse the order of the characters in "b".
        - In "b", replace all instances of 0 with 1 and all 1s with 0.
        - The resulting data is "a", then a single 0, then "b".
     */
    fun generateRandomData(str: String, length: Int): String {
        var data = str
        var a = ""
        var b = ""


        while (data.length < length) {
            a = data
            b = a.reversed().replace("0", "X").replace("1", "0").replace("X", "1")
            data = a + "0" + b
        }

        return data.substring(0, length)
    }

    tailrec fun getChecksum(str: String): String {
        var checksum = ""
        var candidate = ""

        (0..str.length-1 step 2).forEach { i ->
            candidate = str[i] + str[i+1].toString()
            when (candidate) {
                "00", "11" -> checksum += "1"
                else -> checksum += "0"
            }
        }

        if (checksum.length % 2 == 1) return checksum else return getChecksum(checksum)
    }
}