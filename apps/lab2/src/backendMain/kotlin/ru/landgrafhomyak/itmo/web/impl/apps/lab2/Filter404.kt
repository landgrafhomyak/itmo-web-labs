package ru.landgrafhomyak.itmo.web.impl.apps.lab2

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.ws.rs.container.PreMatching
import jakarta.ws.rs.ext.Provider

class Filter404 : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (request.servletPath == "/" || request.servletPath == "/common.css" || request.servletPath == "/history.css" || request.servletPath == "/form.css" || request.servletPath == "/form.js" || request.servletPath == "/form.js.map") {
            chain.doFilter(request, response)
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/404.jsp").forward(request, response)
        }
    }
}