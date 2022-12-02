import java.lang.Integer.max
import java.lang.RuntimeException
import kotlin.math.sqrt

data class Target(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {
    fun test(pos: XY) = when {
        pos.x in x1..x2 && pos.y in y1..y2 -> 0
        pos.x > x2 || pos.y < y1 -> 1
        else -> -1
    }
}

fun seriesSum(n: Int): Int = (1 + n) * n / 2

/**
 * d = (1+n) * n / 2
 * 2*d = n + n*n
 * n^2 + n - 2d = 0
 * a = 1 b = 1 c = -2d
 *
 */
fun velocityXByDistance(distance: Int): Int {
    return max(
        (-1 + sqrt((1 + 4 * 2 * distance).toDouble()).toInt()) / 2,
        (-1 - sqrt((1 + 4 * 2 * distance).toDouble()).toInt()) / 2
    )
}

data class XY(val x: Int, val y: Int)

fun trajectory(pos: XY, velocity: XY, stopCriteria: (pos: XY) -> Boolean): Sequence<XY> {
    var stop = false
    return generateSequence(Pair(pos, velocity)) { (p, v) ->
        if (stop)
            null
        else {
            val newPos = XY(p.x + v.x, p.y + v.y)
            stop = stopCriteria(newPos)
            newPos to XY(
                when {
                    v.x > 0 -> v.x - 1
                    v.x < 0 -> v.x + 1
                    else -> 0
                }, v.y - 1
            )
        }
    }.map { it.first }
}

fun d17p1(): Int = read("/day17.in").useLines { lines ->
    val target = "target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)".toRegex()
        .find(lines.first())!!.groupValues
        .let { Target(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt()) }

    val initialPos = XY(0, 0)

    val velocityXBounds = velocityXByDistance(target.x1) to velocityXByDistance(target.x2)

    val velocityYBounds = when {
        target.y1 >= 0 -> target.y1 to target.y2
        target.y2 < 0 -> -target.y2 - 1 to -target.y1 - 1
        else -> throw RuntimeException("Unsupported target position")
    }

    for (velocityY in (velocityYBounds.second downTo velocityYBounds.first)) {
        for (velocityX in (velocityXBounds.first..velocityXBounds.second)) {
            val lastPosition = trajectory(initialPos, XY(velocityX, velocityY)) {
                target.test(it) >= 0
            }.last()
            if (target.test(lastPosition) == 0) {
                return@useLines seriesSum(velocityY)
            }
        }
    }

    throw RuntimeException("Could not solve the problem")
}

fun d17p2(): Int = read("/day17.in").useLines { lines ->
    val target = "target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)".toRegex()
        .find(lines.first())!!.groupValues
        .let { Target(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt()) }

    val initialPos = XY(0, 0)

    val velocityXBounds = 1 to target.x2
    val velocityYBounds = when {
        target.y1 >= 0 -> 1 to target.y2
        target.y2 < 0 -> target.y1 to -target.y1 - 1
        else -> throw RuntimeException("Unsupported target position")
    }

    var count = 0

    for (velocityY in (velocityYBounds.second downTo velocityYBounds.first)) {
        for (velocityX in (velocityXBounds.first..velocityXBounds.second)) {
            val lastPosition = trajectory(initialPos, XY(velocityX, velocityY)) {
                target.test(it) >= 0
            }.last()
            if (target.test(lastPosition) == 0) {
                count++
            }
        }
    }


    return count
}

fun main() {
    println("Day 17")
    println(" ├─ ${d17p1()}")
    println(" └─ ${d17p2()}")
}