package me.yapoo.skipList

import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SkiplistSSetTest {
    @Test
    fun test() {
        val set = SkiplistSSet()
        val expected = List(1000) { Random.nextInt() }.toSet()
        expected.forEach {
            set.add(it)
        }
        expected.forEach {
            assertTrue { set.exist(it) }
            assertFalse { set.add(it) }
        }
        assertEquals(expected.size, set.size())
        expected.forEach {
            assertTrue { set.remove(it) }
            assertFalse { set.exist(it) }
        }
        assertEquals(0, set.size())
    }
}
