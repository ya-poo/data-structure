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

    @Test
    fun calculate_depth_map() {
        val tree = ScapegoatTree<Int>()
        val values = List(10000) { it }
        values.forEach {
            assertTrue { tree.add(it) }
        }

        val depth = mutableMapOf<Int, Int>()
        values.forEach {
            val d = tree.find(it)!!.depth()
            if (depth.contains(d)) {
                depth[d] = depth[d]!! + 1
            } else {
                depth[d] = 1
            }
        }
        println(depth.toSortedMap())
    }
}
