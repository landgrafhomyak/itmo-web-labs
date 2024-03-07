package ru.landgrafhomyak.itmo.web_labs.jsp

import jakarta.inject.Inject
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class ControllerServlet : HttpServlet() {
    @Inject
    private lateinit var _areaCheckServlet: AreaCheckServlet

    private val areaCheckServlet get() = this._areaCheckServlet

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.parameterMap.isNotEmpty())
            this.areaCheckServlet.doGetNotProtected(req, resp)
        else
            req.getRequestDispatcher("/WEB-INF/pages/form.jsp").forward(req, resp)

    }
}