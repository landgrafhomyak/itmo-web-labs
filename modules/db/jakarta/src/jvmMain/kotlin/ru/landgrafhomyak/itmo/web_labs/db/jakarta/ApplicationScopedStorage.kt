package ru.landgrafhomyak.itmo.web_labs.db.jakarta

import jakarta.enterprise.context.ApplicationScoped
import ru.landgrafhomyak.itmo.web_labs.db.AreaStorage
import ru.landgrafhomyak.itmo.web_labs.db.PointData
import java.util.LinkedList

@ApplicationScoped
class ApplicationScopedStorage : AreaStorage {
    private val data = LinkedList<PointData>()
    override fun saveRequest(token: ByteArray?, data: PointData) {
        this.data.add(data)
    }

    override fun getNewerToOlderHistory(token: ByteArray?): List<PointData> = this.data

    override fun clearHistory(token: ByteArray?) {
        this.data.clear()
    }
}