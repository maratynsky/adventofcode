@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

import java.io.File
import java.nio.file.Path

fun read(resource:String): File = Path.of({}.javaClass.getResource(resource).toURI()).toFile()


fun <Node> tree(
    root: Node,
    getChildren: (Node) -> List<Node>,
    toString: (Node)-> String
): String {
    val sb = StringBuilder()
    tree(sb, BooleanArray(0), root, getChildren, toString)
    return sb.toString()
}

private fun <Node> tree(
    sb: StringBuilder,
    h: BooleanArray,
    node: Node,
    getChildren: (Node) -> List<Node>,
    toString: (Node)-> String
) {
    for (i in 0 until h.size - 1) {
        sb.append(if (h[i]) " │ " else "   ")
    }
    val children: List<Node> = getChildren(node)
    sb.append(" ")
    if (h.isNotEmpty()) {
        sb.append(if (h[h.size - 1]) "├──" else "└──")
        sb.append(if (children.isNotEmpty()) "┬" else "─")
    } else {
        sb.append(if (children.isNotEmpty()) "┌" else " ")
    }
    sb.append("■ ")
    sb.append(toString(node))
    sb.append("\n")
    for (i in children.indices) {
        val h2 = BooleanArray(h.size + 1)
        System.arraycopy(h, 0, h2, 0, h.size)
        h2[h.size] = i < children.size - 1
        tree(sb, h2, children[i], getChildren, toString)
    }
}