package me.yapoo.skipList

import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class SkiplistListTest {
    @Test
    fun test() {
        val list = SkiplistList()
        val size = 1000
        val expected1 = List(size) { Random.nextInt() }
        expected1.withIndex().forEach { (i, value) ->
            list.add(i, value)
        }
        expected1.withIndex().forEach { (i, value) ->
            assertEquals(list.get(i), value)
        }
        assertEquals(list.size(), size)

        val expected2 = List(size) { Random.nextInt() }
        expected2.withIndex().forEach { (i, value) ->
            list.set(i, value)
        }
        expected2.withIndex().forEach { (i, value) ->
            assertEquals(list.get(i), value)
        }

        expected2.withIndex().forEach { (_, value) ->
            assertEquals(value, list.remove(0))
        }
    }
}
