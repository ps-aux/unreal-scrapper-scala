package pro.absolutne.web

import java.nio.file.{FileSystems, Files}
import java.util.Base64

import org.openqa.selenium.{Dimension, OutputType, WebDriver}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.ScreenshotException
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
    System.setProperty("phantomjs.binary.path", ".heroku/vendor/phantomjs")
    var webDriver:WebDriver = new PhantomJSDriver()
    webDriver = new ChromeDriver()
    webDriver.manage().window().setSize(new Dimension(1920, 1080))
    val browser = new Browser(webDriver)

    val filter = Filter(Action.BUY, FlatType.ALL)

    try {
      new TopRealityScrapJob(browser, sink, filter)
        .start()
    } catch {
      case e: Exception => {
        e.printStackTrace()
/*        val file = webDriver.getScreenshotAs(OutputType.FILE)
        e.printStackTrace()
        println(file)
        file.createNewFile()*/
      }

    }
    println("done")
    "Scrapping  done"
  }

}
