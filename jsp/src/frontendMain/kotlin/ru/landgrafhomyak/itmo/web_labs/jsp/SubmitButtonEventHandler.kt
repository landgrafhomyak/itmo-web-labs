package ru.landgrafhomyak.itmo.web_labs.jsp

import encodeURIComponent
import kotlinx.browser.window
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

value class SubmitButtonEventHandler(private val target: InputMeta) : EventListener {
    override fun handleEvent(event: Event) {
        window.location.href = "./?x=${encodeURIComponent(this.target.xValue.toString())}&y=${encodeURIComponent(this.target.yValue.toString())}&r=${encodeURIComponent(this.target.rValue.toString())}"
    }
}