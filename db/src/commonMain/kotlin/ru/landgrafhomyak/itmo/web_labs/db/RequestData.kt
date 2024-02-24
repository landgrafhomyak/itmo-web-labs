package ru.landgrafhomyak.itmo.web_labs.db

import kotlin.jvm.JvmField

/**
 * Represents info about request and response to it.
 *
 * @param xRaw Value of **X** as is.
 * @param yRaw Value of **Y** as is.
 * @param rRaw Value of **R** as is.
 * @param x Parsed value of **X** if correct, otherwise `null`.
 * @param y Parsed value of **Y** if correct, otherwise `null`.
 * @param r Parsed value of **R** if correct, otherwise `null`.
 * @param result Result of area check. If any of [x], [y], [r] are `null`, must be `false`.
 * @param time Unix timestamp of request.
 * @param execTime Time of request processing.
 */
@Suppress("MemberVisibilityCanBePrivate")
class RequestData(
    @JvmField
    val xRaw: String,
    @JvmField
    val yRaw: String,
    @JvmField
    val rRaw: String,
    @JvmField
    val x: Double?,
    @JvmField
    val y: Double?,
    @JvmField
    val r: Double?,
    @JvmField
    val result: Boolean,
    val time: ULong,
    @JvmField
    val execTime: Double
)