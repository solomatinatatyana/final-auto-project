package ru.otus.epam.finalautoproject.pagesandblocks.pages;

import com.epam.healenium.SelfHealingDriver;
import io.qameta.allure.Description;
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
import ru.otus.epam.finalautoproject.helpers.WebElementsHelper;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.TalkCardBlock;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.TalksLibraryFilterBlock;

import java.net.MalformedURLException;
import java.util.List;

@Component
public class TalksLibraryPage extends AbstractPage{
    private Logger log = LogManager.getLogger(TalksLibraryPage.class);
    @Autowired
    public TalksLibraryFilterBlock talksLibraryFilterBlock;
    @Autowired
    public TalkCardBlock talkCardBlock;
    @Autowired
    private WebElementsHelper elementsHelper;

    private static final String GLOBAL_LOADER = ".evnt-global-loader";
    public static final String EVENT_TALK_CARD = ".evnt-talk-card>a";

    public TalksLibraryPage(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = EVENT_TALK_CARD)
    public List<WebElement> eventTalkCardList;

    private List<WebElement> getAllTalkCard() throws InterruptedException, MalformedURLException {
        elementsHelper.scrollPageToTheBottom();
        Thread.sleep(10000);
        return eventTalkCardList;
    }

    public int getEventsCount() throws InterruptedException, MalformedURLException {
        return getAllTalkCard().size();
    }

    /**
     * Найти мероприятия по фильтру Location
     * @param searchText критерий поиска
     */
    @Step("Отфильтровать по месту проведения")
    public void filterByLocation(String searchText){
        talksLibraryFilterBlock.getLocationFilterSelect().click();
        (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.locationFilterMenu));
        talksLibraryFilterBlock.locationSearchTextInput.sendKeys(searchText);
        WebElement checkbox = (new WebDriverWait(driver, 100))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//label[@data-value='"+searchText+"']")));
        checkbox.click();
        WebDriverWait wait = (new WebDriverWait(driver, 100000));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        talksLibraryFilterBlock.getLocationFilterSelect().click();
        log.info("Применен фильтр Location - ["+ searchText +"]");
        Assert.assertTrue(talksIsFilteredSuccess(searchText),
                "Фильтр со значением ["+ searchText +"] не применился или применился неверно!");
    }

    /**
     * Найти мероприятия по фильтру Category
     * @param searchText критерий поиска
     */
    @Description("Отфильтровать по категориям")
    public void filterByCategory(String searchText){
        talksLibraryFilterBlock.getCategoryFilterSelect().click();
        (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.categoryFilterMenu));
        WebElement checkbox = (new WebDriverWait(driver, 100))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//label[@data-value='"+searchText+"']")));
        checkbox.click();
        WebDriverWait wait = (new WebDriverWait(driver, 100000));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        talksLibraryFilterBlock.getCategoryFilterSelect().click();
        log.info("Применен фильтр Category - ["+ searchText +"]");
        Assert.assertTrue(talksIsFilteredSuccess(searchText),
                "Фильтр со значением ["+ searchText +"] не применился или применился неверно!");
    }

    /**
     * Найти мероприятия по фильтру Language
     * @param searchText критерий поиска
     */
    @Step("Отфильтровать по языку")
    public void filterByLanguage(String searchText){
        talksLibraryFilterBlock.getLanguageFilterSelect().click();
        (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.languageFilterMenu));
        talksLibraryFilterBlock.languageSearchTextInput.sendKeys(searchText);
        WebElement checkbox = (new WebDriverWait(driver, 100))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//label[@data-value='"+searchText+"']")));
        checkbox.click();
        WebDriverWait wait = (new WebDriverWait(driver, 100000));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        talksLibraryFilterBlock.getLanguageFilterSelect().click();
        log.info("Применен фильтр Language - ["+ searchText +"]");
        Assert.assertTrue(talksIsFilteredSuccess(searchText),
                "Фильтр со значением ["+ searchText +"] не применился или применился неверно!");
    }

    @Step("Отфильтровать по ключевому слову")
    public void filterByKeyWord(String searchText){
        talksLibraryFilterBlock.searchTextInput.sendKeys(searchText);
        WebDriverWait wait = (new WebDriverWait(driver, 400));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
    }

    private boolean talksIsFilteredSuccess(String filter){
        return elementsHelper.isElementPresent(By.xpath(".//div[@class = 'evnt-tag evnt-filters-tags with-delete-elem' " +
                "and .//label[contains(text(), '"+filter+"')]]"));
    }

    public void moreFilterButtonClick(){
        WebDriverWait wait = (new WebDriverWait(driver, 1000000));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        talksLibraryFilterBlock.getEventToggleFiltersButton().click();
    }

    @Step("Перейти в карточку доклада")
    public void goToCard(int position) throws MalformedURLException {
        elementsHelper.scrollIntoView("evnt-talk-card",position);
        eventTalkCardList.get(position).click();
        WebDriverWait wait = (new WebDriverWait(driver, 400));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
    }
}
