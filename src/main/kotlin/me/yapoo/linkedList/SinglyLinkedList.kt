package me.yapoo.linkedList

class SinglyLinkedList<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var n = 0

    fun pushFront(x: T) {
        val u = Node(x)
        u.next = head
        head = u
        if (n == 0) {
            tail = u
        }
        n++
    }

    fun pop(): T? {
        val x = head?.value ?: return null
        head = head!!.next
        if (n == 1) {
            tail = null
        }
        n -= 1
        return x
    }

    fun pushBack(x: T) {
        val u = Node(x)
        if (n == 0) {
            head = u
        } else {
            tail!!.next = u
        }
        tail = u
        n++
    }

    fun size(): Int {
        return n
    }

    fun reverse() {
        if (head == null) {
            return
        }
        val (h, t) = reverseInternal(head!!)
        this.head = h
        this.tail = t
    }

    private fun reverseInternal(
        u: Node<T>
    ): Pair<Node<T>, Node<T>> {
        if (u.next == null) {
            return u to u
        }

        val (h, t) = reverseInternal(u.next!!)
        u.next = null
        t.next = u
        return h to u
    }

    class Node<T>(
        val value: T,
        var next: Node<T>? = null
    )
}
