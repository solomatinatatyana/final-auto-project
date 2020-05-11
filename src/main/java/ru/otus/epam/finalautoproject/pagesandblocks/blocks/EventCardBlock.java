package ru.otus.epam.finalautoproject.pagesandblocks.blocks;

import com.epam.healenium.SelfHealingDriver;
import com.epam.healenium.annotation.DisableHealing;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.helpers.DateHelper;
import ru.otus.epam.finalautoproject.helpers.EventsCardHelper;
import ru.otus.epam.finalautoproject.helpers.WebElementsHelper;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.AbstractPage;

import java.time.LocalDate;

@Component
public class EventCardBlock extends AbstractPage {
    @Autowired
    private DateHelper dateHelper;
    @Autowired
    private WebElementsHelper elementsHelper;
    public EventCardBlock(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }
    public By CARD_DATE_LOCATOR = By.cssSelector(".date");
    public By CARD_NAME_LOCATOR = By.cssSelector(".evnt-event-name>h1>span");
    public By CARD_STATUS_LOCATOR = By.cssSelector(".status");
    @DisableHealing
    public By CARD_LOCATION_LOCATOR = By.cssSelector(".location>span");
    @DisableHealing
    public By CARD_LOCATION_ONLINE_LOCATOR = By.cssSelector(".online>span");
    public By CARD_SPEAKER_LOCATOR = By.cssSelector(".evnt-speaker");
    public By CARD_LANGUAGE_LOCATOR = By.cssSelector(".language>span");

    public By CARD_SPEAKER_BLOCK_LOCATOR = By.cssSelector(".evnt-people-table");

    public LocalDate getLocalDate(String date) {
        return dateHelper.convertToDate(date);
    }

    public WebElement getDate(){
        return (new WebDriverWait(driver,200))
                .until(ExpectedConditions.presenceOfElementLocated(this.CARD_DATE_LOCATOR));
    }

    public WebElement getName(){
        return (new WebDriverWait(driver,200))
                .until(ExpectedConditions.presenceOfElementLocated(this.CARD_NAME_LOCATOR));
    }

    public WebElement getStatus(){
        return (new WebDriverWait(driver,200))
                .until(ExpectedConditions.presenceOfElementLocated(this.CARD_STATUS_LOCATOR));
    }

    public WebElement getLocation(){
        return (new WebDriverWait(driver,200))
                .until(ExpectedConditions.presenceOfElementLocated(this.CARD_LOCATION_LOCATOR));
    }

    public WebElement getOnlineLocation(){
        return (new WebDriverWait(driver,200))
                .until(ExpectedConditions.presenceOfElementLocated(this.CARD_LOCATION_ONLINE_LOCATOR));
    }

    public WebElement getSpeakers(){
        return (new WebDriverWait(driver,200))
                .until(ExpectedConditions.presenceOfElementLocated(this.CARD_SPEAKER_LOCATOR));
    }

    public WebElement getSpeakersBlock(){
        return (new WebDriverWait(driver,200))
                .until(ExpectedConditions.presenceOfElementLocated(this.CARD_SPEAKER_BLOCK_LOCATOR));
    }

    public WebElement getLanguage(){
        return (new WebDriverWait(driver,200))
                .until(ExpectedConditions.presenceOfElementLocated(this.CARD_LANGUAGE_LOCATOR));
    }
}
