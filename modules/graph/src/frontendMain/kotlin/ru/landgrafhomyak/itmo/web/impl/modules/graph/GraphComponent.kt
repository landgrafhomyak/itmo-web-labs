package ru.landgrafhomyak.itmo.web.impl.modules.graph

import ru.landgrafhomyak.itmo.web.impl.modules.utility.asSequence
import org.w3c.dom.get
import org.w3c.dom.svg.SVGCircleElement
import org.w3c.dom.svg.SVGSVGElement

class GraphComponent(private val root: SVGSVGElement, initialR: Double) {
    private var lastR = initialR
//    private val viewBoxX: Double = this.root.viewBox.baseVal.x
//    private val viewBoxY: Double = this.root.viewBox.baseVal.y
//    private val viewBoxW: Double = this.root.viewBox.baseVal.width
//    private val viewBoxH: Double = this.root.viewBox.baseVal.height

    private class Point(val elem: SVGCircleElement, val modelX: Double, val modelY: Double)

    private val points = ArrayList<Point>()

    init {
        this.root.children
            .asSequence()
            .filter { e -> e.tagName == "circle" }
            .map { e -> e.unsafeCast<SVGCircleElement>() }
            .mapNotNull { e ->
                val x = e.attributes["data-x"]?.value
                val y = e.attributes["data-y"]?.value
                if ((x == null) != (y == null))
                    throw IllegalArgumentException("Only one of 'data-x' or 'data-y' attributes set, second is missing: $e")
                if (x == null || y == null)
                    return@mapNotNull null

                return@mapNotNull GraphComponent.Point(e, x.toDouble(), y.toDouble())
            }
            .forEach { e -> this.points.add(e) }
    }


    fun reposition(newR: Double) {
        this.lastR = newR
        this.reposition()
    }

    fun reposition() {
        val viewBoxX: Double = this.root.viewBox.baseVal.x
        val viewBoxY: Double = this.root.viewBox.baseVal.y
        val viewBoxW: Double = this.root.viewBox.baseVal.width
        val viewBoxH: Double = this.root.viewBox.baseVal.height

        TODO()
        this.points
    }

    fun clear() {
        this.points.forEach { p -> p.elem.remove() }
        this.points.clear()
    }
}