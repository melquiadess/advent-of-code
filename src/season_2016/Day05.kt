package season_2016

import BaseDay
import Stopwatch
import java.security.MessageDigest

/**
 * Created by melquiadess on 05/12/2016.
 */
class Day05 : BaseDay {
    val INPUT = "ugkcyxxp"
    val TEST_INPUT = "abc"

    override fun run() {
        println("Day05")

        println("First password...")
        findPassword(INPUT)
        println("\nSecond password...")
        Stopwatch.startTimer()
        findPasswordExtra(INPUT)
        println("\nDone in ${Stopwatch.getElapsedSeconds()} seconds")

        println()
    }

    fun findPassword(str: String): String {
        var i=0

        var password = mutableListOf("-", "-", "-", "-", "-", "-", "-", "-")
        var passToShow: StringBuilder = StringBuilder()

        var pos = 0
        while (password.any { it.equals("-") }) {

            var hex = md5(str + i).toHexString()
            if (hex.take(5).equals("00000")) {
                password[pos++] = hex[5].toString()
                print("\r${password.toList().toString().replace(", ", "")}")
            }

            if (i%10000==0) {
                password.forEach { c -> passToShow.append(if (c.equals("-")) (122 - (Math.random() * 25).toInt()).toChar().toString() else c ) }
                print("\r${passToShow.toList().toString().replace(", ", "")}")
                passToShow.setLength(0)
            }

            i++
        }

        return password.toString()
    }

    fun findPasswordExtra(str: String): String {
        var i=0

        var password = mutableListOf("-", "-", "-", "-", "-", "-", "-", "-")
        var passToShow: StringBuilder = StringBuilder()

        while (password.any { it.equals("-") }) {
            var hex = md5(str + i).toHexString()
            if (hex.take(5).equals("00000")) {
                val position = hex[5]

                when (position) {
                    in '0'..'7' -> {
                        val digit = hex[6].toString()
                        val current = password[position.toString().toInt()]

                        if (current.equals("-")) {
                            password[position.toString().toInt()] = digit
                        }
                    }
                }
                print("\r${password.toList().toString().replace(", ", "")}")
            }
            if (i%1000==0) {
                password.forEach { c -> passToShow.append(if (c.equals("-")) (122 - (Math.random() * 25).toInt()).toChar().toString() else c ) }
                print("\r${passToShow.toList().toString().replace(", ", "")}")
                passToShow.setLength(0)
            }

            i++
        }

        return password.toList().toString()
    }

    val digester: MessageDigest? = MessageDigest.getInstance("MD5")
    private fun md5(text: String) : ByteArray {
        return digester?.digest(text.toByteArray()) as ByteArray
    }

    /**
     *  Set of chars for a half-byte.
     */
    private val CHARS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     *  Returns the string of two characters representing the HEX value of the byte.
     */
    internal fun Byte.toHexString() : String {
        val i = this.toInt()
        val char2 = CHARS[i and 0x0f]
        val char1 = CHARS[i shr 4 and 0x0f]
        return "$char1$char2"
    }

    /**
     *  Returns the HEX representation of ByteArray data.
     */
    internal fun ByteArray.toHexString() : String {
        val builder = StringBuilder()
        for (b in this) {
            builder.append(b.toHexString())
        }
        return builder.toString()
    }
}