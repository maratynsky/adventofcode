/**
 * https://adventofcode.com/2021/day/2
 */
fun d2p1(): Long = read("/day2.in").useLines {
    var x = 0L
    var y = 0L
    it.forEach { action ->
        val split = action.split(" ")
        val command = split[0]
        val delta = split[1].toLong()
        when (command) {
            "forward" -> x += delta
            "up" -> y -= delta
            "down" -> y += delta
        }
    }
    return x * y
}

fun d2p2(): Long = read("/day2.in").useLines {
    var a = 0L
    var x = 0L
    var y = 0L
    it.forEach { action ->
        val split = action.split(" ")
        val command = split[0]
        val delta = split[1].toLong()
        when (command) {
            "forward" -> {
                x += delta
                y += a * delta
            }
            "up" -> a -= delta
            "down" -> a += delta
        }
    }
    return x * y
}

fun main() {
    println("Day 2")
    println(" ├─ ${d2p1()}")
    println(" └─ ${d2p2()}")
}