package ru.landgrafhomyak.itmo.web.impl.modules.graph

import kotlinx.browser.document
import kotlinx.dom.appendText
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLLabelElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent

class RadioInputComponent(private val owningGroup: Element, values: Iterator<Variant>) {
    constructor(owningGroup: Element, vararg values: Variant) : this(owningGroup, values.iterator())

    private var currentValue: VariantElementsMeta? = null


    private inner class VariantElementsMeta(
        val owningLabel: HTMLLabelElement,
        val button: HTMLInputElement,
        val value: Double
    ) : EventListener {
        override fun handleEvent(event: Event) {
            if (event.currentTarget !== this.button)
                throw IllegalArgumentException("Event handler leak")

            if (event.type != "input")
                throw IllegalArgumentException("Handle fired on wrong vent type")

            if (!this.button.checked)
                return

            this@RadioInputComponent.metas
                .asSequence()
                .filter { m -> m !== this }
                .forEach { m -> m.button.checked = false }
            this.button.checked = true
            this@RadioInputComponent.eventHandlers.notify(this.value)
        }
    }

    private val metas: List<VariantElementsMeta>

    init {
        this.metas = ArrayList()
        values
            .asSequence()
            .map { (v, d) ->
                val label = document.createElement("label").unsafeCast<HTMLLabelElement>()
                label.classList.add("radio")
                val button = document.createElement("input").unsafeCast<HTMLInputElement>()
                val meta = VariantElementsMeta(label, button, v)
                button.type = "radio"
                button.checked = false
                button.addEventListener("input", meta)
                label.appendChild(button)
                label.appendText(d)
                return@map meta
            }
            .onEach { m -> this.owningGroup.appendChild(m.owningLabel) }
            .mapTo(this.metas) { m -> m }
    }

    private val eventHandlers = ValueUpdateHandlersCollection()

    fun addHandler(h: ValueUpdateHandler) {
        this.eventHandlers.addHandler(h)
    }

    fun clear() {
        this.metas.forEach { m -> m.button.checked = false }
        this.eventHandlers.notify(null)
    }
}