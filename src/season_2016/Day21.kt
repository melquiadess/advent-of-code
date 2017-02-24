package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by melquiadess on 21/12/2016.
 */
class Day21 : BaseDay {
    var list: MutableList<String> = mutableListOf()

    var input = "abcdefgh"
    var input2 = "fbgdceah"
    var test_input = "abcde"

    var i = 0

    override fun run() {
        println("Day21")

        readFile()

        println(list)

        runTests()
        println("Scrambled password: ${solvePart1(input)}")
        println("Un-scrambled password: ${solvePart2(input2)}")

        println(rotateOnIndex("fbhdecga", "g"))
        println(movePosition("chbagfed", 0, 6)) // got chbagfed but probably wrong
        println("dhacegfb -> move 6,1 -> " + movePosition("dhacegfb", 6, 1)) // got dhacegfb
        println(movePosition("dhacegfb", 1, 2)) // got dhacegfb
        println(movePosition("dhgecafb", 2, 7)) // got dhgecafb
        println(movePosition("gdbfahce", 4, 5)) // got gdbfahce
        testABCDE()
    }

    fun testABCDE() {
        println("Starting: " + test_input)
        test_input = swap(test_input, 4, 0)
        println("$test_input == ebcda ? ${test_input.equals("ebcda")}")

        test_input = swapLetter(test_input, "d", "b")
        println("$test_input == edcba ? ${test_input.equals("edcba")}")

        test_input = reversePositions(test_input, 0, 4)
        println("$test_input == abcde ? ${test_input.equals("abcde")}")

        test_input = rotateLeft(test_input, 1)
        println("$test_input == bcdea ? ${test_input.equals("bcdea")}")

        test_input = movePosition(test_input, 1, 4)
        println("$test_input == bdeac ? ${test_input.equals("bdeac")}")

        test_input = movePosition(test_input, 3, 0)
        println("$test_input == abdec ? ${test_input.equals("abdec")}")

        test_input = rotateOnIndex(test_input, "b")
        println("$test_input == ecabd ? ${test_input.equals("ecabd")}")

        test_input = rotateOnIndex(test_input, "d")
        println("$test_input == decab ? ${test_input.equals("decab")}")

        println(test_input.equals("decab"))
    }

    private fun runTests() {
        testSwap()
        testSwapLetter()
        testRotateRight()
        testRotateLeft()
        testRotateOnIndex()
        testReversePositions()
        testMovePositions()
    }

    /**
     *  swap position 2 with position 7
     *
        swap letter c with letter a

        rotate based on position of letter g

        rotate right 6 steps

        rotate left 1 step

        reverse positions 4 through 7

        move position 0 to position 6
        move position 6 to position 0
     */
    fun solvePart1(str: String): String {
        var pass = str

        for (line in list) {
            println("${i++} pass: $pass --> $line")

            if (pass.length != 8) {
                println("Password SHORT!")
                break
            }

            var s = line.split(" ")

            when (s[0]) {
                "swap" -> {
                    when (s[1]) {
                        "position" -> pass = swap(pass, s[2].toInt(), s[5].toInt())
                        "letter" -> pass = swapLetter(pass, s[2], s[5])
                    }
                }

                "rotate" -> {
                    when (s[1]) {
                        "based" -> {
                            pass = rotateOnIndex(pass, s[6])
                        }

                        "right" -> {
                            pass = rotateRight(pass, s[2].toInt())
                        }

                        "left" -> {
                            pass = rotateLeft(pass, s[2].toInt())
                        }
                    }
                }

                "reverse" -> {
                    pass = reversePositions(pass, s[2].toInt(), s[4].toInt())
                }

                "move" -> {
                    pass = movePosition(pass, s[2].toInt(), s[5].toInt())
                }
            }
        }

        return pass
    }

