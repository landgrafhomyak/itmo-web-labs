package ru.landgrafhomyak.itmo.web_labs.common

interface AreaChecker {
    fun check(x: Double, y: Double, r: Double): Boolean

    fun validateX(raw: String): Double?
    fun validateY(raw: String): Double?
    fun validateR(raw: String): Double?
}