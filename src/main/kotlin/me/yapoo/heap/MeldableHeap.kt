package me.yapoo.heap

import me.yapoo.tree.BTNode
import me.yapoo.tree.BinaryTree
import kotlin.random.Random

class MeldableHeap<T : Comparable<*>> : BinaryTree<T, MeldableHeap.Node<T>>() {

    private var n = 0

    fun add(x: T) {
        val node = Node(x)
        root = if (root == null) {
            node
        } else {
            merge(node, root)
        }
        root!!.parent = null
        n++
    }

    fun pop(): T? {
        if (n == 0) {
            return null
        }
        val x = root!!.value
        root = merge(root?.left, root?.right)
        root?.parent = null
        n--
        return x
    }

    fun size(): Int = n

    private fun merge(h1: Node<T>?, h2: Node<T>?): Node<T>? {
        if (h1 == null) return h2
        if (h2 == null) return h1
        if (compareValues(h1.value, h2.value) > 0) {
            return merge(h2, h1)
        }

        if (Random.nextInt() % 2 == 0) {
            h1.left = h1.left?.let { merge(it, h2) } ?: h2
            h1.left?.parent = h1
        } else {
            h1.right = h1.right?.let { merge(it, h2) } ?: h2
            h1.right?.parent = h1
        }
        return h1
    }

    class Node<T : Comparable<*>>(
        value: T,
        left: Node<T>? = null,
        right: Node<T>? = null
    ) : BTNode<T, Node<T>>(value, left, right)
}
