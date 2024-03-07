package ru.landgrafhomyak.itmo.web_labs.jsp

import ru.landgrafhomyak.itmo.web.graph_meta.GraphInfo
import ru.landgrafhomyak.itmo.web.graph_meta.QuartInfo
import ru.landgrafhomyak.itmo.web_labs.common.AreaChecker
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

object Model: AreaChecker {
    override val graph = GraphInfo(
        topRight = QuartInfo.Rectangle(true, true),
        bottomRight = null,
        bottomLeft = QuartInfo.Triangle(false, false),
        topLeft = QuartInfo.OuterArc(false, false)
    )

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