fun d6p1(): Long = read("/day6.in").readText()
    .splitToSequence(",")
    .map(String::trim)
    .map(String::toInt)
    .map(::familySize)
    .sum()

fun familySize(fish: Int, day: Int = 0): Long =
    1 + generateSequence(day + fish + 1) { it + 7 }
        .takeWhile { it <= 80 }
        .map { familySize(8, it) }
        .sum()

fun d6p2(): Long = read("/day6.in").readText().splitToSequence(",")
    .map(String::trim)
    .map(String::toInt)
    .groupingBy { it }.eachCount().mapValues { it.value.toLong() }
    .let { initialState ->
        (1..256).fold(initialState) { acc, _ ->
            acc.map { (if (it.key == 0) 6 else (it.key - 1)) to it.value }
                .groupBy { it.first }
                .mapValues { it.value.sumOf { pair -> pair.second } }
                .plus(Pair(8, acc[0] ?: 0))
        }.map { it.value }.sum()
    }


fun main() {
    println("Day 6")
    println(" ├─ ${d6p1()}")
    println(" └─ ${d6p2()}")
}