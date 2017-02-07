package pro.absolutne

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.{AnnotationConfigApplicationContext, ComponentScan, Configuration}

@Configuration
@ComponentScan
class Context {}

object Context {

  private val logger = LoggerFactory.getLogger(classOf[Context])

  def init(): ApplicationContext = {
    logger info "Setting up application context"
    val ctx = new AnnotationConfigApplicationContext(classOf[Context])
    logger info "Application context set"
    ctx
  }
}
