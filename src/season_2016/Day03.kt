package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by gregk on 02/12/2016.
 * Part 1
 * 1591: too high
 */
class Day03 : BaseDay {
    var list: MutableList<String> = mutableListOf()

    override fun run() {
        println("Day03")

        readFile()

        println("Possible triangles ${countPossibleTriangles(list)}")
        println("Possible triangles vertically ${countPossibleTrianglesVerticaly(list)}")

        println()
    }

    fun countPossibleTriangles(list: MutableList<String>): Int {
        var possible = 0

        for (line in list) {
            var s = line.trimStart().trimEnd()
                    .replace("   ", " ")
                    .replace("  ", " ")
                    .split(" ")

            var isPossible =   s[0].trim().toInt() + s[1].trim().toInt() > s[2].trim().toInt()
                            && s[1].trim().toInt() + s[2].trim().toInt() > s[0].trim().toInt()
                            && s[0].trim().toInt() + s[2].trim().toInt() > s[1].trim().toInt()

            if (isPossible) possible++
        }

        return possible
    }

    fun countPossibleTrianglesVerticaly(list: MutableList<String>): Int {
        var possible = 0

        var i=0
        while (i < list.size) {
            var s1 = list[i].trimStart().trimEnd()
                    .replace("   ", " ")
                    .replace("  ", " ")
                    .split(" ")

            var s2 = list[i+1].trimStart().trimEnd()
                    .replace("   ", " ")
                    .replace("  ", " ")
                    .split(" ")

            var s3 = list[i+2].trimStart().trimEnd()
                    .replace("   ", " ")
                    .replace("  ", " ")
                    .split(" ")

            for (j in 0..2) {
                var isPossible =
                        s1[j].trim().toInt() + s2[j].trim().toInt() > s3[j].trim().toInt()
                    &&  s2[j].trim().toInt() + s3[j].trim().toInt() > s1[j].trim().toInt()
                    &&  s1[j].trim().toInt() + s3[j].trim().toInt() > s2[j].trim().toInt()

                if (isPossible) possible++
            }

            i += 3
        }

        return possible
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day03-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }
}