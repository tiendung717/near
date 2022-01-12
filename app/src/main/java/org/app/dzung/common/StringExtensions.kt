package org.app.dzung.common

fun String.shortString(maxLength: Int = 15) : String {
    return if (this.length >= maxLength) buildString {
        append(this@shortString.take(5))
        append("...")
        append(this@shortString.takeLast(5))
    } else {
        this
    }
}