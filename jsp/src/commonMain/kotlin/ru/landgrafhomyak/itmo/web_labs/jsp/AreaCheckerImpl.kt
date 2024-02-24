package ru.landgrafhomyak.itmo.web_labs.jsp

import ru.landgrafhomyak.itmo.web_labs.common.AreaChecker
import kotlin.math.hypot

object AreaCheckerImpl : AreaChecker {
    override fun check(x: Double, y: Double, r: Double): Boolean = when {
        x >= 0 && y >= 0 -> x <= r && y <= r
        x <= 0 && y >= 0 -> hypot(x, y) < r / 2
        x <= 0 && y <= 0 -> x + y >= -r / 2
        x > 0 && y < 0 -> false
        else -> false
    }

    override fun validateX(raw: String): Double? = raw
        .toIntOrNull()
        ?.takeIf { v -> v in -5..3 }
        ?.toDouble()

    override fun validateY(raw: String): Double? = raw
        .toDoubleOrNull()
        ?.takeIf { v -> -5.0 < v && v < 3.0 }

    override fun validateR(raw: String): Double? = raw
        .toIntOrNull()
        ?.takeIf { v -> v in 1..5 }
        ?.toDouble()
}