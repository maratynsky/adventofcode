import kotlin.math.abs
import kotlin.math.sign

typealias Point = Pair<Int, Int>

fun d5(countDiagonals: Boolean): Int = read("/day5.in").useLines {
    it.flatMap { input -> toPointSeq(input, countDiagonals) }.groupingBy { point -> point }.eachCount().asSequence()
        .filter { p -> p.value > 1 }.count()
}

fun d5p1(): Int = d5(false)

fun d5p2(): Int = d5(true)

fun toPointSeq(input: String, countDiagonals: Boolean): Sequence<Point> {
    val start = input.substringBefore(" -> ").toPoint()
    val end = input.substringAfter(" -> ").toPoint()

    val isDiagonal = !isVerticalOrHorizontal(start, end)
    if (!countDiagonals && isDiagonal) {
        return emptySequence()
    }
    return if (isDiagonal) {
        seq(start.first, end.first).asSequence().zip(seq(start.second, end.second))
    } else {
        seq(start.first, end.first).asSequence()
            .flatMap { first -> seq(start.second, end.second).map { second -> Point(first, second) } }
    }

}

fun seq(n1: Int, n2: Int): Sequence<Int> = generateSequence(n1) { it + (n2 - n1).sign }.take(abs(n2 - n1) + 1)

fun isVerticalOrHorizontal(p1: Point, p2: Point): Boolean = p1.first == p2.first || p1.second == p2.second

fun String.toPoint(): Point = Point(this.substringBefore(",").toInt(), this.substringAfter(",").toInt())

fun main() {
    println("Day 5")
    println(" ├─ ${d5p1()}")
    println(" └─ ${d5p2()}")
}