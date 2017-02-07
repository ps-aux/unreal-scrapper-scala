package pro.absolutne.scrapping

import java.io.{File, PrintWriter}

import scala.collection.JavaConversions._
import org.openqa.selenium.{WebDriver, WebElement}
import org.openqa.selenium.By._
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions._
import org.openqa.selenium.support.ui.{ExpectedConditions, Select, WebDriverWait}


class Browser(driver: WebDriver) {

  private val takeDigits = "[\\d ]+".r

  def go(): Unit = {
    try {
      driver.get("https://www.topreality.sk/")

      selectFilter()
      val page = driver.findElement(cssSelector(".listing"))
      val count = page.findElement(cssSelector(".count strong")).getText

      val pw = new PrintWriter(new File("result.txt"))
      pw.write("Count: " + count + "\n")

      while (hasNext()) {
        nextPage()
        val page = driver.findElement(cssSelector(".listing"))
        page.findElements(cssSelector(".estate")).map(extractRecord)
          .filter(r => r != null)
          .foreach(r => pw.write(r.toLine + "\n"))
        pw.flush()
      }

      pw.close()
    } catch {
      case e: Exception => e.printStackTrace()
    }

  }

  private val nextPageBy = cssSelector(".paginator .next")

  private def hasNext() = isPresent(nextPageBy)

  private def nextPage() = driver.findElement(nextPageBy).click()

  private def extractRecord(el: WebElement): Record = {

    try {
      def findByCss(css: String) = el findElement cssSelector(css)

      val header = findByCss("h2 a")
      val area = findByCss(".areas strong")
      val locality = findByCss(".locality")
      val price = findByCss(".price")
      val date = findByCss(".date")

      new Record(title = header.getText,
        url = header getAttribute "href",
        area = takeDigits.findFirstIn(area.getText).get.toDouble,
        price = takeDigits.findFirstIn(price.getText).get
          .replaceAll("\\s", "").toDouble,
        location = locality.getText,
        date = date.getText)
    } catch {
      case e: Exception => {
        println(s"Problem with $el")
        e.printStackTrace()
      }
    }
    null
  }

  private def selectFilter(): Unit = {
    val form = driver findElement id("searchform")

    selectByText(form.findElement(id("n_srch_typ")), "Predaj")
    val el = form findElement cssSelector("#n_srch_druh + button")
    el.click()
    val el2 = findVisible(cssSelector("input[name=multiselect_n_srch_druh][title='2 izbový byt']"))
    el2.click()
    form.findElement(cssSelector("label[for=n_srch_druh]")).click() // Click to close the menu
    form.findElement(id("token-input-n_srch_obec")).sendKeys("Bratislava")


    val l = findVisible(cssSelector(".token-input-dropdown-topreality ul"))

    l.findElements(cssSelector("li")).find(_.getText.contains("obec")).get.click()

    form.findElement(cssSelector("button.submit")).click()
  }

  private def selectByText(el: WebElement, value: String): Unit = {
    new Select(el).selectByVisibleText(value)
  }

  private def findVisible(by: By): WebElement =
    new WebDriverWait(driver, 10).until(visibilityOfElementLocated(by))

  private def getParrent(el: WebElement) = el.findElement(xpath(".."))

  private def isPresent(by: By) = driver.findElements(by).size > 0

  private def hasChild(el: WebElement, by: By) = el.findElements(by).size > 0
}
