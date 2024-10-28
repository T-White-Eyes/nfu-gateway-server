package com.nfu.gateway.util

fun String.startsWithNot(prefix: String, ignoreCase: Boolean = false): Boolean {
    return !this.startsWith(prefix, ignoreCase)
}