package me.yapoo.tree

import kotlin.math.abs
import kotlin.math.max

abstract class BinaryTree<T, Node : BTNode<T, Node>> {

    var root: Node? = null

    override fun toString(): String {
        val root = root ?: return "null"

        val nodes = createNodeList(root, 0)
        val indentSize = 4
        var previous: String? = null
        return nodes.map { (node, d) ->
            val depth = abs(d)
            if (depth == 0) {
                node.toString()
            } else {
                val indent = indentSize * depth
                val branch = if (d >= 0) "├── " else "└── "
                val line = (" ".repeat(indent - indentSize) + branch + node.toString()).toCharArray()

                previous?.withIndex()?.forEach { (index, c) ->
                    if (listOf('└', '├', '│').contains(c) && index < line.size && line[index] == ' ') {
                        line[index] = '│'
                    }
                }
                String(line)
            }.also { previous = it }
        }.reversed().joinToString("\n")
    }

    private fun createNodeList(
        u: Node,
        depth: Int,
    ): List<Pair<Node?, Int>> {
        val left = u.left?.let { createNodeList(it, depth + 1) }
            ?: if (u.right != null) listOf(Pair(null, -(depth + 1))) else emptyList()

        val right = u.right?.let { createNodeList(it, depth + 1) }
            ?: if (u.left != null) listOf(Pair(null, depth + 1)) else emptyList()

        val d = if (u.parent?.left == u) -depth else depth
        return left + right + listOf(Pair(u, d))
    }
}

abstract class BTNode<T, Node : BTNode<T, Node>>(
    var value: T,
    var left: Node? = null,
    var right: Node? = null,
    var parent: Node? = null,
) {
    fun depth(): Int {
        if (this.parent == null) {
            return 0
        }

        return 1 + this.parent!!.depth()
    }

    fun size(): Int {
        val left = left?.size() ?: 0
        val right = right?.size() ?: 0

        return 1 + left + right
    }

    fun height(): Int {
        val left = left?.height() ?: 0
        val right = right?.height() ?: 0

        return 1 + max(left, right)
    }

    override fun toString(): String {
        return value.toString()
    }
}
