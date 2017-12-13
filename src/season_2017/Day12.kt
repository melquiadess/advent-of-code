package season_2017

import AbstractDay
/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 *  12 - wrong
 *  303 - too low
 *
 * Part 2:
 *  1694 - too high
 */
class Day12 : AbstractDay("Day12") {
    var list: MutableList<String> = mutableListOf()

    // -1 = not checked
    //  0 = not connected
    //  1 = connected
    data class Pipe(val index: Int, val nodes: MutableList<Int>, var isConnectedToZero: Boolean = false)

    val pipes: MutableList<Pipe> = mutableListOf()

    private fun getInput() {
        list = readFile("src/season_2017/input/day12-input-test")
//        list = readFile("src/season_2017/input/day12-input")
    }

    override fun part1() {
        println("PART 1")
        getInput()

        println(list)

        preparePipes(0)

        pipes.forEach { pipe ->
            println(pipe)
        }

        println("Count of 0: ${getPipesConnectedToZero(pipes)}")

        var groupCount = 1

        var filtered = pipes.filter { !it.isConnectedToZero }.toMutableList()

        while (true) {
            pipes.clear()
            preparePipes(filtered[0].index)

            groupCount += getPipesConnectedToZero(filtered)
            filtered = filtered.filter { !it.isConnectedToZero }.toMutableList()




            if (filtered.size == 0) break
        }

        println("Group count: $groupCount")

    }

    private fun getPipesConnectedToZero(pipeList: MutableList<Pipe>) = pipeList.count { it.isConnectedToZero }
    private fun hasZeroInChildren(pipe: Pipe, number: Int) = pipe.nodes.contains(number)

    private fun preparePipes(group: Int) {
        println("********* index: $group *************")
        list.forEachIndexed { index, line ->
            println(line)
            var splits = line.split("<->")

            var children: MutableList<Int> = mutableListOf()
            var childrenSide = splits[1]

            if (childrenSide.contains(",")) {
                val childrenSplits = childrenSide.split(", ")
                childrenSplits.forEach { c ->
                    children.add(c.trim().toInt())
                }
            } else {
                children.add(childrenSide.trim().toInt())
            }

            pipes.add(index, Pipe(index, children, if (index == group) true else children.contains(group)))
        }

        // first line
        val childrenSplits = list[group].split("<->")[1].split(", ")
        childrenSplits.forEach { c ->
            val pos = c.trim().toInt()
            val p = pipes[pos]
            val childrenList = mutableListOf(group)
            if (p.nodes.isNotEmpty()) {
                p.nodes.forEach { node ->
                    if (node != group) childrenList.add(node)
                }
            }
            pipes.removeAt(pos)
            pipes.add(pos, Pipe(pos, childrenList, true))
        }

        // for every index which has 0 as child
        val withZero = pipes.filter { it.nodes.contains(group) }

        withZero.forEach { pipe ->
            pipe.nodes.forEach { n ->
                var pos = n
                var p = pipes[pos]
                p.isConnectedToZero = true

                updateChildrenConnectionsToZero(pos)
            }
        }

        pipes.forEachIndexed { index, pipe ->
            pipe.nodes.forEach { it ->
                if (pipes[it].isConnectedToZero) {
                    pipe.isConnectedToZero = true
                    updateChildrenConnectionsToZero(it)
                }
            }
        }
    }

    private fun updateChildrenConnectionsToZero(index: Int) {
        val pipe = pipes[index]

        pipe.nodes.forEach { number ->
            var pos = number
            var p = pipes[pos]

            if (!p.isConnectedToZero) {
                p.isConnectedToZero = true
                p.nodes.forEach { it ->
                    updateChildrenConnectionsToZero(it)
                }
            }
        }
    }

    override fun part2() {
        println("PART 2")
    }
}