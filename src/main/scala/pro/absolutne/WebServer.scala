package pro.absolutne

import io.undertow.{Handlers, Undertow}
import io.undertow.server.handlers.PathHandler
import io.undertow.servlet.Servlets
import io.undertow.servlet.api.{DeploymentInfo, InstanceFactory, InstanceHandle, ServletInfo}
import io.undertow.servlet.handlers.DefaultServlet
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

@Service
class WebServer @Autowired()(env: Environment,
                             ctx: WebApplicationContext,
                             handler: RequestHandler) {

  private val logger = LoggerFactory.getLogger(classOf[Context])

  private val host = env.getProperty("server.host")
  private val port = env.getProperty("server.port", classOf[Integer])
  private val ctxPath = env.getProperty("server.servlet.context-path")
  private val appName = env.getProperty("app.name")


  logger info "Building server"


  private val server = Undertow.builder.addHttpListener(port, host)
    .setHandler(pathHandler()).build()








  def start(): Unit = {
    logger info "Starting server"
    server.start()
    logger info s"Server started. Listening on $host:$port"
  }

  private def pathHandler(): PathHandler = {

    val deployment = deploymentInfo()

    val manager = Servlets.defaultContainer().addDeployment(deployment)
    manager.deploy()
    Handlers.path(Handlers.redirect(ctxPath))
      .addPrefixPath(ctxPath, manager.start())
  }

  private def deploymentInfo(): DeploymentInfo = {
    Servlets.deployment()
      .setClassLoader(classOf[WebServer].getClassLoader)
      .setContextPath(ctxPath)
      .setDeploymentName(appName)
      .addServlet(dispatcherServletInfo())
  }


  private def dispatcherServletInfo(): ServletInfo = {
    val dsFactory = new InstanceFactory[DispatcherServlet] {
      override def createInstance() = new DispatcherServletInstanceHandle(ctx)
    }

    new ServletInfo("dispatcherServlet", classOf[DispatcherServlet], dsFactory)
  }


}

class DispatcherServletFactory extends InstanceFactory[DispatcherServlet] {

  override def createInstance(): InstanceHandle[DispatcherServlet] = ???
}

class DispatcherServletInstanceHandle(ctx: WebApplicationContext) extends InstanceHandle[DispatcherServlet] {

  override def getInstance() = new DispatcherServlet(ctx)

  override def release(): Unit = {}
}
