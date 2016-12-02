package season_2015

import BaseDay
/**
 * Created by  on 17/11/2016.
 */
class Day14 : BaseDay {
    data class Deer(
            var name: String,
            var speed: Int,
            var flyTime: Int,
            var restTime: Int,
            var distance: Int = 0,
            var points: Int = 0,
            var flewSoFar: Int = 0,
            var restedSoFar: Int = 0,
            var isFlying: Boolean = true)

    var deers: MutableList<Deer> = mutableListOf()

    override fun run() {
        println("Day14")

        initializeDeers(INPUT)

//        println(deers)

//        simulate(2503)
        simulateWithPoints(2503)

        var bestDistance = deers.maxBy { it.distance }?.distance
        println("Best distance is ${bestDistance}")

        var bestPoints = deers.maxBy { it.distance }?.points
        println("Best points: ${bestPoints}")

        println()
    }

    fun simulate(time: Int) {
        for (deer in deers) {
            var timeLeft = time

            while (timeLeft > 0) {
                var flyTime = deer.flyTime
                if (timeLeft < flyTime) {
                    flyTime = timeLeft
                }
                deer.distance += deer.speed * flyTime
                timeLeft -= flyTime

                timeLeft -= deer.restTime
            }
        }
    }

    fun simulateWithPoints(time: Int) {
        for (i in 1..time) {
            for (deer in deers) {

                if (deer.isFlying) {
                    if (deer.flewSoFar < deer.flyTime) {
                        deer.flewSoFar++
                        deer.distance += deer.speed
                    }
                    else {
                        deer.isFlying = false
                        deer.restedSoFar = 1
                    }
                }
                else {
                    if (deer.restedSoFar <= deer.restTime) {
                        deer.restedSoFar++
                    }
                    else {
                        deer.isFlying = true
                        deer.flewSoFar = 1
                    }
                }
            }

            var bestDistance = deers.maxBy { it.distance }?.distance
            var bestDeers = deers.filter { it.distance == bestDistance }

            for (d in bestDeers) {
                d.points++
            }
//            println("$i: $deers")
        }
    }

    fun initializeDeers(input: List<String>) {
        for (line in input) {
            var split = line.split(" ")

            deers.add(Deer(split[0], split[3].toInt(), split[6].toInt(), split[13].toInt()))
        }
    }

    val INPUT = arrayListOf(
        "Dancer can fly 27 km/s for 5 seconds, but then must rest for 132 seconds.",
        "Cupid can fly 22 km/s for 2 seconds, but then must rest for 41 seconds.",
        "Rudolph can fly 11 km/s for 5 seconds, but then must rest for 48 seconds.",
        "Donner can fly 28 km/s for 5 seconds, but then must rest for 134 seconds.",
        "Dasher can fly 4 km/s for 16 seconds, but then must rest for 55 seconds.",
        "Blitzen can fly 14 km/s for 3 seconds, but then must rest for 38 seconds.",
        "Prancer can fly 3 km/s for 21 seconds, but then must rest for 40 seconds.",
        "Comet can fly 18 km/s for 6 seconds, but then must rest for 103 seconds.",
        "Vixen can fly 18 km/s for 5 seconds, but then must rest for 84 seconds.")
}