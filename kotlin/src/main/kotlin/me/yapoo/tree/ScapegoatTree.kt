package me.yapoo.tree

import kotlin.math.ceil
import kotlin.math.log

class ScapegoatTree<T : Comparable<*>> {
    private val internal = BinarySearchTree<T>()
    private var q = 0
    private var n = 0

    fun find(x: T): BinarySearchTree.Node<T>? {
        return internal.find(x)
    }

    fun add(x: T): Boolean {
        val depth = addWithDepth(BinarySearchTree.Node(x))
        if (depth == -1) {
            return false
        }
        q++
        n++
        val u = internal.find(x)!!
        if (depth > log32(q)) {
            var w = u.parent!!
            var a = w.size()
            var b = w.parent!!.size()
            while (3 * a <= 2 * b) {
                w = w.parent!!
                a = w.size()
                b = w.parent!!.size()
            }
            rebuild(w)
        }

        return true
    }

    fun remove(x: T): Boolean {
        if (!internal.remove(x)) {
            return false
        }

        n--
        if (2 * n < q) {
            internal.root?.let {
                rebuild(it)
                q = n
            }
        }
        return true
    }

    // 挿入し、挿入したノードの深さを返却する。挿入できなかった場合は -1 を返す。
    private fun addWithDepth(
        u: BinarySearchTree.Node<T>
    ): Int {
        var current = internal.root
        if (current == null) {
            internal.root = u
            return 0
        }

        var d = 0
        var prev: BinarySearchTree.Node<T> = current
        while (current != null) {
            prev = current
            val comp = compareValues(u.value, current.value)
            current = if (comp < 0) {
                current.left
            } else if (comp > 0) {
                current.right
            } else {
                return -1
            }
            d++
        }

        val comp = compareValues(u.value, prev.value)
        if (comp < 0) {
            prev.left = u
            u.parent = prev
        } else {
            prev.right = u
            u.parent = prev
        }
        return d
    }

    private fun rebuild(
        u: BinarySearchTree.Node<T>
    ) {
        if (u.left == null && u.right == null) {
            return
        }
        val p = u.parent
        val array = packIntoArray(u)
        if (p == null) {
            internal.root = buildBalanced(array)
            internal.root!!.parent = null
        } else if (p.right == u) {
            val balanced = buildBalanced(array)
            p.right = balanced
            p.right!!.parent = p
        } else {
            p.left = buildBalanced(array)
            p.left!!.parent = p
        }
    }

    private fun packIntoArray(
        u: BinarySearchTree.Node<T>,
    ): List<BinarySearchTree.Node<T>> {
        val left = u.left?.let { packIntoArray(it) } ?: emptyList()
        val mid = listOf(u)
        val right = u.right?.let { packIntoArray(it) } ?: emptyList()

        return left + mid + right
    }

    private fun buildBalanced(
        a: List<BinarySearchTree.Node<T>>
    ): BinarySearchTree.Node<T> {
        val m = a.size / 2
        val left = a.subList(0, m).ifEmpty { null }
        val right = a.subList(m + 1, a.size).ifEmpty { null }

        a[m].parent = null
        a[m].left = null
        a[m].right = null
        if (left != null) {
            a[m].left = buildBalanced(left)
            a[m].left!!.parent = a[m]
        }
        if (right != null) {
            a[m].right = buildBalanced(right)
            a[m].right!!.parent = a[m]
        }

        return a[m]
    }

    private fun log32(n: Int): Int {
        return ceil(log(n.toDouble(), 1.5)).toInt()
    }
}
