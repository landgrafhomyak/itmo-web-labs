package ru.landgrafhomyak.itmo.web.impl.apps.lab2

import jakarta.inject.Singleton
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.landgrafhomyak.itmo.web.impl.modules.db.PointData
import ru.landgrafhomyak.itmo.web.impl.modules.db.jakarta.HttpSessionStorage
import ru.landgrafhomyak.itmo.web.impl.utility.StartingTimePoint
import ru.landgrafhomyak.itmo.web.impl.utility.TimePoint
import ru.landgrafhomyak.itmo.web.impl.utility.forwardToName
import java.time.Instant

class AreaCheckServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        fun Array<out String>.singleOrNull(): String? {
            if (this@singleOrNull.size != 1) return@singleOrNull null
            return this@singleOrNull[0]
        }

        val currentTime = TimePoint.now()
        val execStartTime = StartingTimePoint.start()

        val params = req.parameterMap
        val xRaw = params.getOrDefault("x", null)?.singleOrNull()
        val yRaw = params.getOrDefault("y", null)?.singleOrNull()
        val rRaw = params.getOrDefault("r", null)?.singleOrNull()
        val x = xRaw?.let(Model::validateX)
        val y = yRaw?.let(Model::validateY)
        val r = rRaw?.let(Model::validateR)
        val result: Boolean =
            if (x == null || y == null || r == null) false
            else Model.graph.check(x, y, r)

        val execTime = execStartTime.snapshot()
        val reqObj = PointData(
            xRaw = xRaw ?: "", yRaw = yRaw ?: "", rRaw = rRaw ?: "",
            x = x, y = y, r = r,
            result = result,
            time = currentTime, execTime = execTime
        )
        val db = HttpSessionStorage(req.session, "history")
        db.saveRequest(null, reqObj)
        req.setAttribute("parsed", reqObj)
        req.forwardToName("jsp:Result", resp)
    }
}