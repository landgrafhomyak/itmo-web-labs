package ru.landgrafhomyak.itmo.web_labs.jsp

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.svg.SVGSVGElement

class AreaCheckClickEventHandler(private val inputMeta: InputMeta) : EventListener {
    override fun handleEvent(event: Event) {
        if (inputMeta.rValue == null) {
            window.alert("Для начала выберите R")
            return
        }

        event as MouseEvent
        val target = event.currentTarget!! as SVGSVGElement
        val targetRect = target.getBoundingClientRect()
        val xPx = event.clientX - targetRect.left
        val yPx = event.clientY - targetRect.top
        val xVb = target.viewBox.baseVal.x + (xPx * target.viewBox.baseVal.width / targetRect.width)
        val yVb = -(target.viewBox.baseVal.y + (yPx * target.viewBox.baseVal.height / targetRect.height))

    }
}