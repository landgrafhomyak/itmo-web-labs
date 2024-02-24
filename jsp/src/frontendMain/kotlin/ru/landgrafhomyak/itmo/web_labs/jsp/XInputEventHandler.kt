package ru.landgrafhomyak.itmo.web_labs.jsp

import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

// import org.w3c.dom.events.InputEvent

class XInputEventHandler(private val value: Int, private val target: InputMeta) : EventListener {
    override fun handleEvent(event: Event) {
        // event as InputEvent
        this.target.xValue = this.value
    }
}