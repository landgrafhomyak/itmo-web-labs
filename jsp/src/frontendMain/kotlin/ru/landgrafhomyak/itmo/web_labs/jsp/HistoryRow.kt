package ru.landgrafhomyak.itmo.web_labs.jsp

interface HistoryRow {
    fun setResult(text: String, cssClass: String? = null)

    fun setExecTime(text: String)
}