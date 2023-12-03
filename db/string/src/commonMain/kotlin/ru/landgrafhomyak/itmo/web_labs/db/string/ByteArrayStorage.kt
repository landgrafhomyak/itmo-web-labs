package ru.landgrafhomyak.itmo.web_labs.db.string

import ru.landgrafhomyak.itmo.web_labs.common.IntSerializing
import ru.landgrafhomyak.itmo.web_labs.db.AreaStorage
import ru.landgrafhomyak.itmo.web_labs.db.RequestData

abstract class ByteArrayStorage : AreaStorage {
    private var data: ByteArray = this.loadDataBytes()

    init {
        if (this.data.size < 8)
            ByteArray(8) { 0 }
                .also { ba ->
                    this.data = ba
                    this.saveDataBytes(ba)
                }
    }

    override fun saveRequest(token: ByteArray?, data: RequestData) {
        require(token == null)
        val xRawEncoded = data.xRaw.encodeToByteArray().slice(0 until 255).toByteArray()
        val yRawEncoded = data.yRaw.encodeToByteArray().slice(0 until 255).toByteArray()
        val rRawEncoded = data.rRaw.encodeToByteArray().slice(0 until 255).toByteArray()
        val packet = ByteArray(48 + xRawEncoded.size + yRawEncoded.size + rRawEncoded.size)
        val flags = (if (data.result) 0b1L else 0b0L) or
                (if (data.r == null) 0b10L else 0b00L) or
                (if (data.y == null) 0b100L else 0b000L) or
                (if (data.x == null) 0b1000L else 0b0000L) or
                (rRawEncoded.size.toLong() shl 8) or
                (yRawEncoded.size.toLong() shl 16) or
                (xRawEncoded.size.toLong() shl 24) or
                (packet.size.toLong() shl 32)
        IntSerializing.int64ToBigEndian(flags, packet, 0)
        IntSerializing.int64ToBigEndian(data.x?.toBits() ?: 0L, packet, 8)
        IntSerializing.int64ToBigEndian(data.y?.toBits() ?: 0L, packet, 16)
        IntSerializing.int64ToBigEndian(data.r?.toBits() ?: 0L, packet, 24)
        IntSerializing.uint64ToBigEndian(data.time, packet, 32)
        IntSerializing.doubleToBigEndian(data.execTime, packet, 40)
        xRawEncoded.copyInto(packet, 48)
        yRawEncoded.copyInto(packet, 48 + xRawEncoded.size)
        rRawEncoded.copyInto(packet, 48 + xRawEncoded.size + yRawEncoded.size)

        val newData = ByteArray(this.data.size + packet.size)
        IntSerializing.int64ToBigEndian(IntSerializing.int64FromBigEndian(this.data) + 1, this.data)
        packet.copyInto(newData, 8)
        this.data.copyInto(newData, 8 + packet.size)
        this.data = newData
        this.saveDataBytes(newData)
    }

    override fun getHistory(token: ByteArray?): List<RequestData> {
        require(token == null)
        var offset = 8
        val data = Array(IntSerializing.int64FromBigEndian(this.data).toInt()) { i ->
            val flags = IntSerializing.int64FromBigEndian(this.data, offset)
            val packetSize = (flags shr 32).toInt()
            val xRawSize = ((flags shr 24) and 255).toInt()
            val yRawSize = ((flags shr 16) and 255).toInt()
            val rRawSize = ((flags shr 8) and 255).toInt()
            val x = if (flags and 0b1000L == 0L) null else IntSerializing.doubleFromBigEndian(this.data, offset + 8)
            val y = if (flags and 0b100L == 0L) null else IntSerializing.doubleFromBigEndian(this.data, offset + 16)
            val r = if (flags and 0b10L == 0L) null else IntSerializing.doubleFromBigEndian(this.data, offset + 24)
            val time = IntSerializing.uint64FromBigEndian(this.data, offset + 32)
            val execTime = IntSerializing.doubleFromBigEndian(this.data, offset + 40)
            val xRaw = this.data.decodeToString(offset + 48)
            val yRaw = this.data.decodeToString(offset + 48 + xRawSize)
            val rRaw = this.data.decodeToString(offset + 48 + xRawSize + yRawSize)
            offset += packetSize

            return@Array RequestData(
                xRaw = xRaw,
                yRaw = yRaw,
                rRaw = rRaw,
                x = x,
                y = y,
                r = r,
                time = time,
                execTime = execTime,
                result = flags and 1L != 0L
            )
        }

        return data.asList()
    }

    override fun clearHistory(token: ByteArray?) {
        require(token == null)
        ByteArray(8) { 0 }
            .also { ba ->
                this.data = ba
                this.saveDataBytes(ba)
            }
    }


    protected abstract fun loadDataBytes(): ByteArray
    protected abstract fun saveDataBytes(data: ByteArray)
}