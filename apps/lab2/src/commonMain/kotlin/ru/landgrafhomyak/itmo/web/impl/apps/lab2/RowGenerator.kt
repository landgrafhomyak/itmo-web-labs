package ru.landgrafhomyak.itmo.web.impl.apps.lab2

import kotlinx.datetime.Instant
import ru.landgrafhomyak.itmo.web_labs.db.PointData
import kotlin.jvm.JvmStatic

interface RowGenerator {
    fun startRow(vararg classes: String)

    fun startCell(vararg classes: String)

    fun startResultCell(vararg classes: String)
    fun startExecTimeCell(vararg classes: String)

    fun endCell()

    fun endRow()

    fun writeText(raw: String)


    companion object {
        @JvmStatic
        fun generateRow(generator: RowGenerator, data: PointData, customResultText: String? = null, customResultClass: String? = null) {
            generator.startRow("history-row")

            generator.startCell("time")
            generator.writeText(Instant.fromEpochSeconds(data.time.toLong()).toString())
            generator.endCell()

            if (data.x == null)
                generator.startCell("input", "invalid")
            else
                generator.startCell("input")
            generator.writeText(data.xRaw)
            generator.endCell()

            if (data.y == null)
                generator.startCell("input", "invalid")
            else
                generator.startCell("input")
            generator.writeText(data.yRaw)
            generator.endCell()

            if (data.r == null)
                generator.startCell("input", "invalid")
            else
                generator.startCell("input")
            generator.writeText(data.rRaw)
            generator.endCell()

            val isValid = data.x != null && data.y != null && data.r != null

            generator.startResultCell("result", customResultClass ?: if (!isValid) "invalid" else if (data.result) "ok" else "missed")
            generator.writeText(customResultText ?: if (!isValid) "Неправильный ввод" else if (data.result) "Попадание" else "Промах")
            generator.endCell()

            generator.startExecTimeCell("exec-time")
            generator.writeText("${data.execTime} μs")
            generator.endCell()

            generator.endRow()
        }
    }
}



