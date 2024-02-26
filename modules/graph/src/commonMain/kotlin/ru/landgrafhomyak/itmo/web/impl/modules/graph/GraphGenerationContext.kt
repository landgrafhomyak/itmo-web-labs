package ru.landgrafhomyak.itmo.web.impl.modules.graph

import ru.landgrafhomyak.itmo.web.svg_generator.Pen
import ru.landgrafhomyak.itmo.web_labs.db.PointData
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface GraphGenerationContext {
    interface AreaGenerator : Pen {
        fun finishBuilding()
    }

    fun startGeneratingArea(cx: Double, cy: Double, rw: Double, rh: Double, vararg classes: String): AreaGenerator

    interface PathGenerator {
        fun moveTo(x: Double, y: Double)
        fun moveBy(dx: Double, dy: Double)
        fun lineTo(x: Double, y: Double)
        fun lineBy(dx: Double, dy: Double)
        fun finishBuilding()
    }

    fun startGeneratingPath(vararg classes: String): PathGenerator

    fun point(x: Double, y: Double, data: PointData, vararg classes: String)

    fun text(x: Double, y: Double, text: String, vararg classes: String)
}

@OptIn(ExperimentalContracts::class)
inline fun GraphGenerationContext.generateArea(
    cx: Double, cy: Double,
    rw: Double, rh: Double,
    vararg classes: String,
    receiver: (GraphGenerationContext.AreaGenerator) -> Unit
) {
    contract {
        callsInPlace(receiver, InvocationKind.EXACTLY_ONCE)
    }

    val generator = this.startGeneratingArea(cx, cy, rw, rh, *classes)
    try {
        receiver(generator)
    } catch (e1: Throwable) {
        try {
            generator.finishBuilding()
        } catch (e2: Throwable) {
            e1.addSuppressed(e2)
        }
    }
}

@OptIn(ExperimentalContracts::class)
inline fun GraphGenerationContext.generatePath(
    vararg classes: String,
    receiver: (GraphGenerationContext.PathGenerator) -> Unit
) {
    contract {
        callsInPlace(receiver, InvocationKind.EXACTLY_ONCE)
    }

    val generator = this.startGeneratingPath(*classes)
    try {
        receiver(generator)
    } catch (e1: Throwable) {
        try {
            generator.finishBuilding()
        } catch (e2: Throwable) {
            e1.addSuppressed(e2)
        }
    }
}