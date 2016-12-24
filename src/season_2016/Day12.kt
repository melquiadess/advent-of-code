package season_2016

import BaseDay
/**
 * Created by melquiadess on 12/12/2016.
 */
class Day12 : BaseDay {
    var list: MutableList<String> = mutableListOf(
            "cpy 1 a",
            "cpy 1 b",
            "cpy 26 d",
            "jnz c 2",
            "jnz 1 5",
            "cpy 7 c",
            "inc d",
            "dec c",
            "jnz c -2",
            "cpy a c",
            "inc a",
            "dec b",
            "jnz b -2",
            "cpy c b",
            "dec d",
            "jnz d -6",
            "cpy 19 c",
            "cpy 14 d",
            "inc a",
            "dec d",
            "jnz d -2",
            "dec c",
            "jnz c -5"
    )

    var test: MutableList<String> = mutableListOf(
            "cpy 41 a",
            "inc a",
            "inc a",
            "dec a",
            "jnz a 2",
            "dec a"
    )

    var a: Int = 0
    var b: Int = 0
    var c: Int = 1
    var d: Int = 0

    fun assembunny(list: MutableList<String>): Int {
        var marker = 0

        while (true) {
            var instr = list[marker].split(" ")

            var instruction = instr[0]

            when (instruction) {
                "jnz" -> {
                    var jump = instr[2].toInt()
                    when (instr[1]) {
                        "a" -> if (a != 0) marker += jump else marker++
                        "b" -> if (b != 0) marker += jump else marker++
                        "c" -> if (c != 0) marker += jump else marker++
                        "d" -> if (d != 0) marker += jump else marker++
                        else -> marker += jump
                    }
                }

                "cpy" -> {
                    var register = instr[2]
                    var copyValue = when (instr[1]) {
                        "a" -> a
                        "b" -> b
                        "c" -> c
                        "d" -> d
                        else -> instr[1].toInt()
                    }

                    when (register) {
                            "a" -> a = copyValue
                            "b" -> b = copyValue
                            "c" -> c = copyValue
                            "d" -> d = copyValue
                    }

                    marker++
                }
                
                "inc" -> {
                    when(instr[1]) {
                        "a" -> a++
                        "b" -> b++
                        "c" -> c++
                        "d" -> d++
                    }

                    marker++
                }

                "dec" -> {
                    when(instr[1]) {
                        "a" -> a--
                        "b" -> b--
                        "c" -> c--
                        "d" -> d--
                    }

                    marker++
                }
            }

            if (marker >= list.size) {
                return a
            }
        }
    }

    override fun run() {
        println("Day09")

        println("Value of register a: ${assembunny(list)}")
    }
}