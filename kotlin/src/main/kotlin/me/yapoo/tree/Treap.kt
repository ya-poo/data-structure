package me.yapoo.tree

import kotlin.random.Random

class Treap<T : Comparable<*>> {

    fun find(x: T): Node<T>? {
        val last = findLast(x) ?: return null
        return if (compareValues(x, last.value) == 0) {
            last
        } else {
            null
        }
    }

    fun add(x: T): Boolean {
        val new = Node(value = x, priority = Random.nextInt())
        if (addNode(new)) {
            bubbleUp(new)
            return true
        }
        return false
    }

    fun remove(x: T): Boolean {
        val u = findLast(x) ?: return false
        if (compareValues(u.value, x) != 0) {
            return false
        }

        trickleDown(u)
        splice(u)
        return true
    }

    // priority を無視してそのノードを葉ノードに移す
    private fun trickleDown(u: Node<T>) {
        while (u.left != null || u.right != null) {
            if (u.left == null) {
                rotateLeft(u)
            } else if (u.right == null) {
                rotateRight(u)
            } else if (u.left!!.priority < u.right!!.priority) {
                rotateRight(u)
            } else {
                rotateLeft(u)
            }
        }
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

    // 指定したノードを priority が適切な位置まで移動
    private fun bubbleUp(u: Node<T>) {
        while (u.parent != null && u.parent!!.priority > u.priority) {
            if (u.parent!!.right == u) {
                rotateLeft(u.parent!!)
            } else {
                rotateRight(u.parent!!)
            }
        }
    }

    // priority を無視して値のみを考慮して葉ノードに値を追加
    private fun addNode(new: Node<T>): Boolean {
        val parent = findLast(new.value)
        if (parent == null) {
            root = new
            return true
        }

        val comp = compareValues(new.value, parent.value)
        if (comp == 0) {
            return false
        }
        new.parent = parent
        if (comp < 0) {
            parent.left = new
        } else {
            parent.right = new
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

    private fun rotateLeft(
        u: Node<T>
    ) {
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

    private fun rotateRight(
        u: Node<T>
    ) {
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

    private var root: Node<T>? = null

    class Node<T : Comparable<*>>(
        var value: T,
        var priority: Int,
        var parent: Node<T>? = null,
        var left: Node<T>? = null,
        var right: Node<T>? = null,
    )
}
