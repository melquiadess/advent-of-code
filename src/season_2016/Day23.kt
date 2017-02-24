package season_2016

import BaseDay
/**
 * Created by melquiadess on 23/12/2016.
 *
 */
class Day23 : BaseDay {
    var list: MutableList<String> = mutableListOf(
            "cpy a b",
            "dec b",
            "cpy a d",
            "cpy 0 a",
            "cpy b c",
            "inc a",
            "dec c",
            "jnz c -2",
            "dec d",
            "jnz d -5",
            "dec b",
            "cpy b c",
            "cpy c d",
            "dec d",
            "inc c",
            "jnz d -2",
            "tgl c",
            "cpy -16 c",
            "jnz 1 c",
            "cpy 94 c",
            "jnz 80 d",
            "inc a",
            "inc d",
            "jnz d -2",
            "inc c",
            "jnz c -5"
    )

    var testInput = mutableListOf(
            Op("cpy", "2", "a"),
            Op("tgl", "a"),
            Op("tgl", "a"),
            Op("tgl", "a"),
            Op("cpy", "1", "a"),
            Op("dec", "a"),
            Op("dec", "a")
    )

    data class Op(var name: String, var reg1: String = "", var reg2: String = "")

    var input = mutableListOf(
            Op("cpy", "a", "b"),
            Op("dec", "b"),
            Op("cpy", "a", "d"),
            Op("cpy", "0", "a"),
            Op("cpy", "b", "c"),
            Op("inc", "a"),
            Op("dec", "c"),
            Op("jnz", "c", "-2"),
            Op("dec", "d"),
            Op("jnz", "d", "-5"),
            Op("dec", "b"),
            Op("cpy", "b", "c"),
            Op("cpy", "c", "d"),
            Op("dec", "d"),
            Op("inc", "c"),
            Op("jnz", "d", "-2"),
            Op("tgl", "c"),
            Op("cpy", "-16", "c"),
            Op("jnz", "1", "c"),
            Op("cpy", "94", "c"),
            Op("jnz", "80", "d"),
            Op("inc", "a"),
            Op("inc", "d"),
            Op("jnz", "d", "-2"),
            Op("inc", "c"),
            Op("jnz", "c", "-5")
    )

    var a: Int = 12
    var b: Int = 0
    var c: Int = 0
    var d: Int = 0

    val JNZ = "jnz"
    val CPY = "cpy"
    val INC = "inc"
    val DEC = "dec"

    fun assembunny(list: MutableList<Op>): Int {
        var marker = 0

        loop@ while (true) {
            var op = list[marker]

            when (op.name) {
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
    }

    override fun run() {
        println("Day22")

        println("Register a: ${assembunny(input)}")

    }
}
