package me.yapoo.linkedList

import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class DoublyLinkedListTest {
    @Test
    fun push_front() {
        val values = List(1000) { it }
        val list = DoublyLinkedList<Int>()

        values.withIndex().forEach { (index, value) ->
            list.pushFront(value)
            assertEquals(index + 1, list.size())
        }
        values.reversed().withIndex().forEach { (value, index) ->
            assertEquals(value, list.get(index))
        }
    }

    @Test
    fun push_back() {
        val values = List(1000) { it }
        val list = DoublyLinkedList<Int>()

        values.withIndex().forEach { (index, value) ->
            list.pushBack(value)
            assertEquals(index + 1, list.size())
        }
        values.withIndex().forEach { (index, value) ->
            assertEquals(value, list.get(index))
        }
    }

    @Test
    fun remove() {
        val values = MutableList(1000) { it }
        val list = DoublyLinkedList<Int>()

        values.forEach {
            list.pushBack(it)
        }

        while (values.isNotEmpty()) {
            val i = Random.nextInt(0, values.size)
            assertEquals(values[i], list.remove(i))
            values.removeAt(i)
        }
    }

    @Test
    fun set() {
        val values1 = List(1000) { it }
        val list = DoublyLinkedList<Int>()

        values1.forEach {
            list.pushBack(it)
        }

        val values2 = values1.shuffled()
        values2.withIndex().forEach { (index, value) ->
            assertEquals(values1[index], list.set(index, value))
        }
        values2.withIndex().forEach { (index, value) ->
            assertEquals(value, list.get(index))
        }
    }
}
