package ru.landgrafhomyak.itmo.web_labs.jsp

import org.w3c.dom.Element
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.xhr.ProgressEvent
import org.w3c.xhr.XMLHttpRequest


class XhrResultReceiver(
    private val statusCell: Element,
    private val execTimeCell: Element
) {
    private var _xhr: XMLHttpRequest? = null
    private val xhr get() = this._xhr ?: throw IllegalStateException("No XHR bound to this object")

    @Suppress("MemberVisibilityCanBePrivate")
    val onSuccessEventListener: EventListener = this.XhrSuccessEventListener()

    @Suppress("MemberVisibilityCanBePrivate")
    val onAbortEventListener: EventListener = this.XhrAbortEventListener()

    @Suppress("MemberVisibilityCanBePrivate")
    val onErrorEventListener: EventListener = this.XhrErrorEventListener()

    @Suppress("MemberVisibilityCanBePrivate")
    val onTimeoutEventListener: EventListener = this.XhrTimeoutEventListener()
    fun bind(xhr: XMLHttpRequest) {
        if (this._xhr != null) throw IllegalStateException("This event handler already bound to xhr")
        this._xhr = xhr
        xhr.addEventListener("load", this.onSuccessEventListener)
        xhr.addEventListener("abort", this.onAbortEventListener)
        xhr.addEventListener("error", this.onErrorEventListener)
        xhr.addEventListener("timeout", this.onTimeoutEventListener)
    }

    private inner class XhrSuccessEventListener : EventListener {
        override fun handleEvent(event: Event) {
            event as ProgressEvent

        }
    }

    private inner class XhrAbortEventListener : EventListener {
        override fun handleEvent(event: Event) {

        }
    }

    private inner class XhrErrorEventListener : EventListener {
        override fun handleEvent(event: Event) {

        }
    }

    private inner class XhrTimeoutEventListener : EventListener {
        override fun handleEvent(event: Event) {

        }
    }
}