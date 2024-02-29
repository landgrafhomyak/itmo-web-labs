package ru.landgrafhomyak.itmo.web_labs.jsp

import encodeURIComponent
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.svg.SVGGElement
import org.w3c.dom.svg.SVGSVGElement
import ru.landgrafhomyak.itmo.web.impl.modules.graph.GraphComponent
import ru.landgrafhomyak.itmo.web.impl.modules.graph.RadioInputComponent
import ru.landgrafhomyak.itmo.web.impl.modules.graph.Variant
import ru.landgrafhomyak.itmo.web_labs.db.PointData

fun main() {
    val graphComponent = GraphComponent(
        svgRoot = document.getElementById("graph")!! as SVGSVGElement,
        cx = 0.0,
        cy = 0.0,
        rw = 100.0,
        rh = 100.0,
        pointsGroup = document.getElementById("graph-points")!! as SVGGElement,
        emptyArray<PointData>().iterator()
    )

    val xInputComponent = RadioInputComponent(
        document.getElementById("form-x")!!,
        Variant(-5.0, "-5"),
        Variant(-4.0, "-4"),
        Variant(-3.0, "-3"),
        Variant(-2.0, "-2"),
        Variant(-1.0, "-1"),
        Variant(0.0, "0"),
        Variant(1.0, "1"),
        Variant(2.0, "2"),
        Variant(3.0, "3"),
        Variant(4.0, "4"),
        Variant(5.0, "5"),
    )

    val yInputComponent = RadioInputComponent(
        document.getElementById("form-y")!!,
        Variant(-5.0, "-5"),
        Variant(-4.0, "-4"),
        Variant(-3.0, "-3"),
        Variant(-2.0, "-2"),
        Variant(-1.0, "-1"),
        Variant(0.0, "0"),
        Variant(1.0, "1"),
        Variant(2.0, "2"),
        Variant(3.0, "3"),
    )

    val rInputComponent = RadioInputComponent(
        document.getElementById("form-r")!!,
        Variant(1.0, "1"),
        Variant(2.0, "2"),
        Variant(3.0, "3"),
        Variant(4.0, "4"),
        Variant(5.0, "5"),
    )

    val submitButton = document.getElementById("submit") as HTMLButtonElement
    submitButton.disabled = true


    val controllerComponent = ControllerComponent()

    xInputComponent.addHandler(controllerComponent.SetXHandler)
    yInputComponent.addHandler(controllerComponent.SetYHandler)
    rInputComponent.addHandler(controllerComponent.SetRHandler)
    submitButton.addEventListener("click", controllerComponent.SubmitButtonOnclickEventHandler)

    class FormUpdaterImpl : ControllerComponent.UpdateFormHandler {
        override fun updateForm(x: Double?, y: Double?, r: Double?) {
            submitButton.disabled = x == null || y == null || r == null
        }
    }

    controllerComponent.addUpdateFormHandler(FormUpdaterImpl())

    class SubmitHandlerImpl : ControllerComponent.SubmitHandler {
        override fun submitFormData(x: Double?, y: Double?, r: Double?) {
            window.location.href = "./?x=${
                encodeURIComponent(x?.toString() ?: "")
            }&y=${
                encodeURIComponent(y?.toString() ?: "")
            }&r=${
                encodeURIComponent(r?.toString() ?: "")
            }"
        }
    }

    controllerComponent.addSubmitHandler(SubmitHandlerImpl())
}