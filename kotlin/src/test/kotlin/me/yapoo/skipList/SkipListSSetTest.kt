package me.yapoo.skipList

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SkipListSSetTest {
    @Test
    fun test() {
        val set = SkipListSSet()
        for (i in 1..10) {
            set.add(i)
            assertEquals(i, set.size())
        }
        for (i in 1..10) {
            assertTrue { set.exist(i) }
        }
        for (i in 1..10) {
            assertFalse { set.add(i) }
        }
        assertEquals(10, set.size())

        for (i in 1..10) {
            assertTrue { set.remove(i) }
            assertFalse { set.exist(i) }
            assertEquals(10 - i, set.size())
        }
    }
}
