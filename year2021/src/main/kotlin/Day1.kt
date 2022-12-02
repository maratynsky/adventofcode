/**
 * https://adventofcode.com/2021/day/1
 */
fun d1p1() = read("/day1.in").useLines {
    it.map(String::toLong).zipWithNext(Long::minus).filter { a -> a < 0 }.count()
}

fun d1p2() = read("/day1.in").useLines {
    it.map(String::toLong).windowed(3) { it.sum() }
        .zipWithNext(Long::minus).filter { a -> a < 0 }.count()
}

fun main() {
    println("Day 1")
    println(" ├─ ${d1p1()}")
    println(" └─ ${d1p2()}")
}
