package ru.landgrafhomyak.itmo.web_labs.jsp

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

class YInputEventHandler(private val target: InputMeta) : EventListener {
    override fun handleEvent(event: Event) {
        val elem = event.currentTarget as HTMLInputElement
        this.target.yRawValue = elem.value
        if (this.target.yValue == null)
            elem.classList.add("invalid")
        else
            elem.classList.remove("invalid")
    }
}