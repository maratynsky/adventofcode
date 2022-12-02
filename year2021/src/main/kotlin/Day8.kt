//                    0:      1:      2:      3:      4:
//                   aaaa    ....    aaaa    aaaa    ....
//                  b    c  .    c  .    c  .    c  b    c
//                  b    c  .    c  .    c  .    c  b    c
//                   ....    ....    dddd    dddd    dddd
//                  e    f  .    f  e    .  .    f  .    f
//                  e    f  .    f  e    .  .    f  .    f
//                   gggg    ....    gggg    gggg    ....
//
//                    5:      6:      7:      8:      9:
//                   aaaa    aaaa    aaaa    aaaa    aaaa
//                  b    .  b    .  .    c  b    c  b    c
//                  b    .  b    .  .    c  b    c  b    c
//                   dddd    dddd    ....    dddd    dddd
//                  .    f  e    f  .    f  e    f  .    f
//                  .    f  e    f  .    f  e    f  .    f
//                   gggg    gggg    ....    gggg    gggg
//
//
// 0 - 6
// 1 - 2 !
// 2 - 5 !
// 3 - 5 !
// 4 - 4 !
// 5 - 5 !
// 6 - 6 !
// 7 - 3 !
// 8 - 7 !
// 9 - 6
//
// 1,4,7,8 - are unique

fun d8p1(): Int = read("/day8.in").useLines { input ->
    input.map { it.substringAfter("|") }
        .flatMap { it.split(" ") }
        .map(String::trim)
        .filter(::isUnique)
        .count()
}

// 0 - 6
// 1 - 2
// 3 - 5
// 4 - 4
// 5 - 5
// 6 - 6
// 7 - 3
// 8 - 7
// 9 - 6
//
// 1,4,7,8 - are unique
fun d8p2(): Int = read("/day8.in").useLines { input ->
    input.map { line ->
        val mapping = findDigitPatterns(line.substringBefore("|"))
        val digits = line.substringAfter("|").trim()
        digits.split(" ")
            .map { it.toCharArray().sorted().joinToString("") }
            .map { mapping[it]!! }
            .fold(0) { acc, num -> acc * 10 + num }
    }.sum()
}

fun findDigitPatterns(input: String): Map<String, Int> {

    val patterns = input.split(" ").map { it.trim() }
    val mapping = patterns.map { getNum(it) to it }.filter { it.first >= 0 }.toMap()

    val one = mapping[1]!!
    val eight = mapping[8]!!

    val six = patterns.filter { it.length == 6 }.first { intersect(it, one).length == 1 }

    val topRightSegment = subtract(eight, six)

    val five = patterns.filter { it.length == 5 }.first { !it.contains(topRightSegment) }
    val three =
        patterns.filter { it.length == 5 }.first { it.contains(topRightSegment) && intersect(it, one).length == 2 }
    val two =
        patterns.filter { it.length == 5 }.first { it.contains(topRightSegment) && intersect(it, one).length == 1 }

    val nine = patterns.filter { it.length == 6 && it != six }.first { intersect(it, five).length == 5 }

    val zero = patterns.filter { it.length == 6 && it != six }.first { intersect(it, five).length != 5 }


    return mapping
        .plus(6 to six)
        .plus(5 to five)
        .plus(3 to three)
        .plus(2 to two)
        .plus(0 to zero)
        .plus(9 to nine)
        .map { it.value.toCharArray().sorted().joinToString("") to it.key }.toMap()
}

fun subtract(s1: String, s2: String) = s1.filter { !s2.contains(it) }
fun intersect(s1: String, s2: String) = s1.filter { s2.contains(it) }

fun isUnique(digit: String) = getNum(digit) >= 0

fun getNum(digit: String) = when (digit.length) {
    2 -> 1
    4 -> 4
    3 -> 7
    7 -> 8
    else -> -1
}

fun main() {
    println("Day 8")
    println(" ├─ ${d8p1()}")
    println(" └─ ${d8p2()}")
}