package me.yapoo.sort

import kotlin.random.Random

fun <T : Comparable<*>> List<T>.quickSort(): List<T> {
    return this.toMutableList().apply {
        quickSort(this, 0, size)
    }
}

// list[i..i+n-1] を整列
private fun <T : Comparable<*>> quickSort(
    list: MutableList<T>,
    i: Int,
    n: Int,
) {
    if (n <= 1) {
        return
    }

    val pivot = list[i + Random.nextInt(0, Int.MAX_VALUE) % n]
    var p = i - 1
    var j = i
    var q = i + n
    while (j < q) {
        val comp = compareValues(list[j], pivot)
        if (comp < 0) {
            list.swap(j, p + 1)
            j++
            p++
        } else if (comp > 0) {
            list.swap(j, q - 1)
            q--
        } else {
            j++
        }
    }
    quickSort(list, i, p - i + 1)
    quickSort(list, q, n - (q - i))
}

private fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val memo = this[i]
    this[i] = this[j]
    this[j] = memo
}
