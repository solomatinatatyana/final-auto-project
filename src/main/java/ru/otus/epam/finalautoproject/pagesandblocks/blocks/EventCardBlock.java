package ru.otus.epam.finalautoproject.pagesandblocks.blocks;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.helpers.DateHelper;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.AbstractPage;

import java.time.LocalDate;

@Component
public class EventCardBlock extends AbstractPage {
    @Autowired
    private DateHelper dateHelper;
    public EventCardBlock(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }
    public By CARD_DATE_LOCATOR = By.cssSelector(".date");
    public By CARD_NAME_LOCATOR = By.cssSelector(".evnt-event-name>h1>span");
    public By CARD_STATUS_LOCATOR = By.cssSelector(".status");

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

}
