package pro.absolutne

import io.undertow.Undertow
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class WebServer @Autowired()(env:Environment, handler: RequestHandler) {

  private val logger = LoggerFactory.getLogger(classOf[Context])

  private val host = env.getProperty("server.host")
  private val port = env.getProperty("server.port", classOf[Integer])

  logger info "Building server"


  private val server = Undertow.builder.addHttpListener(port, host)
    .setHandler(handler).build()


  def start():Unit = {
    logger info "Starting server"
    server.start()
    logger info s"Server started. Listening on ${host}:${port}"
  }

}
