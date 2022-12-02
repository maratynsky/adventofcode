fun d11p1(): Int = read("/day11.in").useLines { lines ->
    val octopuses = lines.map { it.asSequence().map(Char::digitToInt).toList() }
        .map { it.toIntArray() }
        .toList().toTypedArray()

    return (1..100).sumOf { octopuses.step() }
}

typealias Octopuses = Array<IntArray>

fun Octopuses.step(): Int {
    val flashes = this.flatMapIndexed { y, _ ->
        this.mapIndexed { x, _ ->
            incEnergy(x, y)
        }
    }.sum()
    reset()
    return flashes
}

fun Octopuses.incEnergy(x: Int, y: Int) = when {
    this[y][x] == -1 -> 0
    ++this[y][x] > 9 -> flash(x, y)
    else -> 0
}

fun Octopuses.flash(x: Int, y: Int): Int {
    this[y][x] = -1
    return 1 + neighbours(x, y).sumOf { incEnergy(it.first, it.second) }
}

fun Octopuses.neighbours(x: Int, y: Int): Sequence<Pair<Int, Int>> {
    return sequenceOf(
        x - 1 to y - 1,
        x - 1 to y,
        x - 1 to y + 1,
        x to y - 1,
        x to y + 1,
        x + 1 to y - 1,
        x + 1 to y,
        x + 1 to y + 1
    )
        .filter { it.first >= 0 && it.second >= 0 }
        .filter { it.second < this.size && it.first < this[it.second].size }
}

fun Octopuses.reset() {
    for (y in this.indices) {
        for (x in this[y].indices) {
            if (this[y][x] == -1) this[y][x] = 0
        }
    }
}

fun d11p2(): Int = read("/day11.in").useLines { lines ->
    val octopuses = lines.map { it.asSequence().map(Char::digitToInt).toList() }
        .map { it.toIntArray() }
        .toList().toTypedArray()

    generateSequence(1) { it + 1 }.filter { octopuses.step() == 100 }.first()

}

fun main() {
    println("Day 11")
    println(" ├─ ${d11p1()}")
    println(" └─ ${d11p2()}")
}