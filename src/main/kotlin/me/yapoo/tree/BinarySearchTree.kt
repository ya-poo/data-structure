package me.yapoo.tree

open class BinarySearchTree<T : Comparable<*>, Node : BSTNode<T, Node>>(
    private val factory: (T) -> Node
) : BinaryTree<T, Node>() {

    open fun find(x: T): Node? {
        var w = root
        while (w != null) {
            val comp = compareValues(x, w.value)
            w = if (comp < 0) {
                w.left
            } else if (comp > 0) {
                w.right
            } else {
                return w
            }
        }
        return null
    }

    open fun add(x: T): Boolean {
        return add(factory(x))
    }

    open fun add(u: Node): Boolean {
        val parent = findLast(u.value)
        if (parent == null) {
            root = u
            return true
        }

        val comp = compareValues(u.value, parent.value)
        if (comp == 0) {
            return false
        }

        u.parent = parent
        if (comp < 0) {
            parent.left = u
        } else {
            parent.right = u
        }
        return true
    }

    open fun remove(x: T): Boolean {
        val node = find(x) ?: return false

        if (node.left == null || node.right == null) {
            splice(node)
        } else {
            var w = node.right
            while (w?.left != null) {
                w = w.left
            }

            node.value = w!!.value
            splice(w)
        }

        return true
    }

    // x を持つノード または x 未満の最大の値、または x より大きい最小のノードを返却
    // 空の木の場合は null を返却
    fun findLast(x: T): Node? {
        var current = root
        var prev: Node? = null
        while (current != null) {
            prev = current

            val comp = compareValues(x, current.value)
            current = if (comp < 0) {
                current.left
            } else if (comp > 0) {
                current.right
            } else {
                return current
            }
        }

        return prev
    }

    // 葉または子を 1 つだけ持つノードを削除する
    fun splice(u: Node) {
        if (u.left != null && u.right != null) {
            throw IllegalArgumentException("u の子が 2 つあります。 u = $u\n$this")
        }

        val s = if (u.left != null) {
            u.left
        } else {
            u.right
        }

        if (u.parent == null) {
            root = s
            root?.parent = null
        } else {
            val p = u.parent!!
            if (p.left == u) {
                p.left = s
            } else {
                p.right = s
            }
            if (s != null) {
                s.parent = p
            }
        }
    }

    fun rotateLeft(u: Node) {
        val w = u.right ?: return
        w.parent = u.parent

        val p = w.parent
        if (p != null) {
            if (p.left == u) {
                p.left = w
            } else {
                p.right = w
            }
        }
        u.right = w.left
        if (u.right != null) {
            u.right?.parent = u
        }
        u.parent = w
        w.left = u
        if (u == root) {
            root = w
            root?.parent = null
        }
    }

    fun rotateRight(u: Node) {
        val w = u.left ?: return
        w.parent = u.parent

        val p = w.parent
        if (p != null) {
            if (w.parent?.left == u) {
                w.parent?.left = w
            } else {
                w.parent?.right = w
            }
        }
        u.left = w.right
        if (u.left != null) {
            u.left?.parent = u
        }
        u.parent = w
        w.right = u
        if (u == root) {
            root = w
            root?.parent = null
        }
    }
}

open class BSTNode<T : Comparable<*>, Node : BSTNode<T, Node>>(
    value: T,
    left: Node? = null,
    right: Node? = null,
    parent: Node? = null,
) : BTNode<T, Node>(value, left, right, parent)
