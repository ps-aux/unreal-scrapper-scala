package pro.absolutne.scrapping

import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.WebElement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.absolutne.data.RecordSink
import pro.absolutne.data.model.Record

@Component
class TopRealityScrapper @Autowired()(sink: RecordSink) {

  private val takeDigits = "[\\d ]+".r

  def scrap(b: Browser): Unit = {
    b.goTo("https://www.topreality.sk/")
    selectFilter(b)
    val page = b find ".listing"
    val count = b.find(page, ".count strong").getText

    val paginator = ".paginator .next"

    while (b isPresent paginator) {
      b.find(paginator).click()
      val page = b find ".listing"
      b.findAll(page, ".estate").map(extractRecord)
        .filter(_.isDefined)
        .map(_.get)
        .foreach(sink.sink)
    }
  }

  private def extractRecord(el: WebElement): Option[Record] = {

    try {
      def findByCss(css: String) = el findElement cssSelector(css)

      val header = findByCss("h2 a")
      val area = findByCss(".areas strong")
      val locality = findByCss(".locality")
      val price = findByCss(".price")
      val date = findByCss(".date")

      val r = new Record(title = header.getText,
        url = header getAttribute "href",
        area = takeDigits.findFirstIn(area.getText).get.toDouble,
        price = takeDigits.findFirstIn(price.getText).get
          .replaceAll("\\s", "").toDouble,
        location = locality.getText,
        date = date.getText)

      Some(r)
    } catch {
      case e: Exception => {
        val v = el.getText
        println(s"Problem with $v")
        e.printStackTrace()
        None
      }
    }
  }

  private def selectFilter(b: Browser): Unit = {
    val form = b find "#searchform"

    b.findByText(b.find(form, "#n_srch_typ"), "Predaj")

    val el = b.find(form, "#n_srch_druh + button")
    el.click()
    val el2 = b findVisible "input[name=multiselect_n_srch_druh][title='2 izbový byt']"
    el2.click()

    b.find(form, "label[for=n_srch_druh]").click() // Click to close the menu
    b.find(form, "#token-input-n_srch_obec").sendKeys("Bratislava")


    val l = b.findVisible(".token-input-dropdown-topreality ul")

    b.findAll(l, "li").find(_.getText.contains("obec")).get.click()

    b.find(form, "button.submit").click()
  }
}
