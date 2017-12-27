package season_2017

import AbstractDay
/**
 * Created by melquiadess on 05/12/2017.
 * Part 1: 11137
 * Part 2: 1037
 */
class Day06 : AbstractDay("Day06") {
    var list = listOf(14,0,15,12,11,11,3,5,1,6,8,4,9,1,8,4)
//    var list = listOf(0,2,7,0)

    override fun part1() {
        println("PART 1")
//        var configs = mutableListOf<String>()
//
//        var currentList: MutableList<Int> =  list.toMutableList()
//        var newConfig: String = getCurrentConfig(currentList)
//
//        configs.add(newConfig)
//
//        var loops = 0
//        while (true) {
//            loops++
//            currentList = distribute(currentList)
//            newConfig = getCurrentConfig(currentList)
//
//            if (configs.contains(newConfig)) break else configs.add(newConfig)
//        }
//
//        //println("$list --> ${distribute(list)}")
//
//        println("Answer: $loops")
    }

    fun distribute(list: List<Int>): MutableList<Int> {
        var newList: MutableList<Int> = list.toMutableList()

        var indexOfMax = list.indexOf(list.max())
        var currentIndex = indexOfMax

        var valueAtIndex = list[currentIndex]
        // reset value at current index
        newList[currentIndex] = 0

        while (valueAtIndex > 0) {
            currentIndex++
            // wrap
            if (currentIndex >= newList.size) currentIndex = 0
            // distribute
            newList[currentIndex] = newList[currentIndex] + 1
            valueAtIndex--
        }

        return newList
    }

    fun getCurrentConfig(list: List<Int>) = list.joinToString("")

    override fun part2() {
        println("PART 2")

        var configs = mutableListOf<String>()

        var currentList: MutableList<Int> =  list.toMutableList()
        var newConfig: String = getCurrentConfig(currentList)

        configs.add(newConfig)

        var loops = 0
        while (true) {
            loops++
            currentList = distribute(currentList)
            newConfig = getCurrentConfig(currentList)

            if (configs.contains(newConfig)) break else configs.add(newConfig)
        }

        println("Answer: $loops")

        var targetConfig = newConfig

        // second pass
        loops = 0
        while (true) {
            loops++
            currentList = distribute(currentList)
            newConfig = getCurrentConfig(currentList)

            if (newConfig == targetConfig) break else configs.add(newConfig)
        }

        println("Answer second pass: $loops")
    }
}