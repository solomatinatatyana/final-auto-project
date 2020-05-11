package ru.otus.epam.finalautoproject.pagesandblocks.pages;

import com.epam.healenium.SelfHealingDriver;
import com.epam.healenium.annotation.DisableHealing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;
import ru.otus.epam.finalautoproject.enums.Events;
import ru.otus.epam.finalautoproject.helpers.WebElementsHelper;
import ru.otus.epam.finalautoproject.models.EventCard;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventCardBlock;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventsTabsNavBlock;

import java.util.List;

@Component
public class EventsPage extends AbstractPage {
    private Logger log = LogManager.getLogger(EventsPage.class);
    @Autowired
    public EventsTabsNavBlock eventsTabsNavBlock;
    @Autowired
    public EventCardBlock eventCardBlock;
    @Autowired
    private WebElementsHelper elementsHelper;

    private static final String EVENT_CARD_LOADER_LOCATOR = ".evnt-cards-loading";
    private static final String GLOBAL_LOADER = ".evnt-global-loader";

    public EventsPage(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @DisableHealing
    @FindBy(css = EVENT_CARD_LOADER_LOCATOR)
    public WebElement eventCardLoader;

    @FindBy(css = ".evnt-event-card>a")
    public List<WebElement> eventCardList;

    @FindBy(css = ".evnt-filters-content-table")
    public WebElement filterContentTable;

    @FindBy(xpath = ".//div[@id='filter_location']")
    public WebElement locationFilterSelect;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_location']/div/input")
    public WebElement locationSearchTextInput;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_location']")
    public WebElement filterMenu;

    public void goToEventsView(Events eventType){
        log.info("Переключаемся на " + eventType);
        switch (eventType){
            case PAST_EVENTS:
                WebDriverWait wait = (new WebDriverWait(driver, 10));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
                WebElement pastEventButton = (new WebDriverWait(driver, 50))
                        .until(ExpectedConditions.visibilityOf(eventsTabsNavBlock.pastEventsButton));
                pastEventButton.click();
                break;
            case UPCOMING_EVENTS:
                WebDriverWait wait2 = (new WebDriverWait(driver, 10));
                wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
                WebElement upcomingEventButton = (new WebDriverWait(driver, 50))
                        .until(ExpectedConditions.visibilityOf(eventsTabsNavBlock.upcomingEventsButton));
                upcomingEventButton.click();
                break;
        }
    }

    private List<WebElement> getAllEventsCard(){
        scrollPageToTheBottom();
       /* do{
            scrollPageToTheBottom();
        }while (eventCardLoader.isDisplayed());*/
        return eventCardList;
    }

    public int getEventsCount(){
        return getAllEventsCard().size();
    }



    public void scrollPageToTheBottom(){
        ((JavascriptExecutor) driver).executeScript("" +
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
     * Найти мероприятия по фильтру Location
     * @param searchText критерий поиска
     */
    public void filterByLocation(String searchText){
        WebDriverWait wait = (new WebDriverWait(driver, 400));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        WebElement location = new WebDriverWait(driver,200)
                .until(ExpectedConditions.visibilityOf(locationFilterSelect));
        location.click();
        (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.visibilityOf(filterMenu));
        locationSearchTextInput.sendKeys(searchText);
        WebElement checkbox = (new WebDriverWait(driver, 100))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//label[@data-value='"+searchText+"']")));
        checkbox.click();
        locationFilterSelect.click();
        WebDriverWait wait2 = (new WebDriverWait(driver, 100));
        wait2.until(ExpectedConditions.visibilityOf(filterContentTable));
        Assert.assertTrue(eventsIsFilteredSuccess(searchText),
                "Фильтр со значением ["+ searchText +"] не применился или применился неверно!");
    }

    private boolean eventsIsFilteredSuccess(String filter){
        return elementsHelper.isElementPresent(By.xpath(".//div[@class = 'evnt-tag evnt-filters-tags with-delete-elem' " +
                "and .//label[contains(text(), '"+filter+"')]]"));
    }

    public WebElement getGlobalLocation(WebElement element){
        WebElement elLoc = null;
        if(elementsHelper.isElementPresent(element, eventCardBlock.CARD_LOCATION_LOCATOR)){
             elLoc = eventCardBlock.getLocation();
        }else if(elementsHelper.isElementPresent(element,eventCardBlock.CARD_LOCATION_ONLINE_LOCATOR)){
            elLoc = eventCardBlock.getOnlineLocation();
            log.info("Место проведения мероприятия: online");
        }else {
            log.info("Блок с местом проведения мероприятия остуствует");
        }
        return elLoc;
    }

    public void scrollBy(int y){ ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"+ y +")"); }
}