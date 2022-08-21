package me.yapoo.hashTable

import kotlin.random.Random
import kotlin.random.nextInt

class LinearHashTable<T> {
    private var t: MutableList<Node<T>> = mutableListOf()
    private var n = 0 // Value のノード数
    private var q = 0 // Value または Deleted のノード数
    private var d = 1 // t.length = 2^d

    private val table: List<List<Int>> = List(4) {
        List(256) {
            Random.nextInt(0..Int.MAX_VALUE)
        }
    }

    companion object {
        private const val w = 32
    }

    private sealed class Node<out T> {
        data class Value<T>(
            val value: T
        ) : Node<T>()

        object Deleted : Node<Nothing>()

        object Null : Node<Nothing>()
    }

    fun add(x: T): Boolean {
        if (find(x) != null) {
            return false
        }
        if (2 * (q + 1) > t.size) {
            resize()
        }
        var i = hash(x)
        while (t[i] is Node.Value) {
            i = if (i == t.size - 1) 0 else i + 1
        }
        if (t[i] == Node.Null) {
            q++
        }
        n++
        t[i] = Node.Value(x)
        return true
    }

    fun find(x: T): T? {
        var i = hash(x)
        while (i < t.size && t[i] !is Node.Null) {
            val node = t[i]
            if (node is Node.Value && node.value == x) {
                return node.value
            }
            i = if (i == t.size - 1) 0 else i + 1
        }
        return null
    }

    fun remove(x: T): T? {
        var i = hash(x)
        while (t[i] !is Node.Null) {
            val node = t[i]
            if (node is Node.Value && node.value == x) {
                t[i] = Node.Deleted
                n--
                if (8 * n < t.size) {
                    resize()
                }
                return node.value
            }
            i = if (i == t.size - 1) 0 else i + 1
        }
        return null
    }

    private fun hash(x: T): Int {
        val h = x.hashCode()
        return (table[0][h and 0xff] xor
                table[1][h.shr(8) and 0xff] xor
                table[2][h.shr(16) and 0xff] xor
                table[3][h.shr(24) and 0xff]).shr(w - d)
    }

    private fun resize() {
        d = 1
        while (1.shl(d) <= 3 * n) {
            d++
        }
        val new = MutableList<Node<T>>(1.shl(d)) { Node.Null }
        q = n
        for (node in t) {
            if (node is Node.Value) {
                var i = hash(node.value)
                while (new[i] != Node.Null) {
                    i = if (i == new.size - 1) 0 else i + 1
                }
                new[i] = Node.Value(node.value)
            }
        }
        t = new
    }
}
