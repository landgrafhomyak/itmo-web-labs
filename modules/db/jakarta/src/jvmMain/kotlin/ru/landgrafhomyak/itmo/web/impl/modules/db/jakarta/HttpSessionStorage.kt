package ru.landgrafhomyak.itmo.web.impl.modules.db.jakarta

import jakarta.servlet.http.HttpSession
import ru.landgrafhomyak.itmo.web.impl.modules.db.AreaStorage
import ru.landgrafhomyak.itmo.web.impl.modules.db.PointData

class HttpSessionStorage(private val session: HttpSession, private val key: String) : AreaStorage {
    override fun saveRequest(token: ByteArray?, data: PointData) {
        val raw = this.session.getAttribute(this.key)
        if (raw != null) {
            @Suppress("UNCHECKED_CAST")
            val list = this.session.getAttribute(this.key) as MutableList<PointData>
            list.add(data)
        } else {
            this.session.setAttribute(this.key, arrayListOf(data))
        }
    }

    override fun getNewerToOlderHistory(token: ByteArray?): List<PointData> {
        val raw = this.session.getAttribute(this.key)
        @Suppress("LiftReturnOrAssignment")
        if (raw != null) {
            @Suppress("UNCHECKED_CAST")
            val list = this.session.getAttribute(this.key) as MutableList<PointData>
            return list.asReversed()
        } else {
            return emptyList()
        }
    }

    override fun clearHistory(token: ByteArray?) {
        this.session.setAttribute(this.key, emptyList<PointData>())
    }
}