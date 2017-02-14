package pro.absolutne.webscrap;

import org.openqa.selenium.*;

import java.util.List;

import static org.openqa.selenium.By.*;

public class BrowserElement implements WebElement {

    private final WebElement el;
    private final Browser b;

    BrowserElement(WebElement el, Browser b) {
        this.el = el;
        this.b = b;
    }

    public boolean hasChild(String css) {
        return el.findElements(cssSelector(css)).size() > 0;
    }

    public BrowserElement find(String sel) {
        return b.find(this, sel);
    }

    public List<BrowserElement> findAll(String sel) {
        return b.findAll(this, sel);
    }

    @Override
    public void click() {
        el.click();

    }

    @Override
    public void submit() {
        el.submit();

    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        el.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        el.clear();
    }

    @Override
    public String getTagName() {
        return el.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return el.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return el.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return el.isEnabled();
    }

    @Override
    public String getText() {
        return el.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return el.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return el.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return el.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return el.getLocation();
    }

    @Override
    public Dimension getSize() {
        return el.getSize();
    }

    @Override
    public Rectangle getRect() {
        return el.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return el.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target)
            throws WebDriverException {
        return el.getScreenshotAs(target);
    }
}
