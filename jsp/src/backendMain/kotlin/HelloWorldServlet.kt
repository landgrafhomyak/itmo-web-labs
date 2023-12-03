import jakarta.inject.Inject
import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException

@WebServlet("/HelloWorld")
class HelloWorldServlet : HttpServlet() {
    @Inject
    var helloService: HelloService? = null
    @Throws(ServletException::class, IOException::class)
    override fun doGet(
        req: HttpServletRequest,
        resp: HttpServletResponse
    ) {
        resp.contentType = "text/html"
        val writer = resp.writer
        writer.println(PAGE_HEADER)
        writer.println(
            "<h1>" +
                    helloService!!.createHelloMessage("World") +
                    "</h1>"
        )
        writer.println(PAGE_FOOTER)
        writer.close()
    }

    companion object {
        var PAGE_HEADER = "<html><head><title>helloworld</title></head><body>"
        var PAGE_FOOTER = "</body></html>"
    }
}