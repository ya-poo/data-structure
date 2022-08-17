package me.yapoo.tree

import kotlin.math.max

data class BinaryTree<T>(
    val value: T,
    var parent: BinaryTree<T>? = null,
    private var left: BinaryTree<T>? = null,
    private var right: BinaryTree<T>? = null,
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

    fun setLeft(node: BinaryTree<T>) {
        this.left = node
        node.parent = this
    }

    fun setRight(node: BinaryTree<T>) {
        this.right = node
        node.parent = this
    }
}
