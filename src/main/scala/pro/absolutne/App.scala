package pro.absolutne

import io.undertow.Undertow
import org.slf4j.LoggerFactory

object App {

  private val logger = LoggerFactory.getLogger(classOf[Context])

  private val port = 8080
  private val host = "localhost"

  def main(args: Array[String]): Unit = {

    val ctx = Context.init()

    val handler = ctx.getBean(classOf[RequestHandler])

    logger info "Building server"
    val server = Undertow.builder.addHttpListener(port, host)
      .setHandler(handler).build()

    logger info "Starting server"
    server.start()
    logger info s"Server started. Listening on host:${port}"
  }

}
