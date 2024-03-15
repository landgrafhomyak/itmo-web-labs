import ru.landgrafhomyak.itmo.web.impl.apps.lab2.rValueHandler
import ru.landgrafhomyak.itmo.web.impl.apps.lab2.svgGraphPathD
import ru.landgrafhomyak.itmo.web.impl.apps.lab2.xValueHandler

@OptIn(ExperimentalJsExport::class)
@JsExport
@JsName("svgGraphPathD")
fun getSvgGraphPathD(): String = svgGraphPathD

@OptIn(ExperimentalJsExport::class)
@JsExport
@JsName("updateX")
fun updateX(value: Double) = xValueHandler.updateValue(value)
@OptIn(ExperimentalJsExport::class)
@JsExport
@JsName("updateR")
fun updateR(value: Double) = rValueHandler.updateValue(value)