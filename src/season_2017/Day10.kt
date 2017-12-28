package season_2017

import AbstractDay

/**
 * Created by melquiadess on 10/12/2017.
 * Part 1:
 *
 * Part 2:
 *  be4875cf938b02bfc5b8efe490be54e5 - wrong
 *  7410e88f464d6878198bc981190d5583 - wrong
 *  9ce36483f091fe3ff61986e823a19fa4 - wrong
 *  25d9678afce156a55e2554b3b3ffac83 - wrong
 *  a9d0e68649d0174c8756a59ba21d4dc6 - correct!
 */
class Day10 : AbstractDay("Day10") {
    var lengthsAsInts: MutableList<Int> = mutableListOf(199,0,255,136,174,254,227,16,51,85,1,2,22,17,7,192)
    var lengthsAsString = "199,0,255,136,174,254,227,16,51,85,1,2,22,17,7,192"
    var list = IntArray(256, { it })

    var currentPosition = 0
    var skipSize = 0

    override fun part1() {
        println("PART 1")

        println(lengthsAsInts)

        lengthsAsInts.forEach { length ->
            print("Length $length array: ")
            printArray(list)
            println("pos: $currentPosition")

            // 1. Reverse the order of that length of elements in the list, starting with the element at the current position.
            reverseList(length)

            // 2. Move the current position forward by that length plus the skip size.
            currentPosition += (length + skipSize)
//            println("pos: $currentPosition was increased by ${length + skipSize}")
//
            if (currentPosition >= list.size) {
                println("Oops, too long. Wrapping...")
                while (currentPosition >= list.size) {
                    currentPosition -= list.size
                }
            }
            println("pos: $currentPosition")

            // 3. Increase the skip size by one.
            skipSize++
        }

        println("Answer: ${list[0] * list[1]}")
    }

    fun reverseList(length: Int) {
        if (length == 1 || length == 0 || length > list.size) return

        var reversed = IntArray(length, { it })

        var posEnd = currentPosition + length
        while (posEnd >= list.size) {
            posEnd -= list.size
        }
        var index = 0

        if (posEnd > currentPosition) {
            // normal slice of array
            for (i in currentPosition until posEnd) {
                reversed[index++] = list[i]
            }
            reversed.reverse()

            index = 0
            for (i in currentPosition until posEnd) {
                list[i] = reversed[index++]
            }

//            print("REVERSED (X): ")
//            printArray(list)
        } else {
            // we need to get some elements from the beginning
            for (i in currentPosition until list.size) {
                reversed[index++] = list[i]
            }

            for (i in 0 until posEnd) {
                reversed[index++] = list[i]
            }

//            printArray(reversed)
            reversed.reverse()
//            printArray(reversed)

            index = 0
            for (i in currentPosition until list.size) {
                list[i] = reversed[index++]
            }

            for (i in 0 until posEnd) {
                list[i] = reversed[index++]
            }

//            print("REVERSED: ")
//            printArray(list)
        }
    }

    fun printArray(array: IntArray) {
        array.forEach { print("$it, ") }
        println()
    }

    private fun getSequence(input: String): MutableList<Int> {
        var suffix = mutableListOf(17, 31, 73, 47, 23)

        var ascii = getBytesFromString(input)

        ascii.addAll(suffix)

        return ascii
    }

    private fun getBytesFromString(input: String): MutableList<Int> {
        var ascii = mutableListOf<Int>()

        input.forEach { c ->
            ascii.add(c.toByte().toInt())
        }

        return ascii
    }

    override fun part2() {
        println("PART 2")

        currentPosition = 0
        skipSize = 0
        list = IntArray(256, { it })

        var lengths = getSequence(lengthsAsString)

        println("1,2,3 = ${getBytesFromString("1,2,3")}")
        println("1,2,3 augmented = ${getSequence("1,2,3")}")

        for (loop in 0..63) {
            println("loop $loop")
            lengths.forEach { length ->
                //print("Length $length array: ")
                //printArray(list)
                //println("pos: $currentPosition")

                // 1. Reverse the order of that length of elements in the list, starting with the element at the current position.
                reverseList(length)

                // 2. Move the current position forward by that length plus the skip size.
                currentPosition += (length + skipSize)
                //println("pos: $currentPosition was increased by ${length + skipSize}")

                if (currentPosition >= list.size) {
                    println("Oops, too long. Wrapping...")
                    while (currentPosition >= list.size) {
                        currentPosition -= list.size
                    }
                }
                //println("pos: $currentPosition")

                // 3. Increase the skip size by one.
                skipSize++
            }
        }

        println("\nSparse hash:")
        printArray(list)

        var denseHashList = mutableListOf<Int>()

        var xors = list

        var hash: Int
        //dense hash
        for (i in 0 until xors.size step 16) {
            hash = xors[i]
                    .xor(xors[i + 1])
                    .xor(xors[i + 2])
                    .xor(xors[i + 3])
                    .xor(xors[i + 4])
                    .xor(xors[i + 5])
                    .xor(xors[i + 6])
                    .xor(xors[i + 7])
                    .xor(xors[i + 8])
                    .xor(xors[i + 9])
                    .xor(xors[i + 10])
                    .xor(xors[i + 11])
                    .xor(xors[i + 12])
                    .xor(xors[i + 13])
                    .xor(xors[i + 14])
                    .xor(xors[i + 15])

            denseHashList.add(hash)
            println("${i/16} hash: $hash")
        }

        denseHashList.forEach { hash ->
            print(hash.toByte().toHexString())
        }
    }

    /**
     *  Set of chars for a half-byte.
     */
    private val CHARS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     *  Returns the string of two characters representing the HEX value of the byte.
     */
    private fun Byte.toHexString() : String {
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