package ru.landgrafhomyak.itmo.web.impl.modules.db

import ru.landgrafhomyak.itmo.web.impl.utility.TimeDelta
import ru.landgrafhomyak.itmo.web.impl.utility.TimePoint
import kotlin.jvm.JvmName

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
class PointData(
    @get:JvmName("xRaw")
    val xRaw: String,
    @get:JvmName("yRaw")
    val yRaw: String,
    @get:JvmName("rRaw")
    val rRaw: String,
    @get:JvmName("x")
    val x: Double?,
    @get:JvmName("y")
    val y: Double?,
    @get:JvmName("r")
    val r: Double?,
    @get:JvmName("result")
    val result: Boolean,
    @get:JvmName("time")
    val time: TimePoint,
    @get:JvmName("execTime")
    val execTime: TimeDelta
) {
    @get:JvmName("isValid")
    val isValid: Boolean get() = this.x != null && this.y != null && this.r != null
}