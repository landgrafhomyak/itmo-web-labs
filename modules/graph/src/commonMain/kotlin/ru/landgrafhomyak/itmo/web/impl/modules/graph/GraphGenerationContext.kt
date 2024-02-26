package ru.landgrafhomyak.itmo.web.impl.modules.graph

import ru.landgrafhomyak.itmo.web.svg_generator.Pen

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

    fun point(x: Double, y: Double, dataX: Double, dataY: Double, vararg classes: String)

    fun text(x: Double, y: Double, text: String, vararg classes: String)
}