package me.yapoo.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class MeldableHeapTest {

    @Test
    fun test() {
        val heap = MeldableHeap<Int>()
        val n = 1000
        val values = List(n) { it }.shuffled()

        values.withIndex().forEach { (index, value) ->
            heap.add(value)
            assertEquals(index + 1, heap.size())
            assertHeap(heap)
        }
        println(heap)
        values.sorted().withIndex().forEach { (index, value) ->
            assertEquals(value, heap.pop())
            assertEquals(n - index - 1, heap.size())
            assertHeap(heap)
        }
    }

    private fun assertHeap(heap: MeldableHeap<Int>) {
        val root = heap.root ?: return

        val stack = Stack<MeldableHeap.Node<Int>>()
        stack.push(root)
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            node.left?.let { left ->
                assertTrue { node.value < left.value }
            }
            node.right?.let { right ->
                assertTrue { node.value < right.value }
            }
        }
    }
}
