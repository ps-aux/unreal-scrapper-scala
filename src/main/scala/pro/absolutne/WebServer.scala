package pro.absolutne

import java.util
import javax.servlet.{ServletContainerInitializer, ServletContext}

import io.undertow.server.handlers.PathHandler
import io.undertow.servlet.Servlets
import io.undertow.servlet.api._
import io.undertow.{Handlers, Undertow}
import org.slf4j.LoggerFactory
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

class WebServer {

  private val logger = LoggerFactory.getLogger(classOf[WebServer])

  private val host = "0.0.0.0"
  private val port = 8000
  private val ctxPath = "/"
  private val appName = "unreal-scrapper"

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

  private def deploymentInfo(): DeploymentInfo = Servlets.deployment()
    .setClassLoader(classOf[WebServer].getClassLoader)
    .setContextPath(ctxPath)
    .setDeploymentName(appName)
    .addServletContainerInitalizer(// Ouch horrible TODO refactor to readable anything
      new ServletContainerInitializerInfo(classOf[SpringContextIntializer],
        new InstanceFactory[SpringContextIntializer] {
          override def createInstance(): InstanceHandle[SpringContextIntializer] = {
            new InstanceHandle[SpringContextIntializer] {
              override def getInstance() = new SpringContextIntializer()

              override def release(): Unit = {}
            }
          }
        }, null))
}

class SpringContextIntializer() extends ServletContainerInitializer {

  override def onStartup(c: util.Set[Class[_]], servletCtx: ServletContext): Unit = {
    val appContext = new AnnotationConfigWebApplicationContext()
    appContext.setConfigLocation(classOf[ContextConfig].getName)

    val dispatcher = servletCtx.addServlet("dispatcher", new DispatcherServlet(appContext))
    dispatcher.setLoadOnStartup(1)
    dispatcher.addMapping("/")
  }
}


