package pro.absolutne

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.{AnnotationConfigApplicationContext, ComponentScan, Configuration, PropertySource}

import scala.annotation.meta.field

@Configuration
@ComponentScan
@PropertySource(Array("application.properties"))
class Context {}

object Context {

  type InjValue = Value @field

  private val logger = LoggerFactory.getLogger(classOf[Context])

  def init(): ApplicationContext = {
    logger info "Setting up application context"
    val ctx = new AnnotationConfigApplicationContext(classOf[Context])
    logger info "Application context set"
    ctx
  }


}
