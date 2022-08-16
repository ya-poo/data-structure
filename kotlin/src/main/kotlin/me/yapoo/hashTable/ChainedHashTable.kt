package me.yapoo.hashTable

import kotlin.random.Random

class ChainedHashTable<T> {
    private var n: Int = 0
    private var t: MutableList<MutableList<T>> = mutableListOf()
    private val z = Random.nextInt(0, Int.MAX_VALUE) and 1
    private var dimension = 1

    companion object {
        private const val w = 32
    }

    fun add(x: T): Boolean {
        if (find(x) != null) {
            return false
        }
        if (n + 1 > t.size) {
            resize()
        }
        t[hash(x)].add(x)
        n++
        return true
    }

    fun find(x: T): T? {
        val j = hash(x)
        if (j >= t.size) {
            return null
        }
        for (y in t[j]) {
            if (y == x) {
                return y
            }
        }

        return null
    }

    fun remove(x: T): T? {
        val j = hash(x)
        for (i in 0..t[j].size) {
            val y = t[j][i]
            if (x == y) {
                t[j].removeAt(i)
                n--
                return y
            }
        }
        return null
    }

    private fun hash(x: T): Int {
        return (z * x.hashCode()).mod(1.shl(w)) / 1.shl(w - dimension)
    }

    private fun resize() {
        dimension = 1
        while (1.shl(dimension) <= n) {
            dimension++
        }
        val new = MutableList(1.shl(dimension)) { mutableListOf<T>() }
        t.forEach { list ->
            list.forEach { x ->
                new[hash(x)].add(x)
            }
        }
        t = new
    }
}
