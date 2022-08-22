package me.yapoo.sort

import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class SortTest {
    @Test
    fun test() {
        val values = List(1000) { Random.nextInt() }

        assertSorted(values, values.mergeSort())
        assertSorted(values, values.quickSort())
    }

    private fun assertSorted(
        values: List<Int>,
        actual: List<Int>
    ) {
        assertEquals(values.size, actual.size)

        val expected = values.sorted()
        actual.zip(expected).forEach { (e, a) ->
            assertEquals(e, a)
        }
    }
}