    fun solvePart2(str: String): String {
        var pass = str

        i=0
        for (line in list.reversed()) {
            println("${i++} pass: $pass --> $line")

            if (pass.length != 8) {
                println("Password SHORT!")
                break
            }

            var s = line.split(" ")

            when (s[0]) {
                "swap" -> {
                    when (s[1]) {
                        "position" -> pass = swap(pass, s[2].toInt(), s[5].toInt())
                        "letter" -> pass = swapLetter(pass, s[2], s[5])
                    }
                }

                "rotate" -> {
                    when (s[1]) {
                        "based" -> {
                            pass = rotateOnIndexReverse(pass, s[6])
                        }

                        "right" -> {
                            pass = rotateLeft(pass, s[2].toInt())
                        }

                        "left" -> {
                            pass = rotateRight(pass, s[2].toInt())
                        }
                    }
                }

                "reverse" -> {
                    pass = reversePositions(pass, s[2].toInt(), s[4].toInt())
                }

                "move" -> {
                    pass = movePosition(pass, s[5].toInt(), s[2].toInt())
                }
            }
        }

        return pass
    }

    fun swap(str: String, x: Int, y: Int): String {
        var xx = x
        var yy = y

        if (x > y) {
            xx = y
            yy = x
        }

        var first = str[xx]
        var second = str[yy]

        var p1 = str.substring(0, xx)
        var p2 = str.substring(xx+1, yy)
        var remaining = str.substring(yy+1)
        val s = p1 + second + p2 + first + remaining

        return s
    }

    fun swapLetter(str: String, x: String, y: String) = str.replace(x, "*").replace(y, x).replace("*", y)

    fun rotateLeft(str: String, x: Int): String {
        val right = str.substring(x)
        val left = str.substring(0, x)
        return right + left
    }

    fun rotateRight(str: String, x: Int): String {
        val left = str.substring(0, str.length - x)
        val right = str.substring(str.length-x)
        return right + left
    }

    fun rotateOnIndex(str: String, x: String): String {
        var s = str
        var index = str.indexOf(x)

        var count = 1 + index + (if (index >= 4) 1 else 0)
        for (i in 0..count-1) {
            s = rotateRight(s, 1)
        }

        return s
    }

    fun rotateOnIndexReverse(str: String, x: String): String {
        var s = str
        var index = str.indexOf(x)

        var count = index / 2 + (if (index % 2 == 1 || index == 0) 1 else 5)
        for (i in 0..count-1) {
            s = rotateLeft(s, 1)
        }

        return s
    }

    fun reversePositions(str: String, x: Int, y:Int): String {
        var xx = x
        var yy = y

        if (xx > yy) {
            xx = y
            yy = x
        }

        val left = str.substring(0, xx)
        val reversed = str.substring(xx, yy+1).reversed()
        val right = str.substring(yy+1)

        val s = left + reversed + right

        return s
    }

    fun movePosition(str: String, x: Int, y:Int): String {
        if (x < y) {
            val left = str.substring(0, x)
            val charX = str[x]
            val afterX = str.substring(x+1, y)
            val charY = str[y]
            val right = str.substring(y+1)

            val s = left + afterX + charY + charX + right

            return s
        }
        /**
         * bdeac --> (3, 0) --> abdec
         * gdbfahce -> 4,5 -> gdbfahce
         */
        else {
            var charY = str[y]
            var charX = str[x]

            var left = str.substring(0, y)
            var right = str.substring(y+1, x)
            var remainder = str.substring(x+1)

            var s = left + charX + charY + right + remainder

            return s
        }
    }

    fun testMovePositions() {
//        assert(movePosition("abcde", 0, 1).equals("bacde"))
        assert(movePosition("abcde", 4, 0).equals("eabcd"))
    }

    fun testSwap() {
        assert(swap(test_input, 4, 0).equals("ebcda"))
    }

    fun testSwapLetter() {
        assert(swapLetter("ebcda", "d", "b").equals("edcba"))
    }

    fun testRotateRight() {
        assert(rotateRight("abcd", 1).equals("dabc"))
    }

    fun testRotateLeft() {
        assert(rotateLeft("abcde", 1).equals("bcdea"))
        assert(rotateLeft("abcde", 2).equals("cdeab"))
    }

    fun testRotateOnIndex() {
        assert(rotateOnIndex("abdec", "b").equals("ecabd"))
        assert(rotateOnIndex("ecabd", "d").equals("decab"))
    }

    fun testReversePositions() {
        assert(reversePositions("abcdef", 0, 5).equals("fedcba"))
        assert(reversePositions("abcdef", 2, 3).equals("abdcef"))

    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day21-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }
}