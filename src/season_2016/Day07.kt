package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by gregk on 07/12/2016.
 *
 * Part 2:
 * 128 - too low
 * 133 - too low
 * 204 - too low
 * 260 - wrong
 * 258 - correct
 */
class Day07 : BaseDay {
    var list: MutableList<String> = mutableListOf()

    var TEST_INPUT = mutableListOf(
            "abba[mnop]qrst",
            "abcd[bddb]xyyx",
            "aaaa[qwer]tyui",
            "ioxxoj[asdfgh]zxcvbn"
    )

    var TEST_ABA = mutableListOf(
            "aaa[kek]ekek[dod]odo"
    )

    override fun run() {
        println("Day07")

        readFile()
        println("Found ${countDancingQueens(list)} ABBAs")
        println("Found ${countSSLs(list)} SSLs")

        test()

        println()
    }

    /**
     * abba[mnop]qrst
     * abba[mnop]qrst[bggb]bbbb
     */
    fun countDancingQueens(list: MutableList<String>): Int {
        var abbaCount = 0

        var hypernetStrings = mutableListOf<String>()
        var normalStrings = mutableListOf<String>()


        for (line in list) {
            var lineStr = line
            hypernetStrings = getHypernetStrings(line)

            // remove hypernet strings from the main string
            for (hyper in hypernetStrings) {
                lineStr = lineStr.replace(hyper, "-")
            }

            // and add it as normal
            normalStrings.add(lineStr)

            var hasAbba = false

            // counting
            // check if ABBA is in hypernet strings (in square brackets)
            for (s in hypernetStrings) {
                if (hasABBA(s)) hasAbba = true
            }

            // if ABBA not in hypernet strings, let's see if it's in the rest
            if (!hasAbba) {
                for (n in normalStrings) {
                    var parts = n.split("-")

                    if (parts.any { hasABBA(it) }) {
//                        println(line)
                        abbaCount++
                    }
                }
            }

            hypernetStrings.clear()
            normalStrings.clear()
        }

        return abbaCount
    }

    fun countSSLs(list: MutableList<String>): Int {
        var sslCount = 0

        var hypernetStrings = mutableListOf<String>()
        var normalStrings = mutableListOf<String>()


        for (line in list) {
            var lineStr = line
            hypernetStrings = getHypernetStrings(line)

            // remove hypernet strings from main string
            for (hyper in hypernetStrings.filter { it.contains("[") }) {
                lineStr = lineStr.replace(hyper, "-")
            }

            // and add it as normal
            normalStrings.add(lineStr)

            var babSet = mutableSetOf<String>()

            for (s in normalStrings) {
                var parts = s.split("-")
                for (part in parts) {
                    var babs = getBABs(part)
                    for (bab in babs) {
                        babSet.add(bab)
                    }
                }
            }

            // clean hypernet
            var cleanHypernet = hypernetStrings.filter { !it.contains("[") }.toMutableSet()

            if (babSet.isNotEmpty()) {
                var hyper = cleanHypernet.joinToString("-")

                for (bab in babSet) {
                    if (hyper.contains(bab)) {
                        sslCount++
                        break
                    }
                }
            }

            hypernetStrings.clear()
            normalStrings.clear()
        }

        return sslCount
    }

    fun getHypernetStrings(line: String): MutableList<String> {
        var list = mutableListOf<String>()

        var reg = """\[(.*?)\]""".toRegex()

        var stringsInBrackets = reg.findAll(line)
        for (matched in stringsInBrackets) {
            for (gr in matched.groups) {
                list.add(gr!!.value)
            }
        }

        return list
    }

    fun hasABBA(str: String): Boolean {
        var i=0
        while (i < str.length-3) {
            if (str[i] == str[i+3] && str[i+1] == str[i+2] && str[i] != str[i+1]) {
                return true
            }

            i++
        }

        return false
    }

    /**
     * ABA
     * BAB = 1 + 0 + 1
     */
    fun getBABs(str: String): MutableSet<String> {
        var babSet = mutableSetOf<String>()

        var i=0
        while (i < str.length-2) {
            if (str[i] == str[i+2] && str[i] != str[i+1]) {
                babSet.add(str[i+1].toString() + str[i].toString() + str[i+1].toString())
            }
            i++
        }

        return babSet
    }

    fun test() {
        assert(hasABBA(TEST_INPUT[0]))
        assert(hasABBA(TEST_INPUT[1]))
        assert(!hasABBA(TEST_INPUT[2]))
        assert(hasABBA(TEST_INPUT[3]))
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/day07-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }
}