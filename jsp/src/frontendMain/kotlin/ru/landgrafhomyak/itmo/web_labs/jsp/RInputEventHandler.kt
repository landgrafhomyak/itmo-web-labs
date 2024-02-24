package ru.landgrafhomyak.itmo.web_labs.jsp

import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

class RInputEventHandler(
    private val inputElement: HTMLElement,
    private val nullElement: HTMLElement,
    private val options: Map<HTMLElement, UInt>,
    private val target: InputMeta
) : EventListener {
    override fun handleEvent(event: Event) {
        val target = event.currentTarget as HTMLSelectElement
        val option = target.options.item(target.selectedIndex)
        if (option === this.nullElement) {
            this.target.rValue = null
            this.inputElement.classList.add("invalid")
        } else {
            val value = this.options[option]
            this.target.rValue = value
            if (value == null) {
                this.inputElement.classList.add("invalid")
            } else {
                this.inputElement.classList.remove("invalid")
            }
        }
    }
}