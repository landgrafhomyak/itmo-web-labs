package ru.landgrafhomyak.itmo.web_labs.jsp

import jakarta.servlet.jsp.JspWriter
import ru.landgrafhomyak.itmo.web.impl.modules.utility.escapeHtml

class JspWriterRowGenerator(private val dst: JspWriter) : RowGenerator {
    override fun startRow(vararg classes: String) {
        this.dst.print("<tr class='${classes.joinToString(separator = " ")}'>")
    }

    override fun startCell(vararg classes: String) {
        this.dst.print("<td class='${classes.joinToString(separator = " ")}'>")
    }

    override fun startResultCell(vararg classes: String) {
        this.dst.print("<td class='${classes.joinToString(separator = " ")}'>")
    }

    override fun startExecTimeCell(vararg classes: String) {
        this.dst.print("<td class='${classes.joinToString(separator = " ")}'>")
    }

    override fun endCell() {
        this.dst.print("</td>")
    }

    override fun endRow() {
        this.dst.print("</tr>")
    }

    override fun writeText(raw: String) {
        this.dst.print(raw.escapeHtml())
    }
}