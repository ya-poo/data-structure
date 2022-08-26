package me.yapoo.tree

class BinaryTrie : BinaryTree<Int?, BinaryTrie.Node>() {

    override var root: Node? = Node()

    private var n = 0

    fun size() = n

    fun find(x: Int): Node? {
        var u = root!!
        for (i in 0 until W) {
            val c = (x.shr(W - i - 1) and 1)
            u = if (c == 0) {
                u.left
            } else {
                u.right
            } ?: return null
        }

        return u
    }

    fun add(x: Int): Boolean {
        var u = root!!
        var i = 0
        var c = 0
        while (i < W) {
            c = (x.shr(W - i - 1) and 1)
            u = (if (c == 0) u.left else u.right) ?: break
            i++
        }
        if (i == W) {
            return false
        }

        val pred = (if (c == 1) u.jump else u.jump?.prev) ?: dummy
        u.jump = null

        // x への経路を追加する
        while (i < W) {
            c = (x.shr(W - i - 1) and 1)
            val node = Node()
            if (c == 0) {
                u.left = node
                node.value = 0
            } else {
                u.right = node
                node.value = 1
            }
            node.parent = u
            u = node
            i++
        }

        u.value = x
        u.prev = pred
        u.next = pred.next
        u.prev?.next = u
        u.next?.prev = u

        var v = u.parent
        while (v != null) {
            if (
                (v.left == null && (v.jump == null || (v.jump!!.value!! > x))) ||
                (v.right == null && (v.jump == null || (v.jump!!.value!! < x)))
            ) {
                v.jump = u
            }
            v = v.parent
        }
        n++
        return true
    }

    fun remove(x: Int): Boolean {
        var u = root!!
        for (i in 0 until W) {
            val c = (x.shr(W - i - 1) and 1)
            u = (if (c == 0) u.left else u.right) ?: return false
        }
        // u を連結リストから削除
        u.prev?.next = u.next
        u.next?.prev = u.prev

        // u を根から u への経路上のノードから削除する
        var v = u
        var i = W - 1
        while (i >= 0) {
            v = v.parent ?: break
            val c = (x.shr(W - i - 1) and 1)
            if (c == 0) {
                v.left = null
                if (v.right != null) {
                    v.jump = u.next
                    break
                }
            } else {
                v.right = null
                if (v.left != null) {
                    v.jump = u.prev
                    break
                }
            }
            i--
        }

        // jump ポインタを更新する
        while (i >= 0) {
            v = v.parent ?: break
            val c = (x.shr(W - i - 1) and 1)
            if (v.jump == u) {
                v.jump = if (c == 0) {
                    u.next
                } else {
                    u.prev
                }
            }
        }
        n--
        return true
    }

    class Node(
        value: Int? = null,
        parent: Node? = null,
        left: Node? = null,
        right: Node? = null,
        var jump: Node? = null,
        var prev: Node? = null,
        var next: Node? = null,
    ) : BTNode<Int?, Node>(value, left, right, parent) {
        override fun toString(): String {
            return "$value" + if (jump?.value != null) {
                " -> ${jump?.value}"
            } else ""
        }
    }

    private val dummy = Node()

    companion object {
        const val W = 32
    }
}
