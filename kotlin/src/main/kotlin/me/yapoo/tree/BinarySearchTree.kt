package me.yapoo.tree

import kotlin.math.max


class BinarySearchTree<T : Comparable<*>> {

    var root: Node<T>? = null

    fun find(x: T): Node<T>? {
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

    fun add(x: T): Boolean {
        val parent = findLast(x)
        if (parent == null) {
            root = Node(x)
            return true
        }

        val comp = compareValues(x, parent.value)
        if (comp == 0) {
            return false
        }

        val new = Node(x)
        new.parent = parent
        if (comp < 0) {
            parent.left = new
        } else {
            parent.right = new
        }
        return true
    }

    fun remove(x: T): Boolean {
        val node = find(x) ?: return false
        if (node.left == null || node.right == null) {
            splice(node)
        } else {
            var w: Node<T>? = node.right
            while (w?.left != null) {
                w = w.left
            }

            node.value = w!!.value
            splice(w)
        }

        return true
    }

    // x を持つノード または x 未満の最大の数を持つ葉ノードを返却
    private fun findLast(x: T): Node<T>? {
        var current = root
        var prev: Node<T>? = null
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
    private fun splice(u: Node<T>) {
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

    class Node<T : Comparable<*>>(
        var value: T,
        var parent: Node<T>? = null,
        var left: Node<T>? = null,
        var right: Node<T>? = null,
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
    }
}
