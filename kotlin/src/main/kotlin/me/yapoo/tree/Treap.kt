package me.yapoo.tree

import kotlin.random.Random

class Treap<T : Comparable<*>> : BinarySearchTree<T, Treap.Node<T>>(
    factory = { Node(it) }
) {

    override fun add(x: T): Boolean {
        return add(Node(x))
    }

    override fun add(u: Node<T>): Boolean {
        val result = super.add(u)
        if (result) {
            bubbleUp(u)
            return true
        }
        return false
    }

    override fun remove(x: T): Boolean {
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

    class Node<T : Comparable<*>>(
        value: T,
        left: Node<T>? = null,
        right: Node<T>? = null,
        parent: Node<T>? = null,
        val priority: Int = Random.nextInt(),
    ) : BSTNode<T, Node<T>>(value, left, right, parent) {
        override fun toString(): String {
            return "$value ($priority)"
        }
    }
}
