package pro.absolutne.scrapping

import org.openqa.selenium.By._
import org.openqa.selenium.support.ui.ExpectedConditions._
import org.openqa.selenium.support.ui.{Select, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement}
import scala.collection.JavaConverters._


class Browser(driver: WebDriver) {

  def goTo(url: String): Unit = driver.get(url)

  def find(css: String): WebElement = driver.findElement(cssSelector(css))

  def find(parent: WebElement, css: String): WebElement
  = parent.findElement(cssSelector(css))

  def tryFind(css: String): Option[WebElement] = {
    try {
      Some(find(css))
    } catch {
      case e:Exception => None
    }
  }

  def findAll(parent: WebElement, css: String): List[WebElement]
  = parent.findElements(cssSelector(css)).asScala.toList

  def findByText(el: WebElement, value: String): Unit = {
    new Select(el).selectByVisibleText(value)
  }

  def findVisible(css: String): WebElement =
    new WebDriverWait(driver, 10).until(visibilityOfElementLocated(cssSelector(css)))

  def getParrent(el: WebElement) = el.findElement(xpath(".."))

  def isPresent(css: String) = driver.findElements(cssSelector(css)).size > 0

  def hasChild(el: WebElement, by: By) = el.findElements(by).size > 0
}
