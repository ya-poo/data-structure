package me.yapoo.heap

import kotlin.math.max

class BinaryHeap<T : Comparable<*>>(
    list: List<T> = emptyList()
) {
    var a: MutableList<T?>
    var n: Int

    init {
        a = list.toMutableList()
        n = a.size
        for (i in n / 2 - 1 downTo 0) {
            trickleDown(i)
        }
    }

    private fun left(i: Int) = 2 * i + 1
    private fun right(i: Int) = 2 * i + 2
    private fun parent(i: Int) = (i - 1) / 2

    fun add(x: T) {
        if (n + 1 > a.size) {
            resize()
        }
        a[n] = x
        n++
        bubbleUp(n - 1)
    }

    fun pop(): T? {
        if (n == 0) {
            return null
        }
        val x = a[0]!!
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
        while (cur > 0 && compareValues(a[cur]!!, a[p]!!) < 0) {
            swap(cur, p)
            cur = p
            p = parent(cur)
        }
    }

    fun trickleDown(i: Int = 0) {
        var cur = i
        do {
            var j = -1
            val r = right(cur)
            val l = left(cur)
            if (r < n && compareValues(a[r]!!, a[cur]!!) < 0) {
                j = if (compareValues(a[l]!!, a[r]!!) < 0) {
                    l
                } else {
                    r
                }
            } else {
                if (l < n && compareValues(a[l]!!, a[cur]!!) < 0) {
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
                null
            }
        }
        a = new
    }

    private fun swap(i: Int, j: Int) {
        val memo = a[i]
        a[i] = a[j]
        a[j] = memo
    }
}
