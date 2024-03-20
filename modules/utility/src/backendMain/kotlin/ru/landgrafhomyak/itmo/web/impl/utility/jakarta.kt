package ru.landgrafhomyak.itmo.web.impl.utility

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import java.io.IOException

fun ServletRequest.getNamedDispatcher(name: String): RequestDispatcher? = this.servletContext.getNamedDispatcher(name)

@Throws(IOException::class, ServletException::class)
fun ServletRequest.forwardToName(name: String, resp: ServletResponse) {
    val d = this.getNamedDispatcher(name) ?: throw IllegalArgumentException("Dispatcher not found for name: $name")
    d.forward(this, resp)
}