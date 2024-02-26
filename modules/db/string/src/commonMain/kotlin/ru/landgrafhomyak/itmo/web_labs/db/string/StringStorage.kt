package ru.landgrafhomyak.itmo.web_labs.db.string

import ru.landgrafhomyak.itmo.web_labs.db.AreaStorage
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

abstract class StringStorage : ByteArrayStorage(), AreaStorage {
    @OptIn(ExperimentalEncodingApi::class)
    final override fun loadDataBytes(): ByteArray =
        Base64.decode(this.loadDataString())

    @OptIn(ExperimentalEncodingApi::class)
    final override fun saveDataBytes(data: ByteArray) =
        this.saveDataString(Base64.encode(data))

    abstract fun loadDataString(): String
    abstract fun saveDataString(data: String)
}