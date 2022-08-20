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
        val values = listOf(10, 17, 4, 0, 5, 16, 18, 6, 13, 12, 11, 3, 14, 1, 2, 9, 15, 8, 7, 19)

        values.forEach {
            tree.add(it)
        }

        val expected = """
            10
            ├── 17
            │   ├── 18
            │   │   ├── 19
            │   │   └── null
            │   └── 16
            │       ├── null
            │       └── 13
            │           ├── 14
            │           │   ├── 15
            │           │   └── null
            │           └── 12
            │               ├── null
            │               └── 11
            └── 4
                ├── 5
                │   ├── 6
                │   │   ├── 9
                │   │   │   ├── null
                │   │   │   └── 8
                │   │   │       ├── null
                │   │   │       └── 7
                │   │   └── null
                │   └── null
                └── 0
                    ├── 3
                    │   ├── null
                    │   └── 1
                    │       ├── 2
                    │       └── null
                    └── null
        """.trimIndent()

        assertEquals(expected, tree.toString())
    }
}
