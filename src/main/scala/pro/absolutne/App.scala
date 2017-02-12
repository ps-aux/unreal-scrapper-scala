package pro.absolutne

object App {

  def main(args: Array[String]): Unit = {

    val server = new WebServer()
    server.start()
  }

}
