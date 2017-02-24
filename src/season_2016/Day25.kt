package season_2016

import BaseDay
/**
 * Created by melquiadess on 25/12/2016.
 */
class Day25 : BaseDay {
    data class Op(var name: String, var reg1: String = "", var reg2: String = "")

    var input = mutableListOf(
            Op("cpy", "a", "d"),
            Op("cpy", "7", "c"),
            Op("cpy", "362", "b"),
            Op("inc", "d"),
            Op("dec", "b"),
            Op("jnz", "b", "-2"),
            Op("dec", "c"),
            Op("jnz", "c", "-5"),
            Op("cpy", "d", "a"),
            Op("jnz", "0", "0"),
            Op("cpy", "a", "b"),
            Op("cpy", "0", "a"),
            Op("cpy", "2", "c"),
            Op("jnz", "b", "2"),
            Op("jnz", "1", "6"),
            Op("dec", "b"),
            Op("dec", "c"),
            Op("jnz", "c", "-4"),
            Op("inc", "a"),
            Op("jnz", "1", "-7"),
            Op("cpy", "2", "b"),
            Op("jnz", "c", "2"),
            Op("jnz", "1", "4"),
            Op("dec", "b"),
            Op("dec", "c"),
            Op("jnz", "1", "-4"),
            Op("jnz", "0", "0"),
            Op("out", "b"),
            Op("jnz", "a", "-19"),
            Op("jnz", "1", "-21")
    )

    var a: Int = 0
    var b: Int = 0
    var c: Int = 0
    var d: Int = 0

    val JNZ = "jnz"
    val CPY = "cpy"
    val INC = "inc"
    val DEC = "dec"

    val MAX_ITER = 1000000

    fun assembunny(list: MutableList<Op>, regAInitValue: Int): Int {
        var marker = 0
        var transmission_pattern = ""
        var iter = 0

        loop@ while (true) {
            iter++
            if (iter > MAX_ITER) {
                println("$regAInitValue produced $transmission_pattern after $iter iterations")
                break@loop
            }

            var op = list[marker]

            when (op.name) {
                "out" -> {
                    when (op.reg1) {
                        "a" -> transmission_pattern += a
                        "b" -> transmission_pattern += b
                        "c" -> transmission_pattern += c
                        "d" -> transmission_pattern += d
                        else -> transmission_pattern += op.reg1.toInt()
                    }

//                    println(transmission_pattern)
//                    if (transmission_pattern.startsWith("0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1")) {
//                    if (transmission_pattern.startsWith("0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0")) {
                    if (transmission_pattern.contains("00") || transmission_pattern.contains("11")) {
//                        println("Found it. Initializing reg a to $regAInitValue is producing $transmission_pattern")
                        println("Skipping $regAInitValue because $transmission_pattern")
                        break@loop
                    }

                    marker++
                }
                "tgl" -> {
                    var index = 0
                    when (op.reg1) {
                        "a" -> index = a
                        "b" -> index = b
                        "c" -> index = c
                        "d" -> index = d
                    }

                    var newMarker = marker + index
                    if (newMarker >= list.size) {
                        marker++
                        continue@loop
                    }

                    var nextOp = list[newMarker]

                    // two-argument instruction
                    if (!nextOp.reg2.isEmpty()) {
                        if (nextOp.name.equals(JNZ)) {
                            when (nextOp.reg2) {
                                "a", "b", "c", "d" -> nextOp.name = CPY
                                else -> {
                                    marker++
                                    continue@loop
                                }
                            }

                        }
                        else {
                            nextOp.name = JNZ
                        }

                    }
                    // one-argument instruction
                    else {
                        if (nextOp.name.equals(INC)) {
                            nextOp.name = DEC
                        }
                        else {
                            nextOp.name = INC
                        }
                    }

                    marker++
                    continue@loop
                }
                "jnz" -> {
                    var jump = when(op.reg2) {
                        "a" -> a
                        "b" -> b
                        "c" -> c
                        "d" -> d
                        else -> op.reg2.toInt()
                    }

                    when (op.reg1) {
                        "a" -> if (a != 0) marker += jump else marker++
                        "b" -> if (b != 0) marker += jump else marker++
                        "c" -> if (c != 0) marker += jump else marker++
                        "d" -> if (d != 0) marker += jump else marker++
                        "0" -> marker++
                        else -> marker += jump
                    }
                }

                "cpy" -> {
                    var register = op.reg2
                    var copyValue = when (op.reg1) {
                        "a" -> a
                        "b" -> b
                        "c" -> c
                        "d" -> d
                        else -> op.reg1.toInt()
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
                    when(op.reg1) {
                        "a" -> a++
                        "b" -> b++
                        "c" -> c++
                        "d" -> d++
                    }

                    marker++
                }

                "dec" -> {
                    when(op.reg1) {
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

        return a
    }

    override fun run() {
        println("Day25")

        for (i in 111..367) {
            a = i
            b = 0
            c = 0
            d = 0
            assembunny(input, i)
        }
    }
}