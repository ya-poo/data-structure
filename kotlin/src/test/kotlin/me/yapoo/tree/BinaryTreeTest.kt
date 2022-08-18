package me.yapoo.tree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BinaryTreeTest {

    @Test
    fun test() {
        val tree = BinarySearchTree<Int>()
        val values = List(1000) { it }.shuffled()

        values.forEach {
            assertTrue { tree.add(it) }
            assertEquals(it, tree.find(it)?.value)
        }
        values.forEach {
            assertTrue { tree.remove(it) }
            assertNull(tree.find(it))
        }
    }

    @Test
    fun tree_structure() {
        val tree = BinarySearchTree<Int>()
        val values = listOf(7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14)

        values.forEach {
            tree.add(it)
        }

        assertNull(tree.find(7)?.parent)
        assertEquals(3, tree.find(7)?.left?.value)
        assertEquals(11, tree.find(7)?.right?.value)
        assertEquals(0, tree.find(7)?.depth())
        assertEquals(15, tree.find(7)?.size())
        assertEquals(4, tree.find(7)?.height())

        assertEquals(7, tree.find(3)?.parent?.value)
        assertEquals(1, tree.find(3)?.left?.value)
        assertEquals(5, tree.find(3)?.right?.value)

        assertEquals(7, tree.find(11)?.parent?.value)
        assertEquals(9, tree.find(11)?.left?.value)
        assertEquals(13, tree.find(11)?.right?.value)

        assertEquals(3, tree.find(1)?.parent?.value)
        assertEquals(0, tree.find(1)?.left?.value)
        assertEquals(2, tree.find(1)?.right?.value)

        assertEquals(3, tree.find(5)?.parent?.value)
        assertEquals(4, tree.find(5)?.left?.value)
        assertEquals(6, tree.find(5)?.right?.value)

        assertEquals(11, tree.find(9)?.parent?.value)
        assertEquals(8, tree.find(9)?.left?.value)
        assertEquals(10, tree.find(9)?.right?.value)

        assertEquals(11, tree.find(13)?.parent?.value)
        assertEquals(12, tree.find(13)?.left?.value)
        assertEquals(14, tree.find(13)?.right?.value)

        assertEquals(1, tree.find(0)?.parent?.value)
        assertNull(tree.find(0)?.left)
        assertNull(tree.find(0)?.right)

        assertEquals(1, tree.find(2)?.parent?.value)
        assertNull(tree.find(2)?.left)
        assertNull(tree.find(2)?.right)

        assertEquals(5, tree.find(4)?.parent?.value)
        assertNull(tree.find(4)?.left)
        assertNull(tree.find(4)?.right)

        assertEquals(5, tree.find(6)?.parent?.value)
        assertNull(tree.find(6)?.left)
        assertNull(tree.find(6)?.right)

        assertEquals(9, tree.find(8)?.parent?.value)
        assertNull(tree.find(8)?.left)
        assertNull(tree.find(8)?.right)

        assertEquals(9, tree.find(10)?.parent?.value)
        assertNull(tree.find(10)?.left)
        assertNull(tree.find(10)?.right)

        assertEquals(13, tree.find(12)?.parent?.value)
        assertNull(tree.find(12)?.left)
        assertNull(tree.find(12)?.right)

        assertEquals(13, tree.find(14)?.parent?.value)
        assertNull(tree.find(14)?.left)
        assertNull(tree.find(14)?.right)
    }
}
