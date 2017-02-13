package pro.absolutne.web

import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RestController}
import pro.absolutne.data.storage.RecordDao
import pro.absolutne.scrapping.{Browser, TopRealityScrapper}

@RestController
class TestController @Autowired()(scrapper: TopRealityScrapper, dao: RecordDao) {

  @GetMapping(Array("/go"))
  def go(): String = {
    scrapper.scrap(new Browser(new ChromeDriver()))
    "browser done"
  }

  @PostMapping(Array("/save"))
  def dave(): String = {
    dao.saveRecord(null)
    "saved"
  }


}
