package pro.absolutne.web

import org.springframework.web.bind.annotation.{GetMapping, RestController}

@RestController
class TestController {

  @GetMapping(Array("/go"))
  def go(): String = {
    "testing"
  }

}
