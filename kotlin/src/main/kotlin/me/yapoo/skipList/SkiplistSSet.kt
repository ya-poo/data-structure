package me.yapoo.skipList

import kotlin.random.Random
import kotlin.random.nextInt

class SkiplistSSet(
    private var n: Int = 0,
    private var h: Int = 0,
    var sentinel: Node = Node(0, 32),
) {
    class Node(
        val x: Int,
        height: Int
    ) {
        val next = MutableList<Node?>(height + 1) { null }

        fun height(): Int = next.size - 1
    }

    fun size() = n

    fun exist(x: Int): Boolean {
        val node = findPredNode(x)
        return node.next[0]?.x == x
    }

    fun add(x: Int): Boolean {
        var u = sentinel
        val stack = MutableList(h + 1) { Node(0, 0) }
        for (r in h downTo 0) {
            while (u.next[r] != null && u.next[r]!!.x < x) {
                u = u.next[r]!!
            }

            if (u.next[r]?.x == x) {
                return false
            }
            stack[r] = u
        }

        val newNode = Node(x, pickHeight())
        while (h < newNode.height()) {
            h++
            stack.add(sentinel)
        }
        stack.add(sentinel)
        for (i in 0..newNode.height()) {
            newNode.next[i] = stack[i].next[i]
            stack[i].next[i] = newNode
        }
        n++
        return true
    }

    fun remove(x: Int): Boolean {
        var removed = false
        var u = sentinel
        for (r in h downTo 0) {
            while (u.next[r] != null && u.next[r]!!.x < x) {
                u = u.next[r]!!
            }

            if (u.next[r]?.x == x) {
                removed = true
                u.next[r] = u.next[r]!!.next[r]

                if (u == sentinel && u.next[r] == null) {
                    h--
                }
            }
        }
        if (removed) {
            n--
        }
        return removed
    }

    private fun findPredNode(x: Int): Node {
        var u = sentinel
        for (r in h downTo 0) {
            while (u.next[r] != null && u.next[r]!!.x < x) {
                u = u.next[r]!!
            }
        }
        return u
    }

    private fun pickHeight(): Int {
        val z = Random.nextInt(0..Int.MAX_VALUE)
        var k = 0
        var m = 1
        while ((z and m) != 0) {
            k++
            m = m shl 1
        }
        return k
    }
}
