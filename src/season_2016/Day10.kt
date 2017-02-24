package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by melquiadess on 12/12/2016.
 */
class Day10 : BaseDay {
    var list: MutableList<String> = mutableListOf(
            "value 5 goes to bot 2",
            "bot 2 gives low to bot 1 and high to bot 0",
            "value 3 goes to bot 1",
            "bot 1 gives low to output 1 and high to bot 0",
            "bot 0 gives low to output 2 and high to output 0",
            "value 2 goes to bot 2"
    )

    data class Bot(var number: Int, var values: MutableList<Int> = mutableListOf()) {
        val MIN = 17
        val MAX = 61

        var handle61 = false
        var handle17 = false

        fun addValue(value: Int) {
            values.add(value)
        }

        fun getMax(): Int {
            val max = values.max()

            if (max == MAX) handle61 = true
            if (max == MIN) handle17 = true

            val maxIndex = values.indexOf(max)

            if (maxIndex >= 0) {
                values.removeAt(maxIndex)

                return max!!.toInt()
            }

            return -1
        }

        fun getMin(): Int {
            val min = values.min()
            if (min == MIN) handle17 = true
            if (min == MAX) handle61 = true
            val minIndex = values.indexOf(min)

            if (minIndex >= 0) {
                values.removeAt(minIndex)

                return min!!.toInt()
            }

            return -1
        }

        override fun toString(): String {
            return "Bot(number=$number, values=$values, handle61=$handle61, handle17=$handle17)\n"
        }


    }

    var botList = mutableListOf<Bot>()
    var outputList = mutableListOf<Pair<Int, Int>>()

    override fun run() {
        println("Day10")

        readFile()

        // first pass - distribute values to bots
        list
                .map { it.split(" ") }
                .forEach {
                    when (it[0]) {
                        "value" -> {
                            val botNumber = it[5].toInt()
                            val value = it[1].toInt()

                            var bot = Bot(botNumber)
                            bot.addValue(value)

                            var botInList = botList.find { it.number == botNumber }

                            if (botInList == null) {
                                botList.add(bot)
                            }
                            else {
                                botInList.addValue(value)
                            }
                        }
                    }
                }

        println(botList)
        println()
        println()

        // second pass, distribute values
        // example: bot 0 gives low to output 2 and high to output 0
        while (botList.any { it.values.size > 0 }) {
            // find bots with 2 values
            var loadedBotList = botList.filter { it.values.size >= 2 }

            for (loadedBot in loadedBotList) {

                // find line with instruction for this bot
                var line = list.find { it.split(" ")[1].toInt() == loadedBot.number && it.split(" ")[0].equals("bot") }

                val sp = line!!.split(" ")

                when (sp[0]) {
                    "bot" -> {
                        val botNumber = sp[1].toInt()

                        val lowDest = sp[5]
                        val highDest = sp[10]

                        val bot = botList.find { it.number == botNumber }

                        // no bot found, shouldn't happen!!
                        // skip if bot has just one/zero value
                        if (bot == null || bot.values.size < 2) {
                            println("bot null or values < 2")
                        }
                        else {
                            val low = bot.getMin()

                            if (low != -1) {
                                if (lowDest.equals("output")) {
                                    // don't keep track where low goes
                                    // Part 2: actually we NEED to keep track
                                    val outputNumber = sp[6].toInt()
                                    outputList.add(Pair(outputNumber, low))
                                }
                                else if (lowDest.equals("bot")) {
                                    // find destination bot number
                                    val botDest = sp[6].toInt()

                                    // find that bot
                                    val bot = botList.find { it.number == botDest }
                                    if (bot == null) {
                                        val botToAdd = Bot(botDest)
                                        botToAdd.addValue(low)
                                        botList.add(botToAdd)
                                    }
                                    else {
                                        bot.addValue(low)
                                    }
                                }
                            }


                            val high = bot.getMax()

                            if (high != -1) {
                                if (highDest.equals("output")) {
                                    // don't keep track where low goes
                                    // Part 2: actually we NEED to keep track
                                    val outputNumber = sp[11].toInt()
                                    outputList.add(Pair(outputNumber, high))
                                }
                                else if (highDest.equals("bot")) {
                                    // find destination bot number
                                    val botDest = sp[11].toInt()

                                    // find that bot
                                    val bot = botList.find { it.number == botDest }
                                    if (bot == null) {
                                        val botToAdd = Bot(botDest)
                                        botToAdd.addValue(high)
                                        botList.add(botToAdd)
                                    }
                                    else {
                                        bot.addValue(high)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        println(botList)

        botList.forEach { b -> if (b.handle17 && b.handle61) println("Bot number ${b.number} handles 61 and 17") }

        println()

        outputList.sortBy { it.first }
        var mult = outputList[0].second * outputList[1].second * outputList[2].second
        println("First 3 output bins multiplied: $mult")
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day10-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }
}