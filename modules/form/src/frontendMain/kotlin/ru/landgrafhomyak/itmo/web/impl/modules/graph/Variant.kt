package ru.landgrafhomyak.itmo.web.impl.modules.graph

class Variant(
    @Suppress("MemberVisibilityCanBePrivate") val value: Double,
    private val _display: String? = null
) {
    val display get() = this._display ?: this.value.toString()

    operator fun component1(): Double = this.value
    operator fun component2(): String = this.display
}