package me.yapoo.tree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BinarySearchTreeTest {

    class Node<T : Comparable<*>>(
        value: T,
        left: Node<T>? = null,
        right: Node<T>? = null,
        parent: Node<T>? = null,
    ) : BSTNode<T, Node<T>>(value, left, right, parent)

    @Test
    fun test() {
        val tree = BinarySearchTree<Int, Node<Int>> { x -> Node(x) }
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
    fun tree_structure() {
        val tree = BinarySearchTree<Int, Node<Int>> { x -> Node(x) }
        val values = listOf(7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14)

        values.forEach {
            tree.add(it)
        }

        val expected = """
            7
            ├── 11
            │   ├── 13
            │   │   ├── 14
            │   │   └── 12
            │   └── 9
            │       ├── 10
            │       └── 8
            └── 3
                ├── 5
                │   ├── 6
                │   └── 4
                └── 1
                    ├── 2
                    └── 0
        """.trimIndent()

        assertEquals(expected, tree.toString())
    }
}
