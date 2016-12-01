package season_2015

/**
 * Created by gregk on 31/10/2016.
 */
class Day09 : BaseDay {
    val TEST_INPUT = arrayOf("London to Dublin = 464", "London to Belfast = 518", "Dublin to Belfast = 141")
    val INPUT = arrayOf("AlphaCentauri to Snowdin = 66", "AlphaCentauri to Tambi = 28", "AlphaCentauri to Faerun = 60", "AlphaCentauri to Norrath = 34", "AlphaCentauri to Straylight = 34", "AlphaCentauri to Tristram = 3", "AlphaCentauri to Arbre = 108", "Snowdin to Tambi = 22", "Snowdin to Faerun = 12", "Snowdin to Norrath = 91", "Snowdin to Straylight = 121", "Snowdin to Tristram = 111", "Snowdin to Arbre = 71", "Tambi to Faerun = 39", "Tambi to Norrath = 113", "Tambi to Straylight = 130", "Tambi to Tristram = 35", "Tambi to Arbre = 40", "Faerun to Norrath = 63", "Faerun to Straylight = 21", "Faerun to Tristram = 57", "Faerun to Arbre = 83", "Norrath to Straylight = 9", "Norrath to Tristram = 50", "Norrath to Arbre = 60", "Straylight to Tristram = 27", "Straylight to Arbre = 81", "Tristram to Arbre = 90")

    private var map: MutableMap<String, Int> = mutableMapOf()
    private var cities: MutableSet<String> = mutableSetOf()

    private var permutations: MutableMap<String, Int> = mutableMapOf()
    private var citiesPermutations: MutableList<String> = mutableListOf()

    override fun run() {
        println("Day09")

        map = initializeDataStructures(INPUT)

        permuteCities(cities.toMutableList(), 0)

        println(getShortestPath(citiesPermutations))

        println()
    }

    fun getShortestPath(list: MutableList<String>) : Pair<Int, Int> {
        var shortest = Int.MAX_VALUE
        var longest = Int.MIN_VALUE

        for (path in list) {
            var parts = path.split(",")

            var distance = 0
            var i = 0
            while (i < parts.count()-1) {
                val p = parts[i] + "," + parts[i+1]
                val pr = parts[i+1] + "," + parts[i]
                var dist: Int = map[p] ?: map[pr] ?: 0

                distance += dist
                i++
            }

            if (distance <= shortest) {
                shortest = distance
            }

            if (distance >= longest) {
                longest = distance
            }
        }

        return Pair(shortest, longest)
    }

    fun initializeDataStructures(input: Array<String>) : MutableMap<String, Int> {
        var map: MutableMap<String, Int> = mutableMapOf()

        for (str in input) {
            val part = str.split(" ")

            val key = part[0] + "," + part[2]
            val value = part[4].toInt()

            map.put(key, value)

            cities.add(part[0])
            cities.add(part[2])
        }

        return map
    }

    fun permuteCities(arr: MutableList<String>, k: Int) {
        for (i in k..arr.size - 1) {
            java.util.Collections.swap(arr, i, k)
            permuteCities(arr, k + 1)
            java.util.Collections.swap(arr, k, i)
        }
        if (k == arr.size - 1) {
//            println(java.util.Arrays.toString(arr.toTypedArray()))
            val cities = java.util.Arrays.toString(arr.toTypedArray())
            .removePrefix("[")
            .removeSuffix("]")
            .replace(" ", "")

            citiesPermutations.add(cities)
        }
    }
}