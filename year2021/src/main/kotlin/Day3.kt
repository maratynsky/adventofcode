import java.lang.RuntimeException

/**
 * https://adven tofcode.com/2021/day/3
 */

fun d3p1(n:Int): Long = read("/day3.in").useLines {
    val stats = Array(n) { 0 }
    it.forEach { report ->
        report.forEachIndexed { i, bit ->
            stats[i] += if (bit == '1') 1 else -1
        }
    }
    var gamma = 0L
    var epsilon = 0L
    stats.map { stat -> stat > 0 }.forEachIndexed { i, bit ->
        gamma += (if (bit) 1 else 0) shl (n - 1 - i)
        epsilon += (if (bit) 0 else 1) shl (n - 1 - i)
    }
    return gamma * epsilon
}

fun d3p2(): Long = read("/day3.in").useLines {
    val list = it.toList()
    return 1L * bar(list, 0, true) * bar(list, 0, false)
}

fun bar(list: List<String>, idx: Int, common:Boolean): Int {
    if (list.isEmpty()) {
        throw RuntimeException()
    }
    if (list.size == 1) {
        return Integer.parseInt(list[0], 2)
    }
    val groups = list.groupBy { it[idx] }
    val mostCommonBit = if (common) {if ((groups['1']?.size ?: 0) >= (groups['0']?.size ?: 0)) '1' else '0'}
    else {if ((groups['1']?.size ?: 0) < (groups['0']?.size ?: 0)) '1' else '0'}
    return bar(groups[mostCommonBit]!!, idx + 1, common)
}


fun main() {
    println("Day 3")
    println(" ├─ ${d3p1(12)}")
    println(" └─ ${d3p2()}")
}