package me.yapoo.tree

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BinaryTreeTest {

    @Test
    fun test() {
        val nodes = List(11) { BinaryTree(it) }
        nodes[0].setLeft(nodes[1])
        nodes[0].setRight(nodes[6])
        nodes[1].setLeft(nodes[2])
        nodes[1].setRight(nodes[3])
        nodes[3].setLeft(nodes[4])
        nodes[3].setRight(nodes[5])
        nodes[6].setLeft(nodes[7])
        nodes[6].setRight(nodes[8])
        nodes[7].setLeft(nodes[9])
        nodes[8].setLeft(nodes[10])

        assertEquals(11, nodes[0].size())
        assertEquals(4, nodes[0].height())
        assertEquals(0, nodes[0].depth())

        assertEquals(5 , nodes[1].size())
        assertEquals(3, nodes[1].height())
        assertEquals(1, nodes[1].depth())

        assertEquals(1, nodes[10].size())
        assertEquals(1, nodes[10].height())
        assertEquals(3, nodes[10].depth())
    }
}
