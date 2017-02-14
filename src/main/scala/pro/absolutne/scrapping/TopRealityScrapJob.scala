package pro.absolutne.scrapping

import java.text.SimpleDateFormat
import java.util.Date

import org.openqa.selenium.By.cssSelector
import org.openqa.selenium.WebElement
import pro.absolutne.data.RecordSink
import pro.absolutne.data.model.RealEstateOffer
import pro.absolutne.webscrap.{Browser, BrowserElement}

import scala.collection.JavaConverters._
import pro.absolutne.langutils.JavaOptionals._

class TopRealityScrapJob(b: Browser, sink: RecordSink, filter: Filter) {

  private val takeDigits = "[\\d ]+".r
  private val takeFlatType = "(\\d\\s.*byt|.*garz.*)\\s(.*)\\s(predaj)".r
  private val dateFormat = new SimpleDateFormat("MM.dd.yyyy")


  def start(): Unit = {
    b.goTo("https://www.topreality.sk/")
    selectFilter()

    val paginator = ".paginator .next"

    do {
      b.find(paginator).click()
      val page = b find ".listing"
      page.findAll(".estate").asScala
        .map(extractRecord)
        .filter(_.isDefined)
        .map(_.get)
        .foreach(sink.sink)
    } while (b isPresent paginator)
  }

  private def extractRecord(el: WebElement): Option[RealEstateOffer] = {

    try {
      def findByCss(css: String) = el findElement cssSelector(css)

      def parseNum(str: String) =
        takeDigits.findFirstIn(str.replaceAll("\\s", "")) match {
          case Some(s) => Some(s.toDouble)
          case _ => None
        }


      val header = findByCss("h2 a")
      val area = b.tryFind(".areas strong").toOption
      val locality = findByCss(".locality")
      val price = findByCss(".price")
      val date = findByCss(".date")

      val typeLocationActionInfo = findByCss(".links li")

      val reType = takeFlatType.findAllIn(typeLocationActionInfo.getText)
        .group(1)

      val priceVal = parseNum(price.getText)

      val areaVal = area match {
        case Some(areaEl) => parseNum(areaEl.getText)
        case _ => None
      }


      val r = new RealEstateOffer(title = header.getText,
        url = header getAttribute "href",
        area = areaVal,
        price = priceVal,
        location = locality.getText,
        date = dateFormat parse date.getText,
        reType = reType,
        scrapSource = "top-reality",
        scrapDate = new Date()
      )

      Some(r)
    } catch {
      case e: Exception =>
        val v = el.getText
        println(s"Problem with $v")
        e.printStackTrace()
        None
    }
  }

  private def selectFilter(): Unit = {
    val form = b find "#searchform"

    // Setup predaj as the action
    b.selectMatching(form find "#n_srch_typ", "Predaj")

    addFlatTypes(form)
    addLocation(form)
    submit(form)

  }

  private def addFlatTypes(form: BrowserElement) = {
    // Make it apear
    form find "#n_srch_druh + button" click()

    filter.flatTypes.foreach(addFlatType)

    // Close
    form find "label[for=n_srch_druh]" click() // Click to close the menu
  }

  private def addFlatType(flatType: FlatType) = {

    def getTitle(roomCount: Int) = roomCount match {
      case 0 => "garzónka"
      case n if n < 6 => s"$n izbový byt"
    }

    flatType match {
      case FlatType(_, n) if n < 6 => b findVisible
        s"input[name=multiselect_n_srch_druh][title='${getTitle(n)}']" click()

      case FlatType.ALL => b findVisible ".ui-multiselect-optgroup-label a" click()

    }
  }

  private def addLocation(form: BrowserElement) = {
    form.find("#token-input-n_srch_obec").sendKeys("Bratislava")

    val l = b.findVisible(".token-input-dropdown-topreality ul")

    l.findAll("li").asScala.find(_.getText.contains("obec")).get.click()
  }

  private def submit(form: BrowserElement) = {
    form.find("button.submit").click()
  }


}
