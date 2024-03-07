package ru.landgrafhomyak.itmo.web.impl.apps.lab2

import jakarta.inject.Singleton
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.landgrafhomyak.itmo.web_labs.db.PointData
import ru.landgrafhomyak.itmo.web_labs.db.jakarta.HttpSessionStorage
import java.time.Instant

@Singleton
class AreaCheckServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        fun Array<out String>.singleOrNull(): String? {
            if (this@singleOrNull.size != 1) return@singleOrNull null
            return this@singleOrNull[0]
        }

        val currentTime = Instant.now().epochSecond
        val execStartTime = System.nanoTime()

        val params = req.parameterMap
        val xRaw = params.getOrDefault("x", null)?.singleOrNull()
        val yRaw = params.getOrDefault("y", null)?.singleOrNull()
        val rRaw = params.getOrDefault("r", null)?.singleOrNull()
        val x = xRaw?.let(Model::validateX)
        val y = yRaw?.let(Model::validateY)
        val r = rRaw?.let(Model::validateR)
        val isDataValid: Boolean
        val result: Boolean
        if (x != null && y != null && r != null) {
            isDataValid = true
            result = Model.graph.check(x, y, r)
        } else {
            isDataValid = false
            result = false
        }
        val execEndTime = System.nanoTime()
        val execTime = (execEndTime - execStartTime).toDouble() * 1e-9
        val reqObj = PointData(
            xRaw = xRaw ?: "", yRaw = yRaw ?: "", rRaw = rRaw ?: "",
            x = x, y = y, r = r,
            result = result,
            time = currentTime.toULong(), execTime = execTime
        )
        val db = HttpSessionStorage(req.session, "history")
        db.saveRequest(null, reqObj)
        req.setAttribute("parsed", reqObj)
        req.getRequestDispatcher("/WEB-INF/pages/result.jsp").forward(req, resp)
    }

    fun doGetNotProtected(req: HttpServletRequest, resp: HttpServletResponse) = this.doGet(req, resp)
}