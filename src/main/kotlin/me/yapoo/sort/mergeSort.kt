package me.yapoo.sort

fun <T : Comparable<*>> List<T>.mergeSort(): List<T> {
    if (size <= 1) {
        return this
    }
    val a0 = this.subList(0, size / 2).mergeSort()
    val a1 = this.subList(size / 2, size).mergeSort()

    var i = 0
    var j = 0
    return List(size) {
        if (i == a0.size) {
            a1[j++]
        } else if (j == a1.size) {
            a0[i++]
        } else if (compareValues(a0[i], a1[j]) < 0) {
            a0[i++]
        } else {
            a1[j++]
        }
    }
}
