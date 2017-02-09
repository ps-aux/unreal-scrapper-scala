package pro.absolutne

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class ScrapperService @Autowired()(env: Environment) {


  def sayHello() = "Hello, this is scrapper service"

  def go() = {
    val prop = env.getProperty("")

    prop
  }

}
