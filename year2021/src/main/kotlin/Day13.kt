import java.lang.StringBuilder
import kotlin.math.max

typealias Dot = Pair<Int, Int>
typealias Fold = Pair<String, Int>

fun d13p1(): Int = read("/day13.in").useLines { lines ->

    val (dots, folds) = lines.filter(String::isNotBlank).partition { !it.startsWith("fold along ") }
        .let { pair ->
            pair.first.map { it.split(",") }.map { it[0].toInt() to it[1].toInt() } to
                    pair.second.map { it.substringAfter("along ") }.map { it.split("=") }.map { it[0] to it[1].toInt() }
        }


    return dots.mapNotNull { transform(it, folds[0]) }.distinct().count()
}

fun transform(dot: Dot, fold: Fold): Dot? = when (fold.first) {
    "y" -> when {
        dot.second < fold.second -> dot
        dot.second == fold.second -> null
        else -> dot.first to (fold.second * 2 - dot.second)
    }
    "x" -> when {
        dot.first < fold.second -> dot
        dot.first == fold.second -> null
        else -> (fold.second * 2 - dot.first) to dot.second
    }
    else -> null
}

fun d13p2(): String = read("/day13.in").useLines { lines ->

    val result = lines.filter(String::isNotBlank).partition { !it.startsWith("fold along ") }
        .let { pair ->
            pair.first.map { it.split(",") }.map { it[0].toInt() to it[1].toInt() } to
                    pair.second.map { it.substringAfter("along ") }.map { it.split("=") }.map { it[0] to it[1].toInt() }
        }.let { (dots, folds) ->
            folds.fold(dots) { acc, fold -> acc.mapNotNull { transform(it, fold) }.distinct() }
        }.toSet()
    // first - x, second - y
    return with(result.fold(0 to 0) { acc, dot -> max(acc.first, dot.first) to max(acc.second, dot.second) }){
        val strBuilder = StringBuilder()
        for (y in (0 .. this.second)) {
            for (x in (0 .. this.first)){
                strBuilder.append(if(result.contains(x to y)) '█' else ' ')
            }
            strBuilder.append("\n    ")
        }
        strBuilder.toString()
    }
}

fun main() {
    println("Day 13")
    println(" ├─ ${d13p1()}")
    println(" └─ ${d13p2()}")
}