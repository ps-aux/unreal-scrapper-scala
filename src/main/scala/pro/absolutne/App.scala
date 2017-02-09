package pro.absolutne

import io.undertow.Undertow
import org.slf4j.LoggerFactory

object App {

  private val logger = LoggerFactory.getLogger(classOf[Context])

  private val port = 8080
  private val host = "localhost"

  def main(args: Array[String]): Unit = {

    val ctx = Context.init()

    val server = ctx.getBean(classOf[WebServer])

    server.start()
  }

}
