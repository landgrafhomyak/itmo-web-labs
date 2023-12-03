package ru.landgrafhomyak.itmo.web_labs.common

import kotlin.jvm.JvmStatic

object IntSerializing {
    @JvmStatic
    fun int64ToBigEndian(value: Long, dst: ByteArray, offset: Int = 0) {
        dst[offset] = (value shr 56).toByte()
        dst[offset + 1] = (value shr 48).toByte()
        dst[offset + 2] = (value shr 40).toByte()
        dst[offset + 3] = (value shr 32).toByte()
        dst[offset + 4] = (value shr 24).toByte()
        dst[offset + 5] = (value shr 16).toByte()
        dst[offset + 6] = (value shr 8).toByte()
        dst[offset + 7] = (value).toByte()
    }

    @JvmStatic
    fun uint64ToBigEndian(value: ULong, dst: ByteArray, offset: Int = 0) {
        this.int64ToBigEndian(value.toLong(), dst, offset)
    }

    @JvmStatic
    fun doubleToBigEndian(value: Double, dst: ByteArray, offset: Int = 0) {
        this.int64ToBigEndian(value.toBits(), dst, offset)
    }

    @JvmStatic
    fun int64FromBigEndian(src: ByteArray, offset: Int = 0): Long = (src[offset].toLong() shl 56) or
            (src[offset + 1].toLong() shl 48) or
            (src[offset + 2].toLong() shl 40) or
            (src[offset + 3].toLong() shl 32) or
            (src[offset + 4].toLong() shl 24) or
            (src[offset + 5].toLong() shl 16) or
            (src[offset + 6].toLong() shl 8) or
            (src[offset + 7].toLong())

    @JvmStatic
    fun uint64FromBigEndian(src: ByteArray, offset: Int = 0) =
        this.int64FromBigEndian(src, offset).toULong()

    @JvmStatic
    fun doubleFromBigEndian(src: ByteArray, offset: Int = 0) =
        Double.fromBits(this.int64FromBigEndian(src, offset))
}






















