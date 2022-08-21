package me.yapoo.tree

class RedBlackTree<T : Comparable<*>> : BinarySearchTree<T, RedBlackTree.Node<T>>(
    factory = { x -> Node(x, Color.Red) }
) {

    override fun add(x: T): Boolean {
        val u = Node(x, Color.Red)
        return add(u)
    }

    override fun add(u: Node<T>): Boolean {
        val added = super.add(u)
        if (added) {
            addFixup(u)
        }
        return added
    }

    override fun remove(x: T): Boolean {
        var u = findLast(x)
        if (u == null || compareValues(x, u.value) != 0) {
            return false
        }
        var w = u.right
        var spliceLeaf = false
        if (w == null) {
            w = u
            u = w.left ?: Node(x, Color.Black).also {
                // w が葉の場合は u の位置にダミーのノードを挿入する
                spliceLeaf = true
            }
            w.left = u
        } else {
            while (w?.left != null) {
                w = w.left
            }
            u.value = w!!.value
            u = w.right
            u = u ?: Node(x, Color.Black).also {
                // w が葉の場合は u の位置にダミーのノードを挿入する
                spliceLeaf = true
            }
            w.right = u
        }
        splice(w)
        u.parent = w.parent
        u.color = if (u.color == Color.Black && w.color == Color.Black) {
            Color.DoubleBlack
        } else {
            Color.Black
        }
        removeFixup(u)
        if (spliceLeaf) {
            if (u.parent == null) {
                root = null
            } else {
                if (u.parent!!.left == u) {
                    u.parent?.left = null
                } else {
                    u.parent?.right = null
                }
            }
        }
        return true
    }

    private fun addFixup(u: Node<T>) {
        var cur = u
        while (cur.color == Color.Red) {
            if (cur == root) {
                cur.color = Color.Black
                return
            }
            var w = cur.parent!! // cur が root でなければ親が必ず存在する
            if (w.left == null || w.left?.color == Color.Black) {
                // w.left が null または黒の場合、 u は w.right
                flipLeft(w)
                cur = w
                w = cur.parent!!
            }
            if (w.color == Color.Black) {
                return
            }
            val g = w.parent!! // root は黒で保たれるので w が赤なら root ではなく、親が存在する
            if (g.right == null || g.right?.color == Color.Black) {
                flipRight(g)
                return
            } else {
                pushBlack(g)
                cur = g
            }
        }
    }

    private fun removeFixup(
        u: Node<T>,
    ) {
        var cur = u
        while (cur.color == Color.DoubleBlack) {
            val parent = cur.parent ?: run {
                cur.color = Color.Black
                return
            }

            cur = if (parent.left?.color == Color.Red) {
                removeFixupCase1(cur)
            } else if (cur == parent.left) {
                removeFixupCase2(cur)
            } else {
                removeFixupCase3(cur)
            }
        }
        val w = cur.parent ?: return
        if (w.right?.color == Color.Red && w.left?.color == Color.Black) {
            flipLeft(w)
        }
    }

    // (Input)
    //  ●             ●
    //  ├── ●● u  ->  ├── ◯ w
    //  └── ◯ w       |   ├── ●● u (next u)
    //      ├── ●     |   └── ●
    //      └── ●     └── ●
    private fun removeFixupCase1(
        u: Node<T>,
    ): Node<T> {
        if (u.color != Color.DoubleBlack || u.parent?.left?.color != Color.Red) {
            throw IllegalArgumentException("Cannot execute removeFixupCase1: $u\n$this")
        }

        flipRight(u.parent!!)
        return u
    }

    // (Input)
    //  ? w
    //  ├── ● v
    //  └── ●● u
    private fun removeFixupCase2(
        u: Node<T>,
    ): Node<T> {
        if (u.color != Color.DoubleBlack || u.parent?.right?.color != Color.Black) {
            throw IllegalArgumentException("Cannot execute removeFixupCase2: $u\n$this")
        }

        val w = u.parent!!
        val v = w.right!!
        pullBlack(w)
        flipLeft(w)

        val q = w.right
        if (q?.color != Color.Red) {
            return v
        }
        rotateLeft(w)
        flipRight(v)
        pushBlack(q)
        if (v.right?.color == Color.Red) {
            flipLeft(v)
        }
        return q
    }

    // (Input)
    //  ? w
    //  ├── ●● u
    //  └── ● v
    private fun removeFixupCase3(
        u: Node<T>,
    ): Node<T> {
        if (u.color != Color.DoubleBlack || u.parent?.left?.color != Color.Black) {
            throw IllegalArgumentException("Cannot execute removeFixupCase3: $u\n$this")
        }
        val w = u.parent!!
        val v = w.left!!
        pullBlack(w)
        flipRight(w)

        val q = w.left
        return if (q?.color == Color.Red) {
            rotateRight(w)
            flipLeft(v)
            pushBlack(q)
            q
        } else {
            if (v.left?.color == Color.Red) {
                pushBlack(v)
                v
            } else {
                flipLeft(v)
                w
            }
        }
    }

    private fun flipLeft(u: Node<T>) {
        if (u.right == null) {
            throw IllegalArgumentException("Cannot execute flipLeft: $u\n$this")
        }

        swapColors(u, u.right!!)
        rotateLeft(u)
    }

    private fun flipRight(u: Node<T>) {
        if (u.left == null) {
            throw IllegalArgumentException("Cannot execute flipRight: $u\n$this")
        }

        swapColors(u, u.left!!)
        rotateRight(u)
    }

    private fun pushBlack(u: Node<T>) {
        if (u.left == null || u.right == null) {
            throw IllegalArgumentException("Cannot execute pushBlack: $u\n$this")
        }

        u.color = if (u.color == Color.DoubleBlack) Color.Black else Color.Red
        u.left!!.color = if (u.left!!.color == Color.Red) Color.Black else Color.DoubleBlack
        u.right!!.color = if (u.right!!.color == Color.Red) Color.Black else Color.DoubleBlack
    }

    private fun pullBlack(u: Node<T>) {
        if (u.left == null || u.right == null) {
            throw IllegalArgumentException("Cannot execute pullBlack: $u\n$this")
        }

        u.color = if (u.color == Color.Red) Color.Black else Color.DoubleBlack
        u.left!!.color = if (u.left!!.color == Color.DoubleBlack) Color.Black else Color.Red
        u.right!!.color = if (u.right!!.color == Color.DoubleBlack) Color.Black else Color.Red
    }

    private fun swapColors(u: Node<T>, v: Node<T>) {
        val uc = u.color
        u.color = v.color
        v.color = uc
    }

    class Node<T : Comparable<*>>(
        value: T,
        var color: Color,
        parent: Node<T>? = null,
        left: Node<T>? = null,
        right: Node<T>? = null,
    ) : BSTNode<T, Node<T>>(value, left, right, parent) {
        override fun toString(): String {
            return "$value ${
                when (color) {
                    Color.Red -> "◯"
                    Color.Black -> "●"
                    Color.DoubleBlack -> "●●"
                }
            }"
        }
    }

    enum class Color {
        Red,
        Black,
        DoubleBlack, // invalid state
    }
}
