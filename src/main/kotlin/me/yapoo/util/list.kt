package me.yapoo.util

fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val memo = this[i]
    this[i] = this[j]
    this[j] = memo
}
