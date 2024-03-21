package ru.landgrafhomyak.itmo.web.impl.apps.lab2

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.landgrafhomyak.itmo.web.impl.utility.forwardToName

class Filter404 : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (request.servletPath == "/" || request.servletPath == "/common.css" || request.servletPath == "/history.css" || request.servletPath == "/form.css" || request.servletPath == "/form.js" || request.servletPath == "/form.js.map") {
            chain.doFilter(request, response)
        } else {
            request.forwardToName("jsp:404", response)
        }
    }
}