package net.org.cr.cachlookup.core;
import net.org.cr.cachlookup.core.enums.WebElementState;
import net.org.cr.cachlookup.core.utils.ICommonObjects;
import net.org.cr.cachlookup.core.utils.IGUIManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class BasePage
        implements ICommonObjects, IGUIManager {

    private WebDriver _driver;
    private JavascriptExecutor _js;
    private WebDriverWait _wait;

    /**
     * @author Yahav N. Hoffman
     * @since 10/14/2018
     *
     * [Description]
     * Construct an ABSTRACT reference of BasePage.class
     * @param driver -> WebDriver / RemoteWebDriver object
     */
    public BasePage(final WebDriver driver) {
        this._driver = driver;
        PageFactory.initElements(_driver,this);
    }

    /**
     * @author Yahav N. Hoffman
     * @since 10/14/2018
     *
     * [Description]
     * Return the WebDriver Instance
     * @return -> WebDriver Object
     */
    protected WebDriver getDriver() { return _driver; }

    /**
     * @author Yahav N. Hoffman
     * @since 10/14/2018
     *
     * [Description]
     * Clear and Send a String 'phrase' to an WebElement 'elem'
     * @param elem -> WebElement Object
     * @param phrase -> String Object
     */
    public void clearAndSendKeys(WebElement elem, String phrase) {
        this._js = (JavascriptExecutor) _driver;
        try {
            if (elem != null) {
                if (elem.isDisplayed()) {
                    _js.executeScript("arguments[0].setAttributes('style','border: 2px solid green;');");
                    elem.clear();
                    elem.sendKeys(phrase);
                }
            } else {
                _js.executeScript("arguments[0].setAttributes('style','border: 2px solid red;');");
                throw new WebDriverException("Could not click on element! Element -> " + elem);
            }
        }
        catch (StaleElementReferenceException sere) {
            _js.executeScript("arguments[0].setAttributes('style','border: 2px solid green;');");
            _js.executeScript("arguments[0].value ="+null+";");
            _js.executeScript("arguments[0].value ="+phrase+";");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @author Yahav N. Hoffman
     * @since 10/18/2018
     *
     * [Description]
     * This method wil wait until the element exists and displayed
     * then will simulate a click on it.
     * @param elem -> WebElement Object
     */
    public void waitAndClick(WebElement elem) {
        this._js = (JavascriptExecutor) _driver;
        this._wait = new WebDriverWait(_driver, 15000, 300);
        try {
            if (elem != null) {
                if (!elem.isDisplayed()) {
                    _js.executeScript("arguments[0].setAttributes('style', 'border: 2xp solid blue;');");
                    _wait.until(ExpectedConditions.visibilityOf(elem)).click();
                }
                _js.executeScript("arguments[0].setAttributes('style', 'border: 2xp solid blue;');");
                elem.click();
            } else {
                throw new StaleElementReferenceException("WebElement is not visible! -> "+ elem);
            }
        }
        catch (StaleElementReferenceException sere) {
            _js.executeScript("arguments[0].setAttributes('style', 'border: 2xp solid blue;');");
            _js.executeScript("arguments[0].click();");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @author Yahav N. Hoffman
     * @since 10/18/2018
     *
     * [Description]
     * Convert a WebElement Object to a By Object
     * @param elem -> WebElement Object
     * @return -> By Object
     */
    public By convertToBy(WebElement elem) {
        // By format = "[foundFrom] -> locator: term"
        // see RemoteWebElement toString() implementation
        String[] data = elem.toString().split(" -> ")[1].replace("]", "").split(": ");
        String locator = data[0];
        String term = data[1];
        switch (locator) {
            case "xpath":
                return By.xpath(term);
            case "css selector":
                return By.cssSelector(term);
            case "id":
                return By.id(term);
            case "tag name":
                return By.tagName(term);
            case "name":
                return By.name(term);
            case "link text":
                return By.linkText(term);
            case "class name":
                return By.className(term);
        }
        return (By) elem;
    }
}
