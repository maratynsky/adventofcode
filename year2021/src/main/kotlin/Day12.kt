class Cave(val name: String) {
    val small: Boolean = name.all { it in ('a'..'z') }
    val connections: MutableCollection<Cave> = mutableSetOf()

    fun connect(cave: Cave) {
        connections.add(cave)
        cave.connections.add(this)
    }
}

val caveSystem = read("/day12.in").useLines { lines ->
    lines.map { it.split("-") }
        .fold(mutableMapOf<String, Cave>()) { caves, line ->
            caves.computeIfAbsent(line[0], ::Cave)
                .connect(caves.computeIfAbsent(line[1], ::Cave))
            caves
        }
}

fun d12p1() = findDistinctPathsVisitingSmallCavesOnce(caveSystem["start"]!!, caveSystem["end"]!!)

fun findDistinctPathsVisitingSmallCavesOnce(start: Cave, end: Cave): Int {
    return distinctPathsVisitingSmallCavesOnce(start, end)
}

fun distinctPathsVisitingSmallCavesOnce(current: Cave, end: Cave, visited: Set<Cave> = setOf()): Int = when {
    current == end -> 1
    current.small && visited.contains(current) -> 0
    else -> current.connections.sumOf { distinctPathsVisitingSmallCavesOnce(it, end, visited.plus(current)) }
}

fun d12p2() = findDistinctPathsVisitingOneSmallCaveTwice(caveSystem["start"]!!, caveSystem["end"]!!)

fun findDistinctPathsVisitingOneSmallCaveTwice(start: Cave, end: Cave): Int {
    return distinctPathsVisitingOneSmallCaveTwice(start, end)
}

fun distinctPathsVisitingOneSmallCaveTwice(current: Cave, end: Cave, visited: List<Cave> = listOf(), visitedTwice: Boolean = false): Int = when {
    current == end -> 1
    current.small && visited.contains(current) && (visitedTwice) -> 0
    (current.name == "start" || current.name == "end") && visited.contains(current) -> 0
    else -> current.connections.sumOf {
        distinctPathsVisitingOneSmallCaveTwice(
            it,
            end,
            visited.plus(current),
            visitedTwice || (visited.contains(current) && current.small)
        )
    }
}

fun main() {
    println("Day 12")
    println(" ├─ ${d12p1()}")
    println(" └─ ${d12p2()}")
}