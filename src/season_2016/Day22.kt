package season_2016

import BaseDay
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by melquiadess on 22/12/2016.
 */
class Day22 : BaseDay {
    var list: MutableList<String> = mutableListOf()

    var nodes: MutableList<Node> = mutableListOf()

    data class Node(var x: Int, var y:Int, var size: Int, var used: Int, var avail: Int, var use: Int)

    override fun run() {
        println("Day22")

        readFile()

        println(list)

        extractNodes()

        println(nodes)

        println(findViablePairs(nodes))

    }

    fun findViablePairs(nodes: MutableList<Node>): Int {
        var count = 0

        for (node in nodes) {
            for (node2 in nodes) {
                if (areNodesViable(node, node2)) count++
            }
        }

        return count
    }

    fun areNodesViable(A: Node, B: Node) = isNotEmpty(A) && (A.x != B.x || A.y != B.y) && A.used <= B.avail
    fun isNotEmpty(node: Node) = node.used > 0


    fun extractNodes() {
        for (line in list) {
            var p = line.replace("     ", " ").replace("    ", " ").replace("   ", " ").replace("  ", " ").split(" ")

            var coords = p[0].split("-")
            var x = coords[1].takeLast(coords[1].length-1).toInt()
            var y = coords[2].takeLast(coords[2].length-1).toInt()

            var size = p[1].replace("T", "").toInt()
            var used = p[2].replace("T", "").toInt()
            var avail = p[3].replace("T", "").toInt()
            var use = p[4].replace("%", "").toInt()

            var node = Node(x, y, size, used, avail, use)
            nodes.add(node)
        }
    }

    fun readFile() {
        val stream = Files.newInputStream(Paths.get("src/season_2016/input/day22-input"))

        stream.buffered().reader().use { reader ->
            list = reader.readLines().toMutableList()
        }
    }
}