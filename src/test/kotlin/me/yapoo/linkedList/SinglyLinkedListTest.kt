package me.yapoo.linkedList

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SinglyLinkedListTest {

    @Test
    fun push_front_and_pop() {
        val values = List(1000) { it }
        val list = SinglyLinkedList<Int>()

        values.withIndex().forEach { (value, index) ->
            list.pushFront(value)
            assertEquals(index + 1, list.size())
        }
        values.reversed().forEach {
            assertEquals(it, list.pop())
        }
    }

    @Test
    fun push_back_and_pop() {
        val values = List(1000) { it }
        val list = SinglyLinkedList<Int>()
        values.withIndex().forEach { (value, index) ->
            list.pushBack(value)
            assertEquals(index + 1, list.size())
        }
        values.forEach {
            assertEquals(it, list.pop())
        }
    }

    @Test
    fun reverse_should_works() {
        val values = List(10) { it }.shuffled()
        val list = SinglyLinkedList<Int>()
        values.forEach {
            list.pushFront(it)
        }
        list.reverse()
        values.forEach {
            assertEquals(it, list.pop())
        }
    }
}
