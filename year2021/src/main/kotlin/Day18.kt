import java.lang.RuntimeException
import java.lang.StringBuilder

abstract class AN18 {
    fun printTree() {
        println(tree(this, AN18::getChildren, AN18::toString))
    }

    protected open fun getChildren(): List<AN18> = listOf()
    abstract fun magnitude(): Int
    infix fun plus(that: AN18): AN18 = N18(this, that).reduce()
    open fun reduce(): AN18 = this

    abstract fun flatten(level: Int = 0): List<Pair<N18, Int>>

}

class N18(var left: AN18, var right: AN18) : AN18() {
    override fun toString() = ""
    override fun getChildren() = listOf(left, right)
    override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()

    override fun reduce(): AN18 {
        while (true) {
            val list = flatten()
            val explodingPosition = list.indexOfFirst { it.second >= 4 }
            if (explodingPosition >= 0) {
                val exploded = list[explodingPosition]
                // add exploded left to first number to the left
                for (i in explodingPosition - 1 downTo 0) {
                    if (list[i].first.right is T18) {
                        (list[i].first.right as T18).value += (exploded.first.left as T18).value
                        break
                    }
                    if (list[i].first.left is T18) {
                        (list[i].first.left as T18).value += (exploded.first.left as T18).value
                        break
                    }
                }
                // add exploded right to first number to the right
                for (i in explodingPosition + 1 until list.size) {
                    if (list[i].first.left is T18) {
                        (list[i].first.left as T18).value += (exploded.first.right as T18).value
                        break
                    }
                    if (list[i].first.right is T18) {
                        (list[i].first.right as T18).value += (exploded.first.right as T18).value
                        break
                    }
                }
                // find parent of exploded
                if (explodingPosition != 0 && list[explodingPosition - 1].second == exploded.second - 1) {
                    list[explodingPosition - 1].first.right = T18(0)
                } else {
                    list[explodingPosition + 1].first.left = T18(0)
                }
                continue
            }

            val splitted = list.find { pair ->
                val node = pair.first
                if (node.left is T18 && (node.left as T18).value >= 10) {
                    val value = (node.left as T18).value
                    node.left = N18(T18(value / 2), T18(value - value / 2))
                    return@find true
                }

                if (node.right is T18 && (node.right as T18).value >= 10) {
                    val value = (node.right as T18).value
                    node.right = N18(T18(value / 2), T18(value - value / 2))
                    return@find true
                }
                return@find false
            }

            if(splitted != null) continue

            break

        }
        return this
    }

    override fun flatten(level: Int) =
        left.flatten(level + 1) + (this to level) + right.flatten(level + 1)


}

class T18(var value: Int) : AN18() {
    override fun toString() = value.toString()
    override fun magnitude() = value
    override fun flatten(level: Int) = listOf<Pair<N18, Int>>()
}


fun readAN18(input: String) = readAN18(input.iterator())

fun readAN18(iterator: CharIterator): AN18 {
    var char = iterator.next()

    while (iterator.hasNext() && (char != '[' && char !in '0'..'9')) {
        char = iterator.next()
    }

    if (char == '[') {
        // this is a pair
        return N18(readAN18(iterator), readAN18(iterator))
    }
    if (char in '0'..'9') {
        // this is a value
        val sb = StringBuilder().append(char)
        while (iterator.hasNext()) {
            val c = iterator.next()
            if (c !in '0'..'9') {
                break
            }
            sb.append(c)
        }
        return T18(sb.toString().toInt())
    }

    //should never happen
    throw RuntimeException()
}


fun d18p1(): Int = read("/day18.in").useLines { lines ->
    val result = lines.map(::readAN18).reduce(AN18::plus)
    result.printTree()
    return result.magnitude()
}

fun d18p2(): Int = read("/day18.in").useLines { lines ->
    val lines = lines.toList()

    var max = 0
    for (line1 in lines) {
        for (line2 in lines) {
            if(line1!=line2) {
                val number1 = readAN18(line1)
                val number2 = readAN18(line2)
                val result = number1 plus number2
                val sum = result.magnitude()
                if(sum > max) max = sum
            }
        }
    }


    return max
}

fun main() {
    println("Day 18")
    println(" ├─ ${d18p1()}")
    println(" └─ ${d18p2()}")
}