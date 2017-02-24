package season_2016

import BaseDay
import java.security.MessageDigest

/**
 * Created by melquiadess on 12/12/2016.
 */
class Day14 : BaseDay {
    var SALT = "ahsbgdzn"
    var TEST_SALT = "abc"

    var NEEDED_KEYS_COUNT = 64

    override fun run() {
        println("Day14")

        // TESTS
        // for (i in 0..2016) println(i.toString() + ": " +getHashIter("abc0", i))
        // println(getHashIter("abc0", 0))
        // println(getHashIter("abc0", 1))
        // println(getHashIter("abc0", 2))
        // println(getHashIter("abc0", 2016))
        // println(getHashIter("abc0", 2016))

        // var c = getCharacter3InRow("cc38887a5")

        // findIndexFor64thKey()

        // println(c)

        // println()
        println(findIndexFor64thKey(SALT, 2016))
    }

    fun findIndexFor64thKey(salt: String, iterations: Int): Int {
        var keyCount = 0
        var index=0

        var hash = ""
        var value: String

        var secondaryHash = ""
        var secondaryValue: String

        var cache: MutableMap<Int, String> = mutableMapOf()

        while(true) {
            value = salt + index
            hash = getHashIter(value, iterations)

            var c3 = getCharacter3InRow(hash)

            if (c3 != null) {
                for (secondaryIndex in index+1..index+1000) {
                    secondaryValue = salt + secondaryIndex

                    if (cache.isNotEmpty() && cache.containsKey(secondaryIndex)) {
                        secondaryHash = cache.get(secondaryIndex)!!
                    }
                    else {
                        secondaryHash = getHashIter(secondaryValue, iterations)
                        cache.put(secondaryIndex, secondaryHash)
                    }

                    var c5 = getCharacter5InRow(secondaryHash)

                    if (c5 != null && c3.equals(c5)) {
                        keyCount++
                        println("Index: $index has hash: $hash ($c3) secondaryIndex: $secondaryIndex has hash: $secondaryHash ($c5) [$keyCount]")
                    }
                }
            }

            if (keyCount == NEEDED_KEYS_COUNT) return index
            index++
        }
    }

    fun getHashIter(str: String, iter: Int): String {
        var hash = md5(str).toHexString()

        if (iter > 0) {
            for (it in 0..iter-1) {
                hash = md5(hash).toHexString()
            }
        }

        return hash
    }

    fun getCharacter3InRow(str: String): Char? {
        var reg = """(.)\1{2}""".toRegex()

        var tripletss = reg.findAll(str)

        for (matched in tripletss) {
            for (gr in matched.groups) {
                return gr!!.value[0]
            }
        }

        return null
    }

    fun getCharacter5InRow(str: String): Char? {
        var reg = """(.)\1{4}""".toRegex()

        var tripletss = reg.findAll(str)
        for (matched in tripletss) {
            for (gr in matched.groups) {
                return gr!!.value[0]
            }
        }

        return null
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