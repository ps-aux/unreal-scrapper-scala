package pro.absolutne

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.web.servlet.config.annotation.EnableWebMvc

import scala.annotation.meta.field

@Configuration
@EnableWebMvc
@ComponentScan
class ContextConfig {

}

object ContextConfig {
  type InjValue = Value@field
}