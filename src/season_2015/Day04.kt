package season_2015

import BaseDay
import java.security.MessageDigest

/**
 * Created by gregk on 26/10/2016.
 * DONE
 */
class Day04 : BaseDay {
    val secretKey = "iwrupvqb"

    override fun run() {
        println("Day04")

        println(mineCoins(secretKey, 5))
        println(mineCoins(secretKey, 6))

        println()
    }

    fun mineCoins(secret: String, length: Int) : Long {
        var hashMaker: Long = 0

        while (true) {
            var candidate = secret + hashMaker
            var hash = md5(candidate).toHexString()

            // get first x (=length) characters
            val sub = hash.substring(0..length-1)

            // create predicate, against which we are checking
            // part 1: 00000
            // part 2: 000000
            // this creates an array of length, fills it in with 0, then get's the resulting sequence
            // and joins to create a string
            val PREDICATE = IntArray(length).apply { fill(0) }.asSequence().joinToString("")

            when (sub) {
                PREDICATE -> return hashMaker
            }

            hashMaker++
        }
    }

    val digester = MessageDigest.getInstance("MD5")
    private fun md5(text: String) : ByteArray {
        return digester.digest(text.toByteArray())
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