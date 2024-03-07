
package ru.landgrafhomyak.itmo.web.impl.apps.lab2

import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import ru.landgrafhomyak.itmo.web.impl.modules.graph.ValueUpdateHandler

@Suppress("ClassName")
class ControllerComponent {
    var x: Double? = null
    var y: Double? = null
    var r: Double? = null

    @Suppress("ClassName")
    private inner class _SetXHandler : ValueUpdateHandler {
        override fun updateValue(newValue: Double?) {
            this@ControllerComponent.x = newValue
            this@ControllerComponent.updateForm()
        }
    }

    @Suppress("PropertyName")
    val SetXHandler: ValueUpdateHandler = this._SetXHandler()

    @Suppress("ClassName")
    private inner class _SetYHandler : ValueUpdateHandler {
        override fun updateValue(newValue: Double?) {
            this@ControllerComponent.y = newValue
            this@ControllerComponent.updateForm()
        }
    }
    @Suppress("PropertyName")
    val SetYHandler: ValueUpdateHandler = this._SetYHandler()


    @Suppress("ClassName")
    private inner class _SetRHandler : ValueUpdateHandler {
        override fun updateValue(newValue: Double?) {
            this@ControllerComponent.r = newValue
            this@ControllerComponent.updateForm()
        }
    }

    @Suppress("PropertyName")
    val SetRHandler: ValueUpdateHandler = this._SetRHandler()


    interface SubmitHandler {
        fun submitFormData(x: Double?, y: Double?, r: Double?)
    }

    private val formSubmitHandlers = ArrayList<SubmitHandler>()

    fun submit() {
        this.formSubmitHandlers.forEach { h -> h.submitFormData(this.x, this.y, this.r) }
    }

    fun addSubmitHandler(h: SubmitHandler) {
        this.formSubmitHandlers.add(h)
    }

    private inner class _SubmitButtonOnclickEventHandler:EventListener {
        override fun handleEvent(event: Event) {
            this@ControllerComponent.submit()
        }
    }

    @Suppress("PropertyName")
    val SubmitButtonOnclickEventHandler:EventListener = this._SubmitButtonOnclickEventHandler()


    interface UpdateFormHandler {
        fun updateForm(x: Double?, y: Double?, r: Double?)
    }

    private val updateFormHandlers = ArrayList<UpdateFormHandler>()

    fun updateForm() {
        this.updateFormHandlers.forEach { h -> h.updateForm(this.x, this.y, this.r) }
    }


    fun addUpdateFormHandler(h: UpdateFormHandler) {
        this.updateFormHandlers.add(h)
    }
}