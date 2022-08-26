package me.yapoo.tree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue


class BinaryTrieTest {
    @Test
    fun test() {
        val values = List(1000) { it }.shuffled()
        val tree = BinaryTrie()

        values.forEach {
            assertTrue { tree.add(it) }
            assertEquals(it, tree.find(it)?.value)
        }

        values.shuffled().forEach {
            assertEquals(it, tree.find(it)?.value)
            assertTrue { tree.remove(it) }
            assertNull(tree.find(it))
        }
    }
}
