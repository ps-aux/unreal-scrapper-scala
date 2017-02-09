package pro.absolutne

import io.undertow.server.{HttpHandler, HttpServerExchange}
import io.undertow.util.Headers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RequestHandler @Autowired()(scrapService: ScrapperService) extends HttpHandler {


  override def handleRequest(exchange: HttpServerExchange): Unit = {

    exchange.getResponseHeaders.put(Headers.CONTENT_TYPE, "text/plain")
    exchange.getResponseSender.send(scrapService.go())
  }
}
