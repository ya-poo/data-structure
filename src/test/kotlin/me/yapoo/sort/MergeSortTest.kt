package me.yapoo.sort

import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class MergeSortTest {
    @Test
    fun test() {
        val values = List(1000) { Random.nextInt() }
        val expected = values.sorted()
        val actual = values.mergeSort()

        assertEquals(1000, actual.size)
        actual.zip(expected).forEach { (e, a) ->
            assertEquals(e, a)
        }
    }
}
