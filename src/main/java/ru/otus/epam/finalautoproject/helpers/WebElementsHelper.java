package ru.otus.epam.finalautoproject.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.config.Config;

@Component
public class WebElementsHelper {
    @Autowired
    private Config config;
    private Logger log = LogManager.getLogger(WebElementsHelper.class);

    public boolean isElementPresent(By by) {
        try {
            config.getDriver().findElement(by).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(WebElement element, By by) {
        try {
            element.findElement(by).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            log.info("Элемент с локатором "+ by +" не найден");
            return false;
        }
    }

    /**
     * Проскроллить страницу вниз
     */
    public void scrollPageToTheBottom(){
        ((JavascriptExecutor) config.getDriver()).executeScript("" +
                "function f(){\n" +
                "var height = 0; \n" +
                "var attempt = 0; \n" +
                "var intS = 0; \n" +
                "function scrollToEndPage() { \n" +
                "console.log(\"height:\" + height + \" scrollHeight:\" + document.body.scrollHeight + \" att:\" + attempt ); \n" +
                "if (height < document.body.scrollHeight) { \n" +
                "height = document.body.scrollHeight; window.scrollTo(0, height); attempt++; \n" +
                "} else { clearInterval(intS); } } intS = setInterval(scrollToEndPage,1000);\n" +
                "}\n" +
                "f()");
    }

    /**
     * Проскроллить страницу на "y" вниз
     * @param y показатель на сколько скроллить страницу
     */
    public void scrollBy(int y){ ((JavascriptExecutor) config.getDriver()).executeScript("window.scrollBy(0,"+ y +")"); }
}
