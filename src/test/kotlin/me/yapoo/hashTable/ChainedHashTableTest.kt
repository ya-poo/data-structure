package me.yapoo.hashTable

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ChainedHashTableTest {
    @Test
    fun test() {
        val table = ChainedHashTable<Char>()
        for (c in 'a'..'z') {
            table.add(c)
            assertEquals(c, table.find(c))
        }
        for (c in 'a'..'z') {
            assertEquals(c, table.find(c))
        }
        for (c in 'a'..'z') {
            table.remove(c)
            assertEquals(null, table.find(c))
        }
    }
}
