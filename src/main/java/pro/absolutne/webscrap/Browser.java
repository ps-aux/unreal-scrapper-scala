package pro.absolutne.webscrap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Browser {

    private final WebDriver driver;

    /**
     * Wait time in seconds
     */
    private final int waitTime = 10;

    public Browser(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo(String url) {
        driver.get(url);
    }


    private List<BrowserElement> wrapAll(Collection<WebElement> els) {
        return els.stream().map(this::wrap).collect(toList());
    }

    public void selectMatching(WebElement parent, String value) {
        new Select(parent).selectByVisibleText(value);
    }

    public BrowserElement find(String sel) {
        return wrap(driver.findElement(cssSelector(sel)));
    }

    public BrowserElement findWithText(String sel, String text) {
        return findAll(sel).stream()
                .filter(el -> el.getText().equals(text))
                .findFirst().get();
    }

    public Optional<BrowserElement> tryFind(String sel) {
        try {
            return Optional.of(find(sel));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public List<BrowserElement> findAll(String sel) {
        return wrapAll(driver.findElements(cssSelector(sel)));
    }

    public BrowserElement findVisible(String sel) {
        By cssBy = cssSelector(sel);
        WebElement el = new WebDriverWait(driver, waitTime)
                .until(visibilityOfElementLocated(cssBy));

        return wrap(el);
    }


    public BrowserElement getParrent(WebElement el) {
        return wrap(el.findElement(xpath("..")));
    }

    public boolean isPresent(String sel) {
        return driver.findElements(cssSelector(sel)).size() > 0;
    }

    BrowserElement wrap(WebElement el) {
        return new BrowserElement(el, this);
    }

    BrowserElement find(WebElement parent, String sel) {
        return wrap(parent.findElement(cssSelector(sel)));
    }

    List<BrowserElement> findAll(WebElement parent, String sel) {
        return wrapAll(parent.findElements(cssSelector(sel)));
    }
}
