package pro.absolutne.web

import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{PostMapping, RestController}
import pro.absolutne.data.RecordSink
import pro.absolutne.data.storage.RecordDao
import pro.absolutne.scrapping.{Action, Filter, FlatType, TopRealityScrapJob}
import pro.absolutne.webscrap.Browser

@RestController
class TestController @Autowired()(sink: RecordSink, dao: RecordDao) {

  @PostMapping(Array("/go"))
  def go(): String = {
    val browser = new Browser(new ChromeDriver())

    val filter = Filter(Action.BUY, FlatType.ALL)

    new TopRealityScrapJob(browser, sink, filter)
      .start()
    println("done")
    "Scrapping  done"
  }

}
