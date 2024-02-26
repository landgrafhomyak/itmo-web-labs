package ru.landgrafhomyak.itmo.web.impl.modules.graph

import ru.landgrafhomyak.itmo.web.svg_generator.GraphInfo
import ru.landgrafhomyak.itmo.web_labs.db.PointData

object GraphGenerator {
    fun generate(
        x: Double, y: Double,
        w: Double, h: Double,
        meta: GraphInfo,
        startingR: Double,
        ctx: GraphGenerationContext,
        points: Iterator<PointData>
    ) {
        ctx.generateArea((x + w) / 2, (y + h) / 2, w / 3, -h / 3) { pen ->
            meta.draw(pen)
        }
        ctx.generatePath("axis") { pen -> }

        for (p in points) {
            ctx.point()
        }
    }
}