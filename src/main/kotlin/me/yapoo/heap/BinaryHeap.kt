package me.yapoo.heap

import kotlin.math.max

class BinaryHeap<T : Comparable<*>> {
    private var a: MutableList<Node<T>> = mutableListOf()
    private var n = 0

    private fun left(i: Int) = 2 * i + 1
    private fun right(i: Int) = 2 * i + 2
    private fun parent(i: Int) = (i - 1) / 2

    fun add(x: T) {
        if (n + 1 > a.size) {
            resize()
        }
        a[n] = Node.Value(x)
        n++
        bubbleUp(n - 1)
    }

    fun pop(): T? {
        if (n == 0) {
            return null
        }
        val x = a[0].unwrap()
        a[0] = a[n - 1]
        n--
        trickleDown()
        if (3 * n < a.size) {
            resize()
        }
        return x
    }

    fun size(): Int = n

    fun raw() = a.toList()

    private fun bubbleUp(i: Int) {
        var cur = i
        var p = parent(cur)
        while (cur > 0 && compareValues(a[cur].unwrap(), a[p].unwrap()) < 0) {
            swap(cur, p)
            cur = p
            p = parent(cur)
        }
    }

    private fun trickleDown() {
        var cur = 0
        do {
            var j = -1
            val r = right(cur)
            val l = left(cur)
            if (r < n && compareValues(a[r].unwrap(), a[cur].unwrap()) < 0) {
                j = if (compareValues(a[l].unwrap(), a[r].unwrap()) < 0) {
                    l
                } else {
                    r
                }
            } else {
                if (l < n && compareValues(a[l].unwrap(), a[cur].unwrap()) < 0) {
                    j = l
                }
            }
            if (j >= 0) {
                swap(cur, j)
            }
            cur = j
        } while (cur >= 0)
    }

    private fun resize() {
        val new = MutableList(max(2 * n, 1)) { index ->
            if (index < n) {
                a[index]
            } else {
                Node.Empty
            }
        }
        a = new
    }

    private fun swap(i: Int, j: Int) {
        val memo = a[i]
        a[i] = a[j]
        a[j] = memo
    }

    sealed class Node<out T : Comparable<*>> {

        data class Value<T : Comparable<*>>(
            val value: T
        ) : Node<T>()

        object Empty : Node<Nothing>()

        fun unwrap(): T {
            return when (this) {
                is Value -> value
                is Empty -> throw Exception()
            }
        }
    }
}
