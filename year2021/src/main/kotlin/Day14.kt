fun d14p1(): Int = read("/day14.in").useLines { lines ->
    lines.filter(String::isNotBlank).partition { it.contains("->") }
        .let { (rules, template) ->
            rules.map { it.split(" -> ") }.associate { it[0] to "${it[0][0]}${it[1]}" } to template[0] + " "
        }
        .let { (rules, polymer) ->
            (1..10).fold(polymer) { state, _ ->
                state.windowed(2, 1).map { rules[it] ?: it[0] }.joinToString("") + " "
            }
        }
        .trim()
        .asSequence()
        .groupingBy { it }
        .eachCount().values
        .let {
            it.maxOrNull()!! - it.minOrNull()!!
        }
}

fun d14(iterations: Int): Long = read("/day14.in").useLines { lines ->
    lines.filter(String::isNotBlank).partition { it.contains("->") }
        .let { (rules, template) ->
            rules.map { it.split(" -> ") }.map { it[0] to it[1][0] }
                .toList() to template[0]
        }
        .let { (rules, polymer) ->

            val pairsCount = polymer.windowed(2, 1).groupingBy { it }.eachCount().mapValues { it.value.toLong() }

            val elementCount = polymer.groupingBy { it }.eachCount().mapValues { it.value.toLong() }

            (1..iterations).fold(pairsCount to elementCount) { (pairs, elements), i ->
                val newPairs = mutableMapOf<String, Long>()
                val newElements = elements.toMutableMap()
                rules.forEach { rule ->
                    val pair = rule.first
                    val newElement = rule.second

                    pairs[pair]?.also { pairCount ->
                        newElements.compute(newElement) { _, count -> pairCount + (count ?: 0) }
                        newPairs.compute("${pair[0]}${newElement}") { _, count -> pairCount + (count ?: 0) }
                        newPairs.compute("${newElement}${pair[1]}") { _, count -> pairCount + (count ?: 0) }
                    }
                }
                newPairs.toMap() to newElements.toMap()
            }.second

        }
        .values
        .let {
            it.maxOrNull()!! - it.minOrNull()!!
        }
}

fun main() {
    println("Day 14")
    println(" ├─ ${d14(10)}")
    println(" └─ ${d14(40)}")
}