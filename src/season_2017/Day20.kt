package season_2017

import AbstractDay

/**
 * Created by melquiadess on 20/12/2017.
 * Part 1:
 *  860 - too high
 *  39 - wrong
 * Part 2:
 */
class Day20 : AbstractDay("Day20") {
    var list: MutableList<String> = mutableListOf()

    private val particles: MutableList<Particle> = mutableListOf()

    data class Particle(
            var id: Int,
            var position: Triple<Long, Long, Long>,
            private var velocity: Triple<Int, Int, Int>,
            private var acceleration: Triple<Int, Int, Int>) {

        fun update() {
            velocity = Triple(velocity.first + acceleration.first,velocity.second + acceleration.second,velocity.third + acceleration.third)
            position = Triple(position.first + velocity.first,position.second + velocity.second,position.third + velocity.third)
        }

        var distanceFromOrigin: Long = 0
            get() = getManhattanDistance(position)

        private fun getManhattanDistance(pos: Triple<Long, Long, Long>): Long = Math.abs(pos.first) + Math.abs(pos.second) + Math.abs(pos.third)
    }

    private fun getInput() {
        list = readFile("src/season_2017/input/day20-input")
        //list = readFile("src/season_2017/input/day20-input-test")

        list.forEachIndexed { index, line ->
            val splits = line.split(", ")
            val pos = splits[0].split(",")
            val vel = splits[1].split(",")
            val acc = splits[2].split(",")

            val p = Triple(
                    pos[0].removePrefix("p=<").trim().toLong(),
                    pos[1].trim().toLong(),
                    pos[2].trim().removeSuffix(">").toLong()
                    )

            val v = Triple(
                    vel[0].removePrefix("v=<").trim().toInt(),
                    vel[1].trim().toInt(),
                    vel[2].trim().removeSuffix(">").toInt()
            )

            val a = Triple(
                    acc[0].removePrefix("a=<").trim().toInt(),
                    acc[1].trim().toInt(),
                    acc[2].trim().removeSuffix(">").toInt()
            )

            particles.add(Particle(index, p, v, a))
        }

        println(particles)
    }

    override fun part1() {
        println("PART 1")
        getInput()

        for (i in 0..1_000_00 ) {
            if (i.rem(100000) == 0) println(i) // just to see the program is doing something
            particles.forEach { it.update() }
        }

        println(particles)
        var closest = particles.minBy { Math.abs(it.distanceFromOrigin) }
        println(closest)
    }

    override fun part2() {
        println("PART 2")
    }
}