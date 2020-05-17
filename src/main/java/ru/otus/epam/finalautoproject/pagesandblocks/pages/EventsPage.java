package ru.otus.epam.finalautoproject.pagesandblocks.pages;

import com.epam.healenium.SelfHealingDriver;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventCardBlock;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventsFilterBlock;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventsTabsNavBlock;

import java.net.MalformedURLException;
import java.util.List;

@Component
public class EventsPage extends AbstractPage {
    private Logger log = LogManager.getLogger(EventsPage.class);
    @Autowired
    public EventsTabsNavBlock eventsTabsNavBlock;
    @Autowired
    public EventCardBlock eventCardBlock;
    @Autowired
    public EventsFilterBlock eventsFilterBlock;
    @Autowired
    private WebElementsHelper elementsHelper;

    private static final String EVENT_CARD_LOADER_LOCATOR = ".evnt-cards-loading";
    private static final String GLOBAL_LOADER = ".evnt-global-loader";
    public static final String EVENT_CARD = ".evnt-event-card>a";
    private static final String THIS_WEEK_BLOCK = ".//div[@class='evnt-cards-container' and .//h3[contains(text(),'This week')]]";

    public EventsPage(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = EVENT_CARD)
    public List<WebElement> eventCardList;

    @FindBy(css = THIS_WEEK_BLOCK)
    public WebElement thisWeekBlock;

    @FindBy(xpath = THIS_WEEK_BLOCK+"//div[contains(@class,'evnt-event-card')]")
    private List<WebElement> eventCardThisWeekList;

    public List<WebElement> getEventCardThisWeekList(){
        return eventCardThisWeekList;
    }

    @Step("Переход на {eventType}")
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

    private List<WebElement> getAllEventsCard() throws MalformedURLException {
        elementsHelper.scrollPageToTheBottom();
        return eventCardList;
    }

    public int getEventsCount() throws MalformedURLException {
        return getAllEventsCard().size();
    }

    /**
     * Найти мероприятия по фильтру Location
     * @param searchText критерий поиска
     */
    @Step("Отфильтровать по месту проведения")
    public void filterByLocation(String searchText){
        WebDriverWait wait = (new WebDriverWait(driver, 100000));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        eventsFilterBlock.getLocationFilterSelect().click();
        (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.visibilityOf(eventsFilterBlock.locationFilterMenu));
        eventsFilterBlock.locationSearchTextInput.sendKeys(searchText);
        WebElement checkbox = (new WebDriverWait(driver, 100))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//label[@data-value='"+searchText+"']")));
        checkbox.click();
        eventsFilterBlock.getLocationFilterSelect().click();
        WebDriverWait wait2 = (new WebDriverWait(driver, 100));
        wait2.until(ExpectedConditions.visibilityOf(eventsFilterBlock.filterContentTable));
        Assert.assertTrue(eventsIsFilteredSuccess(searchText),
                "Фильтр со значением ["+ searchText +"] не применился или применился неверно!");
    }

    private boolean eventsIsFilteredSuccess(String filter){
        return elementsHelper.isElementPresent(By.xpath(".//div[@class = 'evnt-tag evnt-filters-tags with-delete-elem' " +
                "and .//label[contains(text(), '"+filter+"')]]"));
    }

    @Step("Перейти в карточку мероприятия")
    public void goToCard(int position) throws MalformedURLException {
        elementsHelper.scrollIntoView("evnt-event-card",position);
        eventCardList.get(position).click();
        WebDriverWait wait = (new WebDriverWait(driver, 100000));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
    }
}
