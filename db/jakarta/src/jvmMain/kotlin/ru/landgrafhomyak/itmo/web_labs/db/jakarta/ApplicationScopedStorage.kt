package ru.landgrafhomyak.itmo.web_labs.db.jakarta

import jakarta.enterprise.context.ApplicationScoped
import ru.landgrafhomyak.itmo.web_labs.db.AreaStorage
import ru.landgrafhomyak.itmo.web_labs.db.RequestData
import java.util.LinkedList

@ApplicationScoped
class ApplicationScopedStorage : AreaStorage {
    private val data = LinkedList<RequestData>()
    override fun saveRequest(token: ByteArray?, data: RequestData) {
        this.data.add(data)
    }

    override fun getNewerToOlderHistory(token: ByteArray?): List<RequestData> = this.data

    override fun clearHistory(token: ByteArray?) {
        this.data.clear()
    }
}