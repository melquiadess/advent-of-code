package season_2017

import AbstractDay
import com.marcinmoskala.math.powerset

/**
 * Created by melquiadess on 24/12/2017.
 * Part 1:
 * Part 2:
 */
class Day24 : AbstractDay("Day24") {
    var list: MutableList<String> = mutableListOf()

    data class Component(val port1: Int, val port2: Int)

    var components = mutableSetOf<Component>()

    private fun getInput() {
        list = readFile("src/season_2017/input/day24-input-test")
//        list = readFile("src/season_2017/input/day24-input")

        list.forEach { line ->
            var splits = line.split("/")
            components.add(Component(splits[0].trim().toInt(), splits[1].trim().toInt()))
        }
    }

    override fun part1() {
        println("PART 1")
        getInput()

        var set = components.powerset()

        var filtered = set.filter { isValid(it) }
        //printPermutations(filtered)

        var maxSum = Int.MIN_VALUE
        filtered.forEach { miniset ->
            println(miniset)
            var sum = getSum(miniset)
            if (sum > maxSum) {
                maxSum = sum
                println("Max sum: $maxSum for $miniset")
            }
        }
    }

    override fun part2() {
        println()
        println("PART 2")

//        getInput()
//
//        println(list)
    }

    private fun printPermutations(list: List<Set<Component>>) {
        var i=1
        for (p in list) {
            println("${i++} -> $p")
        }
    }

    private fun getSum(set: Set<Component>) = set.sumBy { it.port1 + it.port2 }

    private fun isValid(set: Set<Component>): Boolean {
        if (set.contains(Component(0, 1)) || set.contains(Component(0, 2))) {

            var numbers = mutableMapOf<Int, Int>()

            set.forEach { component ->
                var c: Int? = 1
                if (numbers.contains(component.port1)) {
                    c = numbers.get(component.port1)
                    numbers.remove(c)
                    numbers.put(component.port1, c!!+1)
                }
                else {
                    numbers.put(component.port1, c!!)
                }

                if (numbers.contains(component.port2)) {
                    c = numbers.get(component.port2)
//                    if (c != component.port1) {
                        numbers.remove(c)
                        numbers.put(component.port2, c!!+1)
//                    }
                }
                else {
                    numbers.put(component.port2, c!!)
                }
            }

            if (set.size == 1) return true
            if (!numbers.contains(0)) return false
            if (numbers.contains(0) && numbers.get(0) != 1) return false
            if (numbers.contains(1) && numbers.get(1) == 1) return false

            numbers.remove(0)
            numbers.remove(1)
            var count1 = numbers.count { it.key != 0 && it.value == 1 }
            if (count1 > 1) return false

            //println(numbers)

            return true
        }

        return false
    }

//    private fun getFrequency(number: Int, set: Set<Component>): Int {
//
//    }
}