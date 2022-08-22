package me.yapoo.tree

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RedBlackTreeTest {

    @Test
    fun test() {
        val tree = RedBlackTree<Int>()
        val values = List(1000) { it }.shuffled()

        values.forEach {
            assertTrue { tree.add(it) }
            assertNotNull(tree.find(it))

            assertRootBlack(tree)
            assertBlackHeight(tree)
            assertNoRedEdge(tree)
            assertLeftLeaning(tree)
            assertNoDoubleBlack(tree)
        }

        values.shuffled().forEach {
            assertFalse { tree.add(it) }
            assertTrue { tree.remove(it) }
            assertNull(tree.find(it))

            assertRootBlack(tree)
            assertBlackHeight(tree)
            assertNoRedEdge(tree)
            assertLeftLeaning(tree)
            assertNoDoubleBlack(tree)
        }
    }

    private fun assertRootBlack(
        tree: RedBlackTree<*>
    ) {
        val root = tree.root ?: return
        assertEquals(RedBlackTree.Color.Black, root.color)
    }

    // 根から葉までの経路上の黒ノードの数はどの葉も同じ
    private fun assertBlackHeight(
        tree: RedBlackTree<*>
    ) {
        val root = tree.root ?: return

        val blackHeight = mutableSetOf<Int>()
        val stack = Stack<Pair<RedBlackTree.Node<*>, Int>>()
        stack.push(Pair(root, 1))
        while (stack.isNotEmpty()) {
            val (node, d) = stack.pop()

            val right = node.right
            val left = node.left
            if (right == null || left == null) {
                blackHeight.add(d + 1)
            }
            if (right != null) {
                stack.push(Pair(right, if (right.color == RedBlackTree.Color.Black) d + 1 else d))
            }
            if (left != null) {
                stack.push(Pair(left, if (left.color == RedBlackTree.Color.Black) d + 1 else d))
            }
        }
        assertEquals(1, blackHeight.size)
    }

    // 赤ノードは隣接しない
    private fun assertNoRedEdge(
        tree: RedBlackTree<*>
    ) {
        val root = tree.root ?: return

        val stack = Stack<RedBlackTree.Node<*>>()
        stack.push(root)
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            if (node.color == RedBlackTree.Color.Red) {
                assertContains(listOf(RedBlackTree.Color.Black, null), node.left?.color)
                assertContains(listOf(RedBlackTree.Color.Black, null), node.right?.color)
            }
            node.left?.let { stack.push(it) }
            node.right?.let { stack.push(it) }
        }
    }

    // 任意のノード u について、u.left が黒ノードなら u.right も黒ノード
    private fun assertLeftLeaning(
        tree: RedBlackTree<*>
    ) {
        val root = tree.root ?: return

        val stack = Stack<RedBlackTree.Node<*>>()
        stack.push(root)
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            if (node.left == null || node.left?.color == RedBlackTree.Color.Black) {
                assertContains(listOf(RedBlackTree.Color.Black, null), node.right?.color)
            }
            node.left?.let { stack.push(it) }
            node.right?.let { stack.push(it) }
        }
    }

    private fun assertNoDoubleBlack(
        tree: RedBlackTree<*>
    ) {
        val root = tree.root ?: return

        val stack = Stack<RedBlackTree.Node<*>>()
        stack.push(root)
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            assertNotEquals(RedBlackTree.Color.DoubleBlack, node.color)
            node.left?.let { stack.push(it) }
            node.right?.let { stack.push(it) }
        }
    }
}
