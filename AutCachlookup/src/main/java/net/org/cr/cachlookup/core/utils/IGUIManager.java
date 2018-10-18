package net.org.cr.cachlookup.core.utils;

import org.openqa.selenium.WebElement;

public interface IGUIManager {

    void waitAndClick(WebElement element);
    void clearAndSendKeys(WebElement elem, String phrase);
}
