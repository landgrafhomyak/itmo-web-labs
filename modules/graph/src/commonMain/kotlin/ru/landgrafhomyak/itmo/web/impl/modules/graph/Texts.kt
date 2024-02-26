package ru.landgrafhomyak.itmo.web.impl.modules.graph

import ru.landgrafhomyak.itmo.web_labs.db.PointData
import kotlin.jvm.JvmStatic

object Texts {
    @JvmStatic
    fun generatePointTitle(data: PointData): String {
        if (!data.isValid) throw IllegalArgumentException("Can't generate title for invalid point")
        return "x=${data.x!!}, y=${data.y!!}, r=${data.r!!} | ${if (data.result) "попадание" else "промах"}"
    }

    @JvmStatic
    fun generatePointClasses(data: PointData):Array<out String> {
        if (!data.isValid) throw IllegalArgumentException("Can't generate css classes for invalid point")
        @Suppress("LiftReturnOrAssignment")
        if (data.result)
            return arrayOf("hit")
        else
            return arrayOf("miss")
    }

    const val NO_R_POINT_CLASS = "r-not-set"
}