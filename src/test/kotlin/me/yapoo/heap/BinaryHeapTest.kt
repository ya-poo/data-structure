package me.yapoo.heap

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BinaryHeapTest {
    @Test
    fun test() {
        val heap = BinaryHeap<Int>()
        val n = 10000
        val values = List(n) { it }.shuffled()

        values.withIndex().forEach { (index, value) ->
            heap.add(value)
            assertEquals(index + 1, heap.size())
            assertHeap(0, heap)
        }
        values.sorted().withIndex().forEach { (index, value) ->
            assertEquals(value, heap.pop())
            assertEquals(n - index - 1, heap.size())
            assertHeap(0, heap)
        }
    }

    private fun assertHeap(i: Int, heap: BinaryHeap<Int>) {
        val array = heap.raw()
        val n = heap.size()
        val left = 2 * i + 1
        val right = 2 * i + 1
        if (left < n) {
            assertTrue { array[i].unwrap() < array[left].unwrap() }
            assertHeap(left, heap)
        }
        if (right < n) {
            assertTrue { array[i].unwrap() < array[right].unwrap() }
            assertHeap(right, heap)
        }
    }
}
