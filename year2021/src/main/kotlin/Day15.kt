data class Node15(
    val risk: Int,
    val pos: Position,
    var shortestPath: Int = Integer.MAX_VALUE,
    var visited: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node15

        if (pos != other.pos) return false

        return true
    }

    override fun hashCode(): Int {
        return pos.hashCode()
    }
}
typealias Graph = Array<Array<Node15>>

data class Position(val x: Int, val y: Int)

infix fun Int.x(that: Int): Position = Position(this, that)
operator fun Graph.get(pos: Position) = this[pos.y][pos.x]
fun Graph.getNeighbors(pos: Position) =
    sequenceOf(
        pos.x - 1 x pos.y,
        pos.x x pos.y - 1,
        pos.x x pos.y + 1,
        pos.x + 1 x pos.y,
    )
        .filter { it.x >= 0 && it.y >= 0 }
        .filter { it.y < this.size && it.x < this[it.y].size }
        .map { this[it] }
        .filterNot { it.visited }
        .sortedBy { it.shortestPath }
        .toList()


val nodes: HashSet<Node15> = HashSet()

fun Graph.getMin(): Pair<Position, Node15> {
    if(nodes.isNotEmpty()){
        return nodes.minByOrNull { it.shortestPath }!!.let { it.pos to it }
    }
    return this.flatMapIndexed { y, row -> row.mapIndexed { x, node -> Position(x, y) to node } }
            .filterNot { it.second.visited }
        .first()
}

var visited: Int = 0

fun Graph.visited(): Boolean {
    return visited == this.size * this.size
}

fun Graph.extend(times: Int) = Graph(this.size * times) { y ->
    val originalY = y % this.size
    Array(this[originalY].size * times) { x ->
        val add = (y / this.size) + (x / this[originalY].size)
        val pos = (x % this[originalY].size) x originalY
        val risk = this[pos].risk + add
        Node15(if (risk > 9) (risk % 10 + 1) else risk, x x y)
    }
}

fun readGraph(lines: Sequence<String>): Graph =
    lines.mapIndexed { y, line -> line.map(Char::digitToInt).mapIndexed{x,d -> Node15(d, x x y)}.toTypedArray() }
        .toList()
        .toTypedArray()

fun d15(graph: Graph): Int {
    visited = 0
    val start = 0 x 0
    val finish = graph.last().size - 1 x graph.size - 1
    graph[start].shortestPath = 0

    while (!graph.visited()) {
        val (pos, node) = graph.getMin()
        val neighbors = graph.getNeighbors(pos)
        nodes.addAll(neighbors)
        neighbors.forEach { neighborNode ->
            val path = node.shortestPath + neighborNode.risk
            if (path < neighborNode.shortestPath) {
                neighborNode.shortestPath = path
            }
        }
        node.visited = true
        nodes.remove(node)
        visited++
    }
    return graph[finish].shortestPath
}

fun d15p1(): Int = d15(read("/day15.in").useLines { readGraph(it) })

fun d15p2(): Int = d15(read("/day15.in").useLines { readGraph(it).extend(5) })

fun main() {
    println("Day 15")
    println(" ├─ ${d15p1()}")
    println(" └─ ${d15p2()}")
}
