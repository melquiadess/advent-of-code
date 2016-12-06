package season_2016

import BaseDay

/**
 * Created by melquiadess on 02/12/2016.
 *
 * 1295324847393395: too high
 * Actually, the above contains the correct answer, I was first reading the moves from a file
 * and treated each line as new set of moves, 16 lines -> 16 digit code, but there are actually 5 of them
 * so the last digit for each group combined together was the code: 129 532 484 7393 395
 *
 */
class Day02 : BaseDay {
    var instructions: List<String> = mutableListOf(
        "RDLRUUULRRDLRLLRLDDUDLULULDDULUDRRUURLRLLUULDURRULLRULDRRDLLULLRLLDRLDDRRRRLLRLURRRDRDULRDUULDDDULURUDDRRRUULUDRLLUUURLUDRUUUDRDUULLRLLUDDRURRDDDRDLUUURLRLLUDRURDUDUULDDLLRDURULLLURLDURLUUULDULDDULULLLRRUDLRUURDRDLLURLUDULDUUUURRLDLUDRULUDLDLLDRLDDDRRLLDUDLLRRDDDRLUDURLLLDRUDDLDDRRLUDRRDUDLRRLULDULURULDULUULDRLLDRUUDDRLLUDRULLRRRLRDLRLUDLRULDRDLRDRLRULUDUURRUUULLDDDDUDDLDDDDRRULRDLRDDULLDLDLLDLLDLLDRRDDDRDDLRRDDDRLLLLURRDLRRLDRURDDURDULDDRUURUDUDDDRDRDDRLRRLRULLDRLDLURLRLRUDURRRDLLLUDRLRDLLDDDLLUDRLDRRUUDUUDULDULLRDLUDUURLDDRUDR",
        "URULDDLDDUDLLURLUUUUUULUDRRRDDUDURDRUURLLDRURLUULUDRDRLLDRLDULRULUURUURRLRRDRUUUDLLLLRUDDLRDLLDUDLLRRURURRRUDLRLRLLRULRLRLRDLRLLRRUDDRLRUDULDURDLDLLLRDRURURRULLLDLLRRDRLLDUUDLRUUDDURLLLDUUDLRDDURRDRRULLDRLRDULRRLLRLLLLUDDDRDRULRRULLRRUUDULRRRUDLLUUURDUDLLLURRDDUDLDLRLURDDRRRULRRUDRDRDULURULRUDULRRRLRUDLDDDDRUULURDRRDUDLULLRUDDRRRLUDLRURUURDLDURRDUUULUURRDULLURLRUUUUULULLDRURULDURDDRRUDLRLRRLLLLDDUURRULLURURRLLDRRDDUUDLLUURRDRLLLLRLUDUUUDLRLRRLDURDRURLRLRULURLDULLLRRUUUDLLRRDUUULULDLLDLRRRDUDDLRULLULLULLULRU",
        "URUUDULRRLULLLDDUDDLRRDURURRRDDRRURDRURDRLULDUDUDUULULDDUURDDULRDUDUDRRURDRDDRLDRDRLDULDDULRULLDULURLUUDUDULRDDRRLURLLRRDLLDLDURULUDUDULDRLLRRRUDRRDDDRRDRUUURLDLURDLRLLDUULLRULLDDDDRULRRLRDLDLRLUURUUULRDUURURLRUDRDDDRRLLRLLDLRULUULULRUDLUDULDLRDDDDDRURDRLRDULRRULRDURDDRRUDRUDLUDLDLRUDLDDRUUULULUULUUUDUULDRRLDUDRRDDLRUULURLRLULRURDDLLULLURLUDLULRLRRDDDDDRLURURURDRURRLLLLURLDDURLLURDULURUUDLURUURDLUUULLLLLRRDUDLLDLUUDURRRURRUUUDRULDDLURUDDRRRDRDULURURLLDULLRDDDRRLLRRRDRLUDURRDLLLLDDDDLUUURDDDDDDLURRURLLLUURRUDLRLRRRURULDRRLULD",
        "LLUUURRDUUDRRLDLRUDUDRLRDLLRDLLDRUULLURLRRLLUDRLDDDLLLRRRUDULDLLLDRLURDRLRRLURUDULLRULLLURRRRRDDDLULURUDLDUDULRRLUDDURRLULRRRDDUULRURRUULUURDRLLLDDRDDLRRULRDRDRLRURULDULRRDRLDRLLDRDURUUULDLLLRDRRRLRDLLUDRDRLURUURDLRDURRLUDRUDLURDRURLRDLULDURDDURUUDRLULLRLRLDDUDLLUUUURLRLRDRLRRRURLRULDULLLLDLRRRULLUUDLDURUUUDLULULRUDDLLDLDLRLDDUDURDRLLRRLRRDDUDRRRURDLRLUUURDULDLURULUDULRRLDUDLDDDUUDRDUULLDDRLRLLRLLLLURDDRURLDDULLULURLRDUDRDDURLLLUDLLLLLUDRDRDLURRDLUDDLDLLDDLUDRRDDLULRUURDRULDDDLLRLDRULURLRURRDDDRLUUDUDRLRRUDDLRDLDULULDDUDURRRURULRDDDUUDULLULDDRDUDRRDRDRDLRRDURURRRRURULLLRRLR",
        "URLULLLDRDDULRRLRLUULDRUUULDRRLLDDDLDUULLDRLULRRDRRDDDRRDLRRLLDDRDULLRRLLUDUDDLDRDRLRDLRDRDDUUDRLLRLULLULRDRDDLDDDRLURRLRRDLUDLDDDLRDLDLLULDDRRDRRRULRUUDUULDLRRURRLLDRDRRDDDURUDRURLUDDDDDDLLRLURULURUURDDUDRLDRDRLUUUULURRRRDRDULRDDDDRDLLULRURLLRDULLUUDULULLLLRDRLLRRRLLRUDUUUULDDRULUDDDRRRULUDURRLLDURRDULUDRUDDRUURURURLRDULURDDDLURRDLDDLRUDUUDULLURURDLDURRDRDDDLRRDLLULUDDDRDLDRDRRDRURRDUDRUURLRDDUUDLURRLDRRDLUDRDLURUDLLRRDUURDUDLUDRRL"
    )

    override fun run() {
        println("Day02")

        var code: String = ""

        var start = 5
        for (line in instructions) {
            start = moveEx(line, start)
            code +=
                when(start) {
                    10 -> "A"
                    11 -> "B"
                    12 -> "C"
                    13 -> "D"
                    else -> start.toString()
                }
        }

        println(code)

        testEdgeCases()
    }

    fun move(directions: String, startingDigit: Int): Int {
        var currentDigit = startingDigit

        var currentDir: String = ""

        for (c in directions) {
            currentDir += c
            when (c) {
                'R' -> {
                    when (currentDigit) {
                        in 1..2 -> currentDigit++
                        in 4..5 -> currentDigit++
                        in 7..8 -> currentDigit++
                    }
                }

                'L' -> {
                    when (currentDigit) {
                        in 2..3 -> currentDigit--
                        in 5..6 -> currentDigit--
                        in 8..9 -> currentDigit--
                    }
                }

                'U' -> {
                    when (currentDigit) {
                        in 4..6 -> currentDigit -= 3
                        in 7..9 -> currentDigit -= 3
                    }
                }

                'D' -> {
                    when (currentDigit) {
                        in 1..3 -> currentDigit += 3
                        in 4..6 -> currentDigit += 3
                    }
                }
            }
//            print(" -> $c = $currentDigit ")
        }

        return currentDigit
    }

    /**
     *      1
     *    2 3 4
     *  5 6 7 8 9
     *    A B C
     *      D
     */
    fun moveEx(directions: String, startingDigit: Int): Int {
        var currentDigit = startingDigit

        var currentDir: String = ""

        for (c in directions) {
            currentDir += c
            when (c) {
                'R' -> {
                    when (currentDigit) {
                        in 2..3 -> currentDigit++
                        in 5..8 -> currentDigit++
                        in 10..11 -> currentDigit++
                    }
                }

                'L' -> {
                    when (currentDigit) {
                        in 3..4 -> currentDigit--
                        in 6..9 -> currentDigit--
                        in 11..12 -> currentDigit--
                    }
                }

                'U' -> {
                    when (currentDigit) {
                        3 -> currentDigit -= 2
                        in 6..8 -> currentDigit -= 4
                        in 10..12 -> currentDigit -= 4
                        13 -> currentDigit -= 2
                    }
                }

                'D' -> {
                    when (currentDigit) {
                        1 -> currentDigit += 2
                        in 2..4 -> currentDigit += 4
                        in 6..8 -> currentDigit += 4
                        11 -> currentDigit += 2
                    }
                }
            }
        }

        return currentDigit
    }

    fun testEdgeCases() {
        println("TESTS")
        assert(move("RRRRR", 5) == 6)
        assert(move("LLLLL", 5) == 4)
        assert(move("DDUUDDUU", 1) == 1)
        assert(move("RRRRRRRRRRRRRRRR", 9) == 9)
        assert(move("RRDDLLUU", 1) == 1)
        assert(move("RRDDLLUU", 9) == 1)
    }
}