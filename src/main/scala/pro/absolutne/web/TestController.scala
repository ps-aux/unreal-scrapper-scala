package pro.absolutne.web

import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RestController}
import pro.absolutne.data.RecordSink
import pro.absolutne.data.model.Record
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
    "browser done"
  }

  @PostMapping(Array("/save"))
  def dave(): String = {
    val r = new Record("a", "b", Some(5), Some(6), "x", "y")
    dao.saveRecord(r)
    "saved"
  }


}
