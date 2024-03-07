package ru.landgrafhomyak.itmo.web.impl.modules.graph

import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.svg.SVGCircleElement
import org.w3c.dom.svg.SVGGElement
import org.w3c.dom.svg.SVGSVGElement
import ru.landgrafhomyak.itmo.web.impl.utility.escapeHtml
import ru.landgrafhomyak.itmo.web.impl.modules.db.PointData

class GraphComponent(
    private val svgRoot: SVGSVGElement,
    private val cx: Double, private val cy: Double,
    private val rw: Double, private val rh: Double,
    private val pointsGroup: SVGGElement,
    pointsToGenerate: Iterator<PointData>
) {
    private var lastR: Double? = null

    private class Point(
        val elem: SVGCircleElement,
        val data: PointData
    ) {
        operator fun component1(): SVGCircleElement = this.elem
        operator fun component2(): PointData = this.data
    }

    private val points = ArrayList<Point>()

    init {
        pointsToGenerate
            .asSequence()
            .filter { d -> d.isValid }
            .map { d ->
                Point(
                    this.pointsGroup.ownerDocument!!.createElement("circle").unsafeCast<SVGCircleElement>(),
                    d
                )
            }
            .onEach { (e, d) -> e.classList.add(*HtmlTexts.generatePointClasses(d)) }
            .onEach { (e, _) -> ; e.setAttribute("r", (this.rw / 20).toString()) }
            .onEach { (e, d) ->
                val title = this.pointsGroup.ownerDocument!!.createElement("title")
                title.innerHTML = HtmlTexts.generatePointTitle(d).escapeHtml()
                e.appendChild(title)
            }
            .onEach { p -> this.pointsGroup.appendChild(p.elem) }
            .forEach { p -> this.points.add(p) }
        this.reposition()
    }


    fun reposition(newR: Double?) {
        this.lastR = newR
        this.reposition()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun reposition() {
        val r = this.lastR
        if (r == null) {
            this.pointsGroup.classList.add(HtmlTexts.NO_R_POINT_CLASS)
            return
        } else {
            this.pointsGroup.classList.remove(HtmlTexts.NO_R_POINT_CLASS)
        }

        this.points.forEach { (e, d) ->
            e.setAttribute("cx", (this.cx + d.x!! * this.rw / r).toString())
            e.setAttribute("cy", (this.cy + d.y!! * this.rw / r).toString())
        }
    }

    fun clear() {
        this.points.forEach { p -> p.elem.remove() }
        this.points.clear()
    }

    interface ClickHandler {
        fun handleGraphClick(graph: GraphComponent, x1: Double, y1: Double, r: Double, xr: Double, yr: Double)
        fun handleGraphClickNoR(graph: GraphComponent, x1: Double, y1: Double)
    }

    private val clickListeners = ArrayList<ClickHandler>()

    fun onClick(clickHandler: ClickHandler) {
        this.clickListeners.add(clickHandler)
    }

    private inner class GraphMouseClickEventListenerImpl : EventListener {
        override fun handleEvent(event: Event) {
            event as MouseEvent
            if (event.currentTarget !== this@GraphComponent.svgRoot)
                throw IllegalArgumentException("Wrong target element")
            val svg = this@GraphComponent.svgRoot
            val svgRect = svg.getBoundingClientRect()
            val xPx = event.clientX - svgRect.left
            val yPx = event.clientY - svgRect.top

            val vb = svg.viewBox.baseVal
            val xVb = vb.x + (xPx * vb.width / svgRect.width)
            val yVb = vb.y + (yPx * vb.height / svgRect.height)

            val x1 = (xVb - this@GraphComponent.cx) / this@GraphComponent.rw
            val y1 = (yVb - this@GraphComponent.cy) / this@GraphComponent.rh

            val r = this@GraphComponent.lastR
            if (r == null) {
                this@GraphComponent.clickListeners
                    .forEach { h -> h.handleGraphClickNoR(this@GraphComponent, x1, y1) }
                return
            }
            this@GraphComponent.clickListeners
                .forEach { h -> h.handleGraphClick(this@GraphComponent, x1, y1, r, x1 * r, y1 * r) }
        }
    }

    init {
        this.svgRoot.addEventListener("click", this.GraphMouseClickEventListenerImpl())
    }
}