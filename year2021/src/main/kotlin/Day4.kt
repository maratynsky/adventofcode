open class Board(private val data: Array<Array<Int?>>) {
    private val size = data.size

    open fun mark(num: Int): Boolean {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (data[i][j] == num) {
                    data[i][j] = null
                }
            }
        }
        return isWining()
    }

    private fun isWining(): Boolean {
        for (row in data) {
            if (row.all { it == null }) return true
        }

        for (j in 0 until size) {
            if ((0 until size).map { data[it][j] }.all { it == null }) return true
        }
        return false
    }

    fun score() = data.flatMap { it.asSequence() }.filterNotNull().sum()
}

fun d4p1(): Int = read("/day4.in").useLines {
    val iterator = it.iterator()
    val numbers = iterator.next().split(",").map(String::toInt)
    iterator.next()
    val boards = iterator.asSequence().chunked(6) { rows ->
        rows.filter(String::isNotBlank)
            .map { row -> row.split(" ").filter(String::isNotBlank).map(String::toInt).toTypedArray<Int?>() }
            .toTypedArray()

    }.map(::Board).toList()

    numbers.forEach { num ->
        boards.forEach { board ->
            if (board.mark(num)) {
                //winning board
                return board.score() * num
            }
        }
    }
    return 0
}

fun d4p2(): Int = read("/day4.in").useLines {
    val iterator = it.iterator()
    val numbers = iterator.next().split(",").map(String::toInt)
    iterator.next()
    val boards = iterator.asSequence().chunked(6) { rows ->
        rows.filter(String::isNotBlank)
            .map { row -> row.split(" ").filter(String::isNotBlank).map(String::toInt).toTypedArray<Int?>() }
            .toTypedArray()

    }.map(::Board)

    val lastWiningBoard = boards.map { board ->
        for (i in numbers.indices) {
            if (board.mark(numbers[i])) {
                return@map Pair(board, i)
            }
        }
        return@map Pair(board, -1)
    }.sortedByDescending { p -> p.second }.first()

    return lastWiningBoard.first.score() * numbers[lastWiningBoard.second]
}


fun main() {
    println("Day 4")
    println(" ├─ ${d4p1()}")
    println(" └─ ${d4p2()}")
}