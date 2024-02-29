package ru.landgrafhomyak.itmo.web_labs.common

import ru.landgrafhomyak.itmo.web.svg_generator.GraphInfo

interface AreaChecker {

    val graph: GraphInfo
    fun validateX(raw: String): Double?
    fun validateY(raw: String): Double?
    fun validateR(raw: String): Double?
}