package ru.otus.epam.finalautoproject.pagesandblocks.pages;

import com.epam.healenium.SelfHealingDriver;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;
import ru.otus.epam.finalautoproject.enums.NavigationBar;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventNavigationNavBarBlock;

@Component
public class MainPage extends AbstractPage{
    private Logger log = LogManager.getLogger(MainPage.class);

    @Autowired
    public EventNavigationNavBarBlock eventNavigationNavBarBlock;

    public MainPage(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    private static final String GLOBAL_LOADER = ".evnt-global-loader";

    @Step("Переход на сайт {url}")
    public void open(String url){
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(),url,"Сайт не открылся");
    }

    @Step("Переход на вкладку {button}")
    public void goToNavView(NavigationBar button){
        log.info("Переходим на вкладку " + button);
        switch (button){
            case CALENDAR:
                WebElement calendarButton = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.visibilityOf(eventNavigationNavBarBlock.calendarButton));
                calendarButton.click();
                WebDriverWait wait = (new WebDriverWait(driver, 5000));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
                Assert.assertEquals(driver.getCurrentUrl(),"https://events.epam.com/calendar");
                break;
            case EVENTS:
                WebElement eventButton = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.visibilityOf(eventNavigationNavBarBlock.eventsButton));
                eventButton.click();
                WebDriverWait wait1 = (new WebDriverWait(driver, 5000));
                wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
                Assert.assertEquals(driver.getCurrentUrl(),"https://events.epam.com/events");
                break;
            case TALKS_LIBRARY:
                WebElement talksButton = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.visibilityOf(eventNavigationNavBarBlock.talksLibraryButton));
                talksButton.click();
                WebDriverWait wait2 = (new WebDriverWait(driver, 5000));
                wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
                Assert.assertEquals(driver.getCurrentUrl(),"https://events.epam.com/talks");
                break;
            case SPEAKERS:
                WebElement speakersButton = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.visibilityOf(eventNavigationNavBarBlock.speakersButton));
                speakersButton.click();
                WebDriverWait wait3 = (new WebDriverWait(driver, 5000));
                wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
                Assert.assertEquals(driver.getCurrentUrl(),"https://events.epam.com/speakers");
                break;
        }
    }

}
