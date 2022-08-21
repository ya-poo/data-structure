package me.yapoo.linkedList

class DoublyLinkedList<T : Any> {
    private val dummy = Node<T>(null, null, null)
    private var n = 0

    init {
        dummy.prev = dummy
        dummy.next = dummy
    }

    fun size(): Int {
        return n
    }

    fun get(i: Int): T? {
        return getNode(i)?.value
    }

    fun set(i: Int, x: T): T? {
        val u = getNode(i) ?: return null
        val y = u.value!!
        u.value = x
        return y
    }

    fun add(i: Int, x: T): Boolean {
        val w = getNode(i) ?: return false
        addBefore(w, x)
        return true
    }

    fun pushFront(x: T) {
        val u = Node(x)
        val w = dummy.next!!
        u.next = w
        w.prev = u
        u.prev = dummy
        dummy.next = u
        n++
    }

    fun pushBack(x: T) {
        val u = Node(x)
        val w = dummy.prev!!
        u.next = dummy
        dummy.prev = u
        w.next = u
        u.prev = w
        n++
    }

    fun remove(i: Int): T? {
        val w = getNode(i) ?: return null
        val x = w.value!!
        remove(w)
        return x
    }

    private fun getNode(i: Int): Node<T>? {
        if (i >= n) {
            return null
        }
        return if (i < n / 2) {
            var p = dummy.next
            for (j in 0 until i) {
                p = p!!.next
            }
            p!!
        } else {
            var p: Node<T>? = dummy
            for (j in n downTo i + 1) {
                p = p!!.prev
            }
            p!!
        }
    }

    private fun addBefore(w: Node<T>, x: T): Node<T> {
        val u = Node(x)
        u.prev = w.prev
        u.next = w
        u.next!!.prev = u
        u.prev!!.next = u
        n++

        return u
    }

    private fun remove(w: Node<T>) {
        w.prev!!.next = w.next
        w.next!!.prev = w.prev
        n--
    }

    class Node<T : Any>(
        var value: T?, // null when this node is dummy
        var prev: Node<T>? = null, // null only when initializing
        var next: Node<T>? = null, // null only when initializing
    )
}
