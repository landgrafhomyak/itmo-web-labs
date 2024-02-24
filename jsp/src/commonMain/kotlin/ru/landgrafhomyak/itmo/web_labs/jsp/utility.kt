package ru.landgrafhomyak.itmo.web_labs.jsp

import kotlin.jvm.JvmName

fun escapeHtml(raw: String) = raw.asIterable().joinToString(separator = "") { c ->
    when (c) {
        '<' -> "&lt;"
        '>' -> "&gt;"
        '&' -> "&amp;"
        else -> "$c"
    }
}

@JvmName("_escapeHtml_ext")
fun String.escapeHtml() = escapeHtml(this)