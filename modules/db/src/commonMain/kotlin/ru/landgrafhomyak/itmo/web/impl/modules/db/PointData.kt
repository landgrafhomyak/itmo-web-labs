package ru.landgrafhomyak.itmo.web.impl.modules.db

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
    val xRaw: String,
    val yRaw: String,
    val rRaw: String,
    val x: Double?,
    val y: Double?,
    val r: Double?,
    val result: Boolean,
    val time: ULong,
    val execTime: Double
) {
    val isValid: Boolean get() = this.x != null && this.y != null && this.r != null
}