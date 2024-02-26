package ru.landgrafhomyak.itmo.web.impl.modules.utility

import kotlin.jvm.JvmName

private val pattern = Regex("""^-?[_a-zA-Z]+[_a-zA-Z0-9-]*$""")

fun validateHtmlClass(className: String) {
    if (className.length < 2)
        throw IllegalArgumentException("Class name must be at least 2 symbols")
    if (!pattern.containsMatchIn(className))
        throw IllegalArgumentException("Class name has invalid symbols")
}

@JvmName("_validateHtmlClass_ext")
fun String.validateHtmlClass() = escapeHtml(this)

fun Array<out String>.validateHtmlClass() = this.forEach { s -> s.validateHtmlClass() }
fun Iterable<String>.validateHtmlClass() = this.forEach { s -> s.validateHtmlClass() }

@Suppress("DuplicatedCode")
fun Array<out String>.validate7joinToClassAttribute(quote: String? = "'", attrName: Boolean = true): String {
    if (quote != null && quote != "'" && quote != "\"")
        throw IllegalArgumentException("Quote must be one of null, \"'\" or \"\\\"\"")
    this.validateHtmlClass()
    return this.joinToString(
        separator = " ",
        prefix = (if (attrName) "class=" else "") + (quote ?: ""),
        postfix = quote ?: "",
    )
}

@Suppress("DuplicatedCode")
fun Iterable<String>.validate7joinToClassAttribute(quote: String? = "'", attrName: Boolean = true): String {
    if (quote != null && quote != "'" && quote != "\"")
        throw IllegalArgumentException("Quote must be one of null, \"'\" or \"\\\"\"")
    this.validateHtmlClass()
    return this.joinToString(
        separator = " ",
        prefix = (if (attrName) "class=" else "") + (quote ?: ""),
        postfix = quote ?: "",
    )
}

fun Array<out String>.validate7joinToClassAttributeOrNothing(quote: String? = "'", attrName: Boolean = true): String {
    if (this.isEmpty())
        return ""
    return this.validate7joinToClassAttribute(quote, attrName)
}

