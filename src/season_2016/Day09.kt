package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by melquiadess on 09/12/2016.
 */
class Day09 : BaseDay {
    var list: MutableList<String> = mutableListOf(
            "A(2x2)BCD(2x2)EFG",
            "ADVENT",
            "A(1x5)BC",
            "(3x3)XYZ",
            "(6x1)(1x3)A",
            "X(8x2)(3x3)ABCY"
    )

    override fun run() {
        println("Day09")

        readFile()

        for (line in list) {
            var result = decompress(line.trim())
            println("$line decompressed: ${result} \nhas length ${result.length}")
        }
    }

    fun getListOfParentheses(str: String): MutableList<String> {
        var parList = mutableListOf<String>()

        var reg = """\((.*?)\)""".toRegex()

        var stringsInBrackets = reg.findAll(str)

        for (matched in stringsInBrackets) {
            parList.add(matched.groups[0]!!.value)
        }

        return parList
    }

    fun getListOfValidParentheses(str: String): MutableList<String> {
        var parList = mutableListOf<String>()

        var reg = """\((.*?)\)^((.*?))""".toRegex()

        var stringsInBrackets = reg.findAll(str)

        for (matched in stringsInBrackets) {
            parList.add(matched.groups[0]!!.value)
        }

        return parList
    }

    fun decompress(str: String): String {
        var decompressed: StringBuilder = StringBuilder()

        if (!str.contains("(")) return str

        var marker = 0
        var indexOfParOpen = 0
        var indexOfParClose = 0
        var currentParContent = ""
        var currentParLength = 0
        var currentRepeatPattern: String = ""

        while (true) {
            indexOfParOpen = str.indexOf("(", marker)
            // find (
            if (indexOfParOpen < 0) {
                // no more (, append the rest of string
                var rest = str.substring(indexOfParClose+currentRepeatPattern.length+1)
                decompressed.append(rest)
                break
            }
            // append whatever is before opening (
            var beforeBracket = str.substring(marker, indexOfParOpen)
            decompressed.append(beforeBracket)
            marker += beforeBracket.length

            // find )
            indexOfParClose = str.indexOf(")", marker)

            currentParContent = str.substring(indexOfParOpen, indexOfParClose+1)
            println(currentParContent)

            var instr = currentParContent
                    .replace("(", "")
                    .replace(")", "")
                    .split("x")

            currentParLength = currentParContent.length

            currentRepeatPattern = str.substring(indexOfParOpen + currentParLength, indexOfParOpen + currentParLength + instr[0].toInt())

            for (x in 0..instr[1].toInt()-1) {
                decompressed.append(currentRepeatPattern)
            }

            marker += currentParLength + currentRepeatPattern.length
        }

        return decompressed.toString()
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day09-input"))
        list.clear()

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }
}