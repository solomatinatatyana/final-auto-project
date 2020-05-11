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
                "function f(){" +
                "window.scrollTo(0, document.body.scrollHeight);" +
                "setTimeout(function(){" +
                " if ($(window).scrollTop() != $(document).height()-$(window).height()){" +
                "f();" +
                "}" +
                "}" +
                ",500);" +
                "}" +
                "f()");
    }

    /**
     * Проскроллить страницу на "y" вниз
     * @param y показатель на сколько скроллить страницу
     */
    public void scrollBy(int y){ ((JavascriptExecutor) config.getDriver()).executeScript("window.scrollBy(0,"+ y +")"); }
}
