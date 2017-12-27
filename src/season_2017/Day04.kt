package season_2017

import AbstractDay

/**
 * Created by melquiadess on 04/12/2017.
 *
 * Part 2
 * 202 - too high
 * 167 - correct
 */
class Day04 : AbstractDay("Day04") {
    var list: MutableList<String> = mutableListOf()

    override fun part1() {
        println("PART 1")

        list = readFile("src/season_2017/input/day04-input")

        val count = list.count { it -> isValidPassphrase(it) }

        println("Answer: $count of ${list.size} are valid")
    }

    fun isValidPassphrase(row: String): Boolean {
        var splits = row.split(" ")
        var alreadyPresent = mutableListOf<String>()

        for (pass in splits) {
            val freq = alreadyPresent.count { it.equals(pass) }
            if (freq == 0) {
                alreadyPresent.add(pass)
            } else return false
        }

        return true
    }

    fun isAnagram(firstWord: String, secondWord: String): Boolean {
        val leftString = firstWord.toList().sorted().toList().joinToString("")
        val rightString = secondWord.toList().sorted().toList().joinToString("")

        return leftString == rightString
    }

    fun hasNoAnagramWords(row: String): Boolean {
        val splits = row.split(" ").toList()

        splits.forEachIndexed { index, value ->
            splits.forEachIndexed { index2, value2 ->
                if (index == index2) {
                    // skip, we don't compare words on the same position
                } else {
                    // immediately return false, because we have anagram, so this pass phrase is invalid
                    if (isAnagram(value, value2)) return false
                }
            }
        }

        return true
    }

    override fun part2() {
        println("PART 2")

        list = readFile("src/season_2017/input/day04-input")

        testAnagrams()

        val count = list.count { it -> hasNoAnagramWords(it) }
        println("Answer: $count of ${list.size} are valid")
    }

    fun testAnagrams() {
        assert(isAnagram("abcde", "ecdab"))
        assert(!isAnagram("abcde", "fghij"))
    }
}