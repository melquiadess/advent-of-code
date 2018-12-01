import java.util.*

/**
 * Created by gregk on 13/11/2016.
 */
object Stopwatch {
    var start = 0L
    var finish = 0L

    fun startTimer() {
        start = System.nanoTime()
    }

    fun getElapsedSeconds() = (System.nanoTime() - start) / 1000000L
}