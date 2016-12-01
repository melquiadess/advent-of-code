package season_2015

import java.util.*

/**
 * Created by gregk on 18/11/2016.
 */
class Day15 : BaseDay {
    data class Ingredient(var name: String, var capacity: Int, var durability: Int, var flavor: Int, var texture: Int, var calories: Int)

    var list = mutableListOf<Ingredient>()

    override fun run() {
        println("Day14")

        processInput(INPUT)
        findBest()


        println()
    }

    fun findBest() {
        var best: Long = 0L
        var currentBest = 0L

        for (capacity in 1..100)
            for (durability in 1..100)
                for (falvor in 1..100)
                    for (texture in 1..100) {
                        if (capacity + durability + falvor + texture != 100) continue

                        var a: IntArray = IntArray(4)

                        for (ing in list) {
                            a[0] += ing.capacity * capacity
                            a[1] += ing.durability * durability
                            a[2] += ing.flavor * falvor
                            a[3] += ing.texture * texture
                        }

                        if (a[0] < 0) a[0] = 0
                        if (a[1] < 0) a[1] = 0
                        if (a[2] < 0) a[2] = 0
                        if (a[3] < 0) a[3] = 0

                        currentBest = a[0].toLong() * a[1].toLong() * a[2].toLong() * a[3].toLong()

                        if (currentBest > best) {
                            best = currentBest
                        }
                    }
        println("Best: $best")
    }

    fun processInput(input: List<String>) {
        var str: String
        for (s in input) {
            str = s.replace(",", "")
            var split = str.split(" ")

            list.add(Ingredient(
                split[0],
                split[2].toInt(),
                split[4].toInt(),
                split[6].toInt(),
                split[8].toInt(),
                split[10].toInt()
                )
            )
        }
    }

    var INPUT = listOf(
        "Sprinkles: capacity 2, durability 0, flavor -2, texture 0, calories 3",
        "Butterscotch: capacity 0, durability 5, flavor -3, texture 0, calories 3",
        "Chocolate: capacity 0, durability 0, flavor 5, texture -1, calories 8",
        "Candy: capacity 0, durability -1, flavor 0, texture 5, calories 8"
    )
}
