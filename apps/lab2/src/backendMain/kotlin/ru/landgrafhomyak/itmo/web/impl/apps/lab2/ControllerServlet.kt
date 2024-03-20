package ru.landgrafhomyak.itmo.web.impl.apps.lab2

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.landgrafhomyak.itmo.web.impl.utility.forwardToName

class ControllerServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        if (req.parameterMap.isNotEmpty())
            req.forwardToName("servlet:AreaCheck", resp)
        else
            req.forwardToName("jsp:Form", resp)

    }
}