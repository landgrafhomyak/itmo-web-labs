package ru.landgrafhomyak.itmo.web_labs.db.jakarta

import jakarta.servlet.http.HttpSession
import ru.landgrafhomyak.itmo.web_labs.db.AreaStorage
import ru.landgrafhomyak.itmo.web_labs.db.RequestData

class HttpSessionStorage(private val session: HttpSession, private val key: String) : AreaStorage {
    override fun saveRequest(token: ByteArray?, data: RequestData) {
        val raw = this.session.getAttribute(this.key)
        if (raw != null) {
            @Suppress("UNCHECKED_CAST")
            val list = this.session.getAttribute(this.key) as MutableList<RequestData>
            list.add(data)
        } else {
            this.session.setAttribute(this.key, arrayListOf(data))
        }
    }

    override fun getHistory(token: ByteArray?): List<RequestData> {
        val raw = this.session.getAttribute(this.key)
        @Suppress("LiftReturnOrAssignment")
        if (raw != null) {
            @Suppress("UNCHECKED_CAST")
            val list = this.session.getAttribute(this.key) as MutableList<RequestData>
            return list
        } else {
            return emptyList()
        }
    }

    override fun clearHistory(token: ByteArray?) {
        this.session.setAttribute(this.key, emptyList<RequestData>())
    }
}