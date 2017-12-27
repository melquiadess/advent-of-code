import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created by melquiadess on 02/12/2017.
 */
abstract class AbstractDay(private val date: String) {
    fun readFile(path: String): MutableList<String> {
        var rowsList: MutableList<String> = mutableListOf()

        val stream = Files.newInputStream(Paths.get(path))

        stream.buffered().reader().use { reader ->
            rowsList = reader.readLines().toMutableList()
        }

        return rowsList
    }

    fun run() {
        println("Advent-of-Code 2017...$date")

        part1()
        part2()
    }

    abstract fun part1()
    abstract fun part2()
}