package ru.otus.epam.finalautoproject.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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
}
