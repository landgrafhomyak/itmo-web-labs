package ru.landgrafhomyak.itmo.web.impl.modules.graph

class ValueUpdateHandlersCollection {
    private val handlers = ArrayList<ValueUpdateHandler>()
    fun addHandler(h: ValueUpdateHandler) {
        this.handlers.add(h)
    }

    fun notify(newValue: Double?) {
        this.handlers.forEach { h -> h.updateValue(newValue) }
    }
}