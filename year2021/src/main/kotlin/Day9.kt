fun d9p1(): Int = read("/day9.in").useLines { lines ->
    val data = lines.map { it.asSequence().map(Char::digitToInt).toList() }
        .map { it.toIntArray() }
        .toList().toTypedArray()
    data.flatMapIndexed { row, rowData -> rowData.filterIndexed { col, _ -> data.isLowPoint(row, col) } }
        .sumOf { it + 1 }
}

fun Array<IntArray>.isLowPoint(row: Int, col: Int): Boolean {
    val height = getHeight(row, col)
    return height < getHeight(row - 1, col)
            && height < getHeight(row + 1, col)
            && height < getHeight(row, col - 1)
            && height < getHeight(row, col + 1)
}

fun Array<IntArray>.getHeight(row: Int, col: Int): Int {
    if (row < 0 || row >= this.size) {
        return Int.MAX_VALUE
    }
    val rowData: IntArray = this[row]
    if (col < 0 || col >= rowData.size) {
        return Int.MAX_VALUE
    }
    return rowData[col]
}

fun d9p2(): Int = read("/day9.in").useLines { lines ->
    val data = lines.map { it.asSequence().map(Char::digitToInt).toList() }
        .map { it.toIntArray() }
        .toList().toTypedArray()

    data.flatMapIndexed { row, rowData ->
        rowData.mapIndexed() { col, _ ->
            if (data.isLowPoint(row, col)) data.basinSize(row, col) else -1
        }
    }.asSequence()
        .filter { it > 0 }
        .sortedDescending()
        .take(3)
        .fold(1) { acc, i -> acc * i }
}

fun Array<IntArray>.basinSize(row: Int, col: Int): Int {
    if (row < 0 || row >= this.size) {
        return 0
    }
    val rowData: IntArray = this[row]
    if (col < 0 || col >= rowData.size) {
        return 0
    }
    if (this[row][col] == 9) {
        return 0
    }
    this[row][col] = 9
    return 1 + basinSize(row - 1, col) + basinSize(row + 1, col) + basinSize(row, col - 1) + basinSize(row, col + 1)
}

fun main() {
    println("Day 9")
    println(" ├─ ${d9p1()}")
    println(" └─ ${d9p2()}")
}