package br.com.driw.sysgine.common.utils

fun <T> Iterable<T>.has(predicate: (T) -> Boolean): Boolean = this.find(predicate) != null
fun <T> Iterable<T>.hasNot(predicate: (T) -> Boolean): Boolean = this.find(predicate) == null
