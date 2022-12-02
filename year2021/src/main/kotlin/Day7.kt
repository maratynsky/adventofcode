import kotlin.math.abs
import kotlin.math.min

fun d7p1(): Int = read("/day7.in").readText()
    .splitToSequence(",")
    .map(String::trim)
    .map(String::toInt)
    .sorted()
    .toList()
    .let { sorted ->
        val median = if (sorted.size % 2 == 0) {
            (sorted[sorted.size / 2 - 1] + sorted[sorted.size / 2]) / 2
        } else {
            sorted[sorted.size / 2]
        }
        sorted.sumOf { abs(it - median) }
    }


fun d7p2(): Int = read("/day7.in").readText()
    .splitToSequence(",")
    .map(String::trim)
    .map(String::toInt)
    .toList()
    .let { crabs ->

        var start = (crabs.minOrNull() ?: 0)
        var end = (crabs.maxOrNull() ?: 0)

        while (true) {
            println("Interval $start:$end")
            val middle = (end + start) / 2
            println("Middle $middle")
            if (middle == start) {
                return@let min(requiredFuel(crabs, start), requiredFuel(crabs, end))
            }
            val d1 = requiredFuel(crabs, middle)
            val d2 = requiredFuel(crabs, middle + 1)
            if (d2 > d1) {
                println("Up $d1->$d2")
                end = middle
            } else if (d2 < d1) {
                println("Down $d1->$d2")
                start = middle
            }
            if (start >= end) {
                return@let d1
            }
        }
        0
    }

fun requiredFuel(crabs: List<Int>, position: Int) = crabs.sumOf { distance(it, position) }

fun distance(from: Int, to: Int) = (1 + abs(to - from)) * abs(to - from) / 2

fun main() {
    println("Day 7")
    println(" ├─ ${d7p1()}")
    println(" └─ ${d7p2()}")
}