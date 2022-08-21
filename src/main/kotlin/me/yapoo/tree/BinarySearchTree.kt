package me.yapoo.tree

import kotlin.math.abs
import kotlin.math.max

open class BinarySearchTree<T : Comparable<*>, Node : BSTNode<T, Node>>(
    private val factory: (T) -> Node
) {

    var root: Node? = null

    fun find(x: T): Node? {
        var w = root
        while (w != null) {
            val comp = compareValues(x, w.value)
            w = if (comp < 0) {
                w.left
            } else if (comp > 0) {
                w.right
            } else {
                return w
            }
        }
        return null
    }

    open fun add(x: T): Boolean {
        return add(factory(x))
    }

    open fun add(u: Node): Boolean {
        val parent = findLast(u.value)
        if (parent == null) {
            root = u
            return true
        }

        val comp = compareValues(u.value, parent.value)
        if (comp == 0) {
            return false
        }

        u.parent = parent
        if (comp < 0) {
            parent.left = u
        } else {
            parent.right = u
        }
        return true
    }

    open fun remove(x: T): Boolean {
        val node = find(x) ?: return false

        if (node.left == null || node.right == null) {
            splice(node)
        } else {
            var w = node.right
            while (w?.left != null) {
                w = w.left
            }

            node.value = w!!.value
            splice(w)
        }

        return true
    }

    // x を持つノード または x 未満の最大の値、または x より大きい最小のノードを返却
    // 空の木の場合は null を返却
    fun findLast(x: T): Node? {
        var current = root
        var prev: Node? = null
        while (current != null) {
            prev = current

            val comp = compareValues(x, current.value)
            current = if (comp < 0) {
                current.left
            } else if (comp > 0) {
                current.right
            } else {
                return current
            }
        }

        return prev
    }

    // 葉または子を 1 つだけ持つノードを削除する
    fun splice(u: Node) {
        if (u.left != null && u.right != null) {
            throw IllegalArgumentException("u の子が 2 つあります。 u = $u\n$this")
        }

        val s = if (u.left != null) {
            u.left
        } else {
            u.right
        }

        if (u.parent == null) {
            root = s
            root?.parent = null
        } else {
            val p = u.parent!!
            if (p.left == u) {
                p.left = s
            } else {
                p.right = s
            }
            if (s != null) {
                s.parent = p
            }
        }
    }

    fun rotateLeft(u: Node) {
        val w = u.right ?: return
        w.parent = u.parent

        val p = w.parent
        if (p != null) {
            if (p.left == u) {
                p.left = w
            } else {
                p.right = w
            }
        }
        u.right = w.left
        if (u.right != null) {
            u.right?.parent = u
        }
        u.parent = w
        w.left = u
        if (u == root) {
            root = w
            root?.parent = null
        }
    }

    fun rotateRight(u: Node) {
        val w = u.left ?: return
        w.parent = u.parent

        val p = w.parent
        if (p != null) {
            if (w.parent?.left == u) {
                w.parent?.left = w
            } else {
                w.parent?.right = w
            }
        }
        u.left = w.right
        if (u.left != null) {
            u.left?.parent = u
        }
        u.parent = w
        w.right = u
        if (u == root) {
            root = w
            root?.parent = null
        }
    }

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

open class BSTNode<T : Comparable<*>, Node : BSTNode<T, Node>>(
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
