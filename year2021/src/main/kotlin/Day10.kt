import java.util.*


fun d10p1(): Int = read("/day10.in").useLines { lines ->

    val pairs = mapOf('{' to '}', '[' to ']', '<' to '>', '(' to ')')

    val points = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    lines.map { line ->
        val stack = Stack<Char>()
        line.asSequence().find {
            val closing = pairs[it]
            if (closing != null) {
                stack.push(closing)
            } else {
                if (stack.pop() != it) {
                    return@find true
                }
            }
            return@find false
        }
    }.filterNotNull().sumOf { points[it]!! }
}

fun d10p2(): Long = read("/day10.in").useLines { lines ->

    val pairs = mapOf('{' to '}', '[' to ']', '<' to '>', '(' to ')')

    val points = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    lines.map { line ->
        val stack = Stack<Char>()
        val incomplete = line.asSequence().none {
            val closing = pairs[it]
            if (closing != null) {
                stack.push(closing)
            } else {
                if (stack.pop() != it) {
                    return@none true
                }
            }
            return@none false
        }
        return@map if (incomplete) generateSequence{ if (stack.isNotEmpty()) stack.pop() else null } else emptySequence()
    }
        .map { it.fold(0L) { acc, c -> acc * 5 + points[c]!! } }
        .filter { it>0 }
        .sorted()
        .toList().let {
            println(it)
            it }
        .let { it[it.size/2] }
}

fun main() {
    println("Day 10")
    println(" ├─ ${d10p1()}")
    println(" └─ ${d10p2()}")
}