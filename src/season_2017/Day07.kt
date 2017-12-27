package season_2017

import AbstractDay
/**
 * Created by melquiadess on 07/12/2017.
 * Part 1:
 *  rqpripo - wrong
 *
 * Part 2:
 *  -18876 - wrong
 */
class Day07 : AbstractDay("Day07") {
    var listString: MutableList<String> = mutableListOf()
    var towerList: MutableList<Tower> = mutableListOf()

    data class Tower(var name: String, var value: Int = 0, var children: MutableList<Tower> = mutableListOf())

    override fun part1() {
        println("PART 1")

        // vvsvez (57) -> utlqx, xzhdy, tlmvaep, nbyij, fszyth, zimrf

//        loadInputFromFile()
//
//        listString.forEach {
//            line -> processLine(line)
//        }
//
//        println("Bottom: ${getBottomTower(towerList)}")
    }

    override fun part2() {
        println("PART 2")

        towerList.clear()
        listString.clear()
        loadInputFromFile()

        listString.forEach {
            line -> processLine(line)
        }

//        println(towerList)
        updateTowers()

        // get towers with children
        val towersWithChildren = towerList.filter { tower -> tower.children.isNotEmpty() }
        println("Towers with children count: ${towersWithChildren.count()}")

        // find bottom tower
        val bottomTower = getBottomTower(towerList)
        println("Bottom tower: ${bottomTower.name}")

        processFromBottomTower(bottomTower.children)
    }

    private fun loadInputFromFile() {
        listString = readFile("src/season_2017/input/day07-input")
//        listString = readFile("src/season_2017/input/day07-input-test")
    }

    private fun processFromBottomTower(towers: MutableList<Tower>) {
        println("****************************")
        var stacks: MutableMap<Tower, Int> = mutableMapOf()

        towers.forEach { tower ->
            var sum = 0

            println("tower: ${tower.name} (${tower.value})")

            tower.children.forEach { child ->
                val found: Tower? = getTowerByName(child.name)
                sum += found?.value ?: 0
            }
            sum += tower.value

            println("tower+children: $sum")
            stacks.put(tower, sum)
        }


        // check all sums for this level, if one is different,
        var set: MutableSet<Int> = mutableSetOf()

        stacks.forEach { it ->
            println("sum: ${it.value}")
            set.add(it.value)
        }

        towers.forEach { child ->
            processFromBottomTower(child.children)
        }

//        if (set.size == stacks.size) {
//            // go deeper
//            towers.forEach { child ->
//                processFromBottomTower(child.children)
//            }
//        }
//        else {
//            //set.forEach { it -> println(it) }
//        }
//        if (sum == sumFromAbove) {
//            towers.forEach { subtower ->
//                processFromBottomTower(subtower.children, sum)
//            }
//        }
//        else {
//            println("tower: ${tower.name}")
//        }
    }

    private fun getTowerByName(name: String): Tower? = towerList.find { it.name == name }

    private fun updateTowers() {
        towerList.forEach { tower ->
            if (tower.children.isNotEmpty()) {
                tower.children.forEach { child ->
                    if (child.value == 0) {
                        var value = towerList.find { it.name == child.name }
                        child.value = value?.value ?: 0
                        child.children = value?.children ?: child.children
                    }
                }
            }
        }
    }

    /**
     * ktlj (57)
     *
     * or
     *
     * fwft (72) -> ktlj, cntj, xhth
     */
    private fun processLine(line: String) {
        if (line.contains("->")) {
            addTowerWithChildren(line)
        } else {
            addTower(line)
        }
    }

    private fun addTower(line: String) {
        val tower = createTower(line)
        towerList.add(tower)
    }

    private fun createTower(line: String): Tower {
        val splits = line.split(" ")

        val name = splits[0].trim()
        val value = splits[1].replace("(", "").replace(")", "").trim().toInt()

        return Tower(name, value)
    }

    /**
     * fwft (72) -> ktlj, cntj, xhth
     */
    private fun addTowerWithChildren(line: String) {
        val splits = line.split("->")

        val tower = createTower(splits[0])

        // add children
        val childrenString = splits[1].split(", ")
        var childrens: MutableList<Tower> = mutableListOf()
        childrenString.forEach {
            it -> childrens.add(Tower(it.trim()))
        }

        tower.children = childrens
        towerList.add(tower)
    }

    private fun getBottomTower(list: MutableList<Tower>): Tower {
        // ROOT TOWER is a tower that:
        // - is in the big list
        // - has children
        // - is NOT in any children list

        val towersWithChildren = list.filter { it.children.isNotEmpty() }

        list.forEach { tower ->
            if (tower.children.isNotEmpty()) {
                if (isInChildren(towersWithChildren, tower.name)) {
                    return tower
                }
            }
        }

        return Tower("unknown")
    }

    private fun isInChildren(towersWithChildren: List<Tower>, name: String): Boolean {
        var count = 0
        towersWithChildren.forEach { tower ->
            count += tower.children.count { it.name == name }
        }

        return count == 0
    }
}