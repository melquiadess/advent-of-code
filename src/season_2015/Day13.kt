package season_2015

import BaseDay

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by gregk on 14/11/2016.
 */
class Day13 : BaseDay {
    var list: MutableList<String> = mutableListOf()
    var map: MutableMap<String, Int> = mutableMapOf()
    var names: MutableSet<String> = mutableSetOf()

    override fun run() {
        println("Day13")

        readFile()
        processLines()

        println(map)
        println(names)

        println()

        var test = "test"
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2015/day13"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }

    fun processLines() {
        for (line in list) {
            var split = line.split(" ")
            var key = split[0] + "-" + split[10].replace(".", "")
            var value = split[3].toInt() * (if (split[2].equals("gain")) 1 else -1)

            map.put(key, value)
            names.add(split[0])
        }
    }
}