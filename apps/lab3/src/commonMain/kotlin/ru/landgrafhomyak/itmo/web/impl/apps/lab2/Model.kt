package ru.landgrafhomyak.itmo.web.impl.apps.lab2

import ru.landgrafhomyak.itmo.web.graph_meta.GraphInfo
import ru.landgrafhomyak.itmo.web.graph_meta.QuartInfo
import ru.landgrafhomyak.itmo.web.impl.modules.graph.AreaChecker

object Model : AreaChecker {
    override val graph = GraphInfo(
        topRight = QuartInfo.Rectangle(true, false),
        bottomRight = QuartInfo.OuterArc(false, false),
        bottomLeft = null,
        topLeft = QuartInfo.Triangle(true, true),
    )

    override fun validateX(raw: String): Double? = raw
        .toIntOrNull()
        ?.takeIf { v -> v in -5..5 }
        ?.toDouble()

    override fun validateY(raw: String): Double? = raw
        .toDoubleOrNull()
        ?.takeIf { v -> -5.0 <= v && v <= 5.0 }

    override fun validateR(raw: String): Double? = raw
        .toDoubleOrNull()
        ?.takeIf { v -> v.times(2.0).toInt() in 2..6 }
        ?.toDouble()
}