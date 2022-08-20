package me.yapoo.tree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ScapegoatTreeTest {
    @Test
    fun test() {
        val tree = ScapegoatTree<Int>()
        val values = List(10000) { it }.shuffled()

        values.forEach {
            assertTrue { tree.add(it) }
            assertEquals(it, tree.find(it)?.value)
            assertFalse { tree.add(it) }
        }
        values.forEach {
            assertTrue { tree.remove(it) }
            assertNull(tree.find(it))
        }
    }
}
