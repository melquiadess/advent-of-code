package season_2017

import AbstractDay
/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 *
 * Part 2:
 */
class Day13 : AbstractDay("Day13") {
    var list: MutableList<String> = mutableListOf()

    var firewall: MutableMap<Int, Layer> = mutableMapOf()

    data class Layer(var depth: Int, var range: Int, var scannerPosition: Int = 0) {
        var direction = 1

        var severity: Int = 0
            get() = depth * range

        fun move() {
            if (range == 0) return
            scannerPosition += direction
            //println("Scanner at position $scannerPosition for $depth")

            if (scannerPosition == range-1 || scannerPosition == 0) {
                //println("Reached end. Reversing...")
                direction *= -1
            }
        }

        override fun toString(): String {
            var output = "  $depth";

            (0 until range).forEach { it ->
                if (scannerPosition == it) {
                    output += "\n [S]"
                }
                else {
                    output += "\n [ ]"
                }
            }

            if (range == 0) output += "\n ..."

            return output+"\n"
        }
    }

    private fun getInput() {
        list = readFile("src/season_2017/input/day13-input-test")
//        list = readFile("src/season_2017/input/day13-input")
    }

    override fun part1() {
        println("PART 1")
        getInput()

        println(list)
        processInput()

        var tripSeverity = 0

        (0 until firewall.size).forEachIndexed { position, loop ->
            firewall.forEach { layer ->
                if (layer.value.scannerPosition == 0 && position == layer.value.depth) {
                    tripSeverity += layer.value.severity
                }
                layer.value.move()

                println(layer.value)
            }
        }

        println("Trip severity: $tripSeverity")
    }

    private fun processInput() {
        val layerCount = list.last().split(":")[0].trim().toInt()

        for (i in 0..layerCount) {
            firewall.put(i, Layer(i, 0, -1))
        }

        list.forEach { line ->
            val splits = line.split(":")
            val depth = splits[0].trim().toInt()
            val range = splits[1].trim().toInt()

            firewall.put(depth, Layer(depth, range, 0))
        }
    }

    override fun part2() {
        println("PART 2")

        getInput()

        println(list)
        processInput()

        var tripSeverity = 1

        var delay = 0

        while (true) {
            firewall.clear()
            processInput()
            tripSeverity = 0

            println("Delay..moving $delay times")
            for (i in 0 until delay) {
                firewall.forEach { it.value.move() }
            }

            (0 until firewall.size).forEachIndexed outer@ { position, time ->
                firewall.forEach { layer ->
                    if (layer.value.scannerPosition == 0 && position == layer.value.depth) {
                        println("Adding ${layer.value.severity} severity at position $position")
                        tripSeverity += layer.value.severity
                    }

                    layer.value.move()
//                    if (tripSeverity != 0) return@outer
//                    else {
//                        println("========= MOVED ========")
//                        println(layer.value)
//                        println("========= MOVED ========")
//                    }

                    //println(layer.value)
                }
            }

            if (tripSeverity == 0) {
                println("Trip severity: $tripSeverity")
                println("Delay: $delay")
                break
            }

            delay++
        }
    }
}