package ru.landgrafhomyak.itmo.web_labs.jsp

import kotlinx.browser.document
import kotlinx.dom.appendText
import org.w3c.dom.Element

class DomRowGenerator(private var insertBefore: Element) : RowGenerator {
    lateinit var resultCell: Element
    lateinit var execTimeCell: Element

    private val stack = ArrayList<Element>()

    override fun startRow(vararg classes: String) {
        val e = document.createElement("tr")
        e.classList.add(*classes)
        if (this.stack.isEmpty()) {
            this.insertBefore.parentElement!!.insertBefore(e, this.insertBefore)
            this.insertBefore = e
        } else {
            this.stack.last().appendChild(e)
        }
        this.stack.add(e)
    }

    override fun startCell(vararg classes: String) {
        val e = document.createElement("td")
        e.classList.add(*classes)
        if (this.stack.isEmpty()) {
            this.insertBefore.parentElement!!.insertBefore(e, this.insertBefore)
            this.insertBefore = e
        } else {
            this.stack.last().appendChild(e)
        }
        this.stack.add(e)
    }

    override fun startResultCell(vararg classes: String) {
        val e = document.createElement("tr")
        e.classList.add(*classes)
        if (this.stack.isEmpty()) {
            this.insertBefore.parentElement!!.insertBefore(e, this.insertBefore)
            this.insertBefore = e
        } else {
            this.stack.last().appendChild(e)
        }
        this.stack.add(e)
        this.resultCell = e
    }

    override fun startExecTimeCell(vararg classes: String) {
        val e = document.createElement("tr")
        e.classList.add(*classes)
        if (this.stack.isEmpty()) {
            this.insertBefore.parentElement!!.insertBefore(e, this.insertBefore)
            this.insertBefore = e
        } else {
            this.stack.last().appendChild(e)
        }
        this.stack.add(e)
        this.execTimeCell = e
    }

    override fun endCell() {
        this.stack.removeLast()
    }

    override fun endRow() {
        this.stack.removeLast()
    }

    override fun writeText(raw: String) {
        this.stack.last().appendText(raw.escapeHtml())
    }
}