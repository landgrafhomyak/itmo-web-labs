package ru.landgrafhomyak.itmo.web_labs.jsp

import kotlinx.browser.document
import kotlinx.dom.appendText
import kotlinx.dom.createElement
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement

fun main() {
    val xInputsContainer = document.getElementById("form-x")!!
    val xElements = (-5..3)
        .map { v -> v to document.createElement("input") as HTMLInputElement }
        .onEach { (v, elem) ->
            elem.name = "x"
            elem.type = "radio"
            elem.value = v.toString()
        }
        .onEach { (v, elem) ->
            val span = document.createElement("label")
            span.appendChild(elem)
            span.classList.add("radio")
            span.appendText(v.toString())
            xInputsContainer.appendChild(span)
        }

    val yElement = document.getElementById("form-y")!! as HTMLInputElement
    yElement.name = "y"
    yElement.type = "text"

    val rRoot = document.getElementById("form-r")!! as HTMLElement
    val rNullElement = document.createElement("option") as HTMLElement
    rNullElement.appendText("...")
    rRoot.appendChild(rNullElement)
    val rOptions = (1u..5u)
        .map { v -> v to document.createElement("option") as HTMLElement }
        .onEach { (v, e) -> e.appendText(v.toString()) }
        .onEach { (_, e) -> rRoot.appendChild(e) }
        .associate { (v, e) -> e to v }
    val submitButton = document.getElementById("submit") as HTMLButtonElement
    submitButton.disabled = true

    val historyInsertBefore = document.getElementById("first-static-history-entry")!!

    val inputMeta = InputMeta(submitButton, historyInsertBefore)

    xElements.onEach { (v, elem) -> elem.addEventListener("input", XInputEventHandler(v, inputMeta)) }
    yElement.addEventListener("input", YInputEventHandler(inputMeta))
    rRoot.addEventListener("change", RInputEventHandler(rRoot, rNullElement, rOptions, inputMeta))
    submitButton.addEventListener("click", SubmitButtonEventHandler(inputMeta))

    document.getElementById("svg-form")!!.addEventListener("click", AreaCheckClickEventHandler(inputMeta))
}