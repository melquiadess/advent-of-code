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

    // -1 = don't use
    //  0 = not connected
    //  1 = connected
    data class Program(val index: Int, val nodes: MutableList<Int>, var isConnectedToZero: Int = 0)

    var programs: MutableList<Program> = mutableListOf()
    var tempPrograms: MutableList<Program> = mutableListOf()

    private fun getInput() {
//        list = readFile("src/season_2017/input/day12-input-test")
        list = readFile("src/season_2017/input/day12-input")
    }

    override fun part1() {
        println("PART 1")
        getInput()

        println(list)

        programs = initProgramsFromFile()
        programs = setProgramsForGroup(0, programs)

        programs.forEach { pipe ->
            println(pipe)
        }

        println("Count of 0: ${getPipesConnectedToZero(programs)}")

        var groupCount = 1

        println("Group count: $groupCount")

    }

    private fun getPipesConnectedToZero(programList: MutableList<Program>) = programList.count { it.isConnectedToZero == 1 }
    private fun hasZeroInChildren(program: Program, number: Int) = program.nodes.contains(number)

    private fun initProgramsFromFile(): MutableList<Program> {
        val programs: MutableList<Program> = mutableListOf()

        list.forEachIndexed { index, line ->
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

            programs.add(index, Program(index, children, 0))
        }

        return programs
    }

    private fun setProgramsForGroup(group: Int, programs: MutableList<Program>): MutableList<Program> {
        val programsGrouped: MutableList<Program> = mutableListOf()
        programs.forEachIndexed { index, program ->
            programsGrouped.add(index, Program(index, program.nodes, if (index == group) 1 else if (program.nodes.contains(group)) 1 else 0))
        }

        // group'th line
        val children = programsGrouped[group].nodes
        children.forEach { child ->
            val pos = child
            val c = programsGrouped[pos]

            val childrenList = mutableListOf(group)
            if (c.nodes.isNotEmpty()) {
                c.nodes.forEach { node ->
                    if (node != group) childrenList.add(node)
                }
            }

            programsGrouped.removeAt(pos)
            programsGrouped.add(pos, Program(pos, childrenList, 1))
        }

        // for every index which has 0 as child
        val withZero = programsGrouped.filter { it.nodes.contains(group) }.toMutableList()

        withZero.forEach { program ->
            program.nodes.forEach { n ->
                var pos = n
                var p = programs[pos]
                p.isConnectedToZero = 1

                updateChildrenConnectionsToZero(pos, programsGrouped)
            }
        }

        programsGrouped.forEachIndexed { _, program ->
            program.nodes.forEach { it ->
                if (programsGrouped[it].isConnectedToZero == 1) {
                    program.isConnectedToZero = 1
                    updateChildrenConnectionsToZero(it, programsGrouped)
                }
            }
        }

        return programsGrouped
    }

    private fun updateChildrenConnectionsToZero(index: Int, programs: MutableList<Program>) {
        val program = programs[index]

        program.nodes.forEach { number ->
            var pos = number
            var p = programs[pos]

            if (p.isConnectedToZero != 1) {
                p.isConnectedToZero = 1
                p.nodes.forEach { it ->
                    updateChildrenConnectionsToZero(it, programs)
                }
            }
        }
    }

    override fun part2() {
        println("PART 2")
    }
}