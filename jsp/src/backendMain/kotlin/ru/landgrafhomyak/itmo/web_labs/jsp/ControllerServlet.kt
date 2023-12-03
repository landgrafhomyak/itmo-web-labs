package ru.landgrafhomyak.itmo.web_labs.jsp

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class ControllerServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.getRequestDispatcher("/WEB-INF/form.jsp").forward(req, resp)
    }
}