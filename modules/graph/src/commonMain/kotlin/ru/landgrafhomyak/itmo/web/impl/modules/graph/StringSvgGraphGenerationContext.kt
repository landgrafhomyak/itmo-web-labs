package ru.landgrafhomyak.itmo.web.impl.modules.graph

import ru.landgrafhomyak.itmo.web.impl.modules.utility.escapeHtml
import ru.landgrafhomyak.itmo.web.impl.modules.utility.validate7joinToClassAttributeOrNothing
import ru.landgrafhomyak.itmo.web.svg_generator.Pen
import ru.landgrafhomyak.itmo.web.svg_generator.SvgPathDStringPen

class StringSvgGraphGenerationContext : GraphGenerationContext {
    private val builder = StringBuilder()
    private var activeChild: Any? = null
    private var isFinished = false

    @Suppress("FunctionName")
    private fun _checkState() {
        if (this.isFinished)
            throw IllegalStateException("This generator already built")
        if (this.activeChild != null)
            throw IllegalStateException("Last path or area generation not finished")
    }

    override fun startGeneratingArea(cx: Double, cy: Double, rw: Double, rh: Double, vararg classes: String): GraphGenerationContext.AreaGenerator {
        this._checkState()
        this.builder.append("<path ${classes.validate7joinToClassAttributeOrNothing()} d='")
        val areaGenerator = this.AreaGeneratorImpl(cx, cy, rw, rh)
        this.activeChild = areaGenerator
        return areaGenerator
    }

    private inner class AreaGeneratorImpl(cx: Double, cy: Double, rw: Double, rh: Double) : GraphGenerationContext.AreaGenerator {
        private val builder = SvgPathDStringPen(cx, cy, rw, rh)
        private var isFinished = false

        @Suppress("FunctionName")
        private fun _checkState() {
            if (this.isFinished) throw IllegalStateException("Building of this area already finished")
        }


        override fun arcTo(rx: Pen.Coordinate, ry: Pen.Coordinate, angle: Int, outerArc: Boolean, toX: Pen.Coordinate, toY: Pen.Coordinate) {
            this._checkState()
            this.builder.arcTo(rx, ry, angle, outerArc, toX, toY)
        }

        override fun closeLine() {
            this._checkState()
            this.builder.closeLine()
        }

        override fun lineTo(x: Pen.Coordinate, y: Pen.Coordinate) {
            this._checkState()
            this.builder.lineTo(x, y)
        }

        override fun moveTo(x: Pen.Coordinate, y: Pen.Coordinate) {
            this._checkState()
            this.builder.moveTo(x, y)
        }

        override fun finishBuilding() {
            this._checkState()
            this.isFinished = true
            this@StringSvgGraphGenerationContext.builder.append(this.builder.build() + "'></path>")
            this@StringSvgGraphGenerationContext.activeChild = null
        }
    }

    override fun startGeneratingPath(vararg classes: String): GraphGenerationContext.PathGenerator {
        this.builder.append("<path ${classes.validate7joinToClassAttributeOrNothing()} d='")
        val pathGenerator = this.PathGeneratorImpl()
        this.activeChild = pathGenerator
        return pathGenerator
    }

    private inner class PathGeneratorImpl() : GraphGenerationContext.PathGenerator {
        private var isFinished = false
        private var isFirst = true

        @Suppress("FunctionName")
        private fun _addLeadingSeparator() {
            if (this.isFirst) {
                this@StringSvgGraphGenerationContext.builder.append(' ')
                this.isFirst = false
            }
        }

        @Suppress("FunctionName")
        private fun _checkState() {
            if (this.isFinished) throw IllegalStateException("Building of this path already finished")
        }

        override fun moveTo(x: Double, y: Double) {
            this._checkState()
            this._addLeadingSeparator()
            this@StringSvgGraphGenerationContext.builder.append("M $x $y")
        }

        override fun moveBy(dx: Double, dy: Double) {
            this._checkState()
            this._addLeadingSeparator()
            this@StringSvgGraphGenerationContext.builder.append("m $dx $dy")
        }

        override fun lineTo(x: Double, y: Double) {
            this._checkState()
            this._addLeadingSeparator()
            this@StringSvgGraphGenerationContext.builder.append("L $x $y")
        }

        override fun lineBy(dx: Double, dy: Double) {
            this._checkState()
            this._addLeadingSeparator()
            this@StringSvgGraphGenerationContext.builder.append("l $dx $dy")
        }

        override fun finishBuilding() {
            this._checkState()
            this.isFinished = true
            this@StringSvgGraphGenerationContext.builder.append("'></path>")
            this@StringSvgGraphGenerationContext.activeChild = null
        }
    }

    override fun point(x: Double, y: Double, dataX: Double, dataY: Double, vararg classes: String) {
        this._checkState()
        this.builder.append("<circle ${classes.validate7joinToClassAttributeOrNothing()} x='$x' y='$y' data-x='${dataX}' data-y='${dataY}'></circle>")

    }

    override fun text(x: Double, y: Double, text: String, vararg classes: String) {
        this._checkState()
        this.builder.append("<text ${classes.validate7joinToClassAttributeOrNothing()} x='$x' y='$y'>${text.escapeHtml()}</text>")
    }
}