package ru.landgrafhomyak.itmo.web_labs.jsp

import encodeURIComponent
import kotlinx.datetime.Clock
import kotlinx.dom.appendText
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.xhr.XMLHttpRequest
import ru.landgrafhomyak.itmo.web_labs.db.RequestData

class InputMeta(
    private val submitButton: HTMLButtonElement,
    private var historyTopElem: Element
) {
    var xValue: Int? = null
        internal set(newValue) {
            field = newValue
            this.updateSubmitButtonStatus()
        }

    var yRawValue: String? = null
        internal set(newValue) {
            field = newValue
            if (newValue != null) {
                this.yValue = AreaCheckerImpl.validateY(newValue)
            }
            this.updateSubmitButtonStatus()
        }

    var yValue: Double? = null
        private set

    var rValue: UInt? = null
        internal set(newValue) {
            field = newValue
            this.updateSubmitButtonStatus()
        }

    private fun updateSubmitButtonStatus(): Boolean {
        val isValid = this.xValue != null && this.yValue != null && this.rValue != null
        this.submitButton.disabled = !isValid
        return isValid
    }

    internal fun submitDynamic(x: String, y: String, r: String) {
        val data = RequestData(
            xRaw = x, yRaw = y, rRaw = r,
            x = AreaCheckerImpl.validateX(x), y = AreaCheckerImpl.validateY(y), r = AreaCheckerImpl.validateR(r),
            result = false,
            time = Clock.System.now().epochSeconds.toULong(),
            execTime = 0.0
        )

        val gen = DomRowGenerator(this.historyTopElem)
        RowGenerator.generateRow(gen, data, "Запрос обрабатывается", "pending")
        val resultCell = gen.resultCell
        val execTimeCell = gen.execTimeCell

        val xhr = XMLHttpRequest()
        XhrResultReceiver(gen.resultCell, gen.execTimeCell).bind(xhr)
        xhr.open("GET", "./?x=${encodeURIComponent(x)}&y=${encodeURIComponent(y)}&r=${encodeURIComponent(r)}")
        xhr.send()

        xhr.onreadystatechange = xhr@{ _ ->
            if (xhr.readyState != 4.toShort()) return@xhr null

            resultCell.classList.remove("pending")
            resultCell.innerHTML = ""
            if (xhr.status == 400.toShort()) {
                resultCell.classList.add("invalid")
                resultCell.appendText("Некорректный ввод")
            } else {
                if (xhr.getResponseHeader("X-AreaCheckResult")!! == "true") {
                    resultCell.classList.add("ok")
                    resultCell.appendText("Попадание")
                } else {
                    resultCell.classList.add("ok")
                    resultCell.appendText("Промах")
                }
            }

            execTimeCell.innerHTML = ""
            execTimeCell.appendText(xhr.getResponseHeader("X-ExecTime")!!)

            return@xhr null
        }

    }
}