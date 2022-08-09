package me.yapoo.skipList

import kotlin.random.Random
import kotlin.random.nextInt

class SkiplistList {
    private var n: Int = 0
    private var h: Int = 0
    private var sentinel: Node = Node(0, 32)

    class Node(
        var x: Int,
        height: Int,
    ) {
        val next = MutableList<Node?>(height + 1) { null }
        val length = MutableList(height + 1) { 0 }

        val height: Int = next.size - 1
    }

    fun size() = n

    fun get(i: Int): Int {
        return findPredNode(i).next[0]!!.x
    }

    fun set(i: Int, x: Int): Int {
        val node = findPredNode(i).next[0]
        val y = node!!.x
        node.x = x
        return y
    }

    fun add(i: Int, x: Int) {
        val node = Node(x, pickHeight())
        if (node.height > h) {
            h = node.height
        }
        addNode(i, node)
    }

    private fun findPredNode(i: Int): Node {
        var u = sentinel
        var j = -1
        for (r in h downTo 0) {
            while (u.next[r] != null && j + u.length[r] < i) {
                j += u.length[r]
                u = u.next[r]!!
            }
        }
        return u
    }

    private fun addNode(i: Int, node: Node) {
        var u = sentinel
        val k = node.height
        var j = -1
        for (r in h downTo 0) {
            while (u.next[r] != null && j + u.length[r] < i) {
                j += u.length[r]
                u = u.next[r]!!
            }
            if (r <= k) {
                node.next[r] = u.next[r]
                u.next[r] = node
                node.length[r] = u.length[r] + 1 - (i - j)
                u.length[r] = i - j
            } else {
                u.length[r]++
            }
        }
        n++
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
