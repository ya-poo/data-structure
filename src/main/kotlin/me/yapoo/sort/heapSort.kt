package me.yapoo.sort

import me.yapoo.heap.BinaryHeap
import me.yapoo.util.swap

fun <T : Comparable<*>> List<T>.heapSort(): List<T> {
    val heap = BinaryHeap(this)
    while (heap.n > 1) {
        heap.a.swap(heap.n - 1, 0)
        heap.n--
        heap.trickleDown()
    }
    return heap.a.filterNotNull().toList().reversed()
}
