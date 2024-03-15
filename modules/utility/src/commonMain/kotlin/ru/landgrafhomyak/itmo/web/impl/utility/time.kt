package ru.landgrafhomyak.itmo.web.impl.utility

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic

/*@JvmInline
value*/ class StartingTimePoint private constructor(private val epochNanos: ULong) {
    companion object {
        @JvmStatic
        fun start() = StartingTimePoint(this._currentNanos())

        @Suppress("FunctionName")
        @JvmStatic
        private fun _currentNanos(): ULong {
            val now = Clock.System.now()
            return now.epochSeconds.toULong() * 1_000_000_000u + now.nanosecondsOfSecond.toUInt().toULong()
        }
    }

    @Suppress("RemoveRedundantQualifierName")
    fun snapshot() = TimeDelta(StartingTimePoint._currentNanos() - this.epochNanos)

}

/*@JvmInline
value*/ class TimeDelta internal constructor(private val nanos: ULong) {
    @Suppress("RemoveCurlyBracesFromTemplate")
    fun format() = "${nanos/*.toDouble()*/} μs"

    @JvmName("_asULong")
    @Suppress("FunctionName")
    fun _asULong() = this.nanos

    companion object {

        @JvmName("_fromULong")
        @Suppress("FunctionName")
        @JvmStatic
        public fun _fromULong(raw: ULong) = TimeDelta(raw)
    }
}

/*@JvmInline
value*/ class TimePoint private constructor(private val epochSeconds: ULong) {
    companion object {
        @JvmStatic
        fun now() = TimePoint(Clock.System.now().epochSeconds.toULong())

        @JvmName("_fromULong")
        @Suppress("FunctionName")
        @JvmStatic
        public fun _fromULong(raw: ULong) = TimePoint(raw)
    }

    fun format() = Instant.fromEpochSeconds(this.epochSeconds.toLong()).toString()

    @JvmName("_asULong")
    @Suppress("FunctionName")
    fun _asULong() = this.epochSeconds
}