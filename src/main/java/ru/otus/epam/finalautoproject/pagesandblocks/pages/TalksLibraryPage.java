package ru.otus.epam.finalautoproject.pagesandblocks.pages;

import com.epam.healenium.SelfHealingDriver;
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
import ru.otus.epam.finalautoproject.helpers.WebElementsHelper;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.TalkCardBlock;
import ru.otus.epam.finalautoproject.pagesandblocks.blocks.TalksLibraryFilterBlock;

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
    private static final String EVENT_TALK_CARD = ".evnt-talk-card>a";

    public TalksLibraryPage(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = EVENT_TALK_CARD)
    public List<WebElement> eventTalkCardList;

    private List<WebElement> getAllTalkCard() throws InterruptedException {
        elementsHelper.scrollPageToTheBottom();
        Thread.sleep(10000);
        return eventTalkCardList;
    }

    public int getEventsCount() throws InterruptedException {
        return getAllTalkCard().size();
    }

    /**
     * Найти мероприятия по фильтру Location
     * @param searchText критерий поиска
     */
    public void filterByLocation(String searchText){
        WebElement location = new WebDriverWait(driver,200)
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.locationFilterSelect));
        location.click();
        (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.locationFilterMenu));
        talksLibraryFilterBlock.locationSearchTextInput.sendKeys(searchText);
        WebElement checkbox = (new WebDriverWait(driver, 100))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//label[@data-value='"+searchText+"']")));
        checkbox.click();
        WebDriverWait wait = (new WebDriverWait(driver, 400));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        talksLibraryFilterBlock.locationFilterSelect.click();
        log.info("Применен фильтр Location - ["+ searchText +"]");
    }

    /**
     * Найти мероприятия по фильтру Category
     * @param searchText критерий поиска
     */
    public void filterByCategory(String searchText){
        WebElement category = new WebDriverWait(driver,200)
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.categoryFilterSelect));
        category.click();
        (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.categoryFilterMenu));
        WebElement checkbox = (new WebDriverWait(driver, 100))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//label[@data-value='"+searchText+"']")));
        checkbox.click();
        WebDriverWait wait = (new WebDriverWait(driver, 200));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        talksLibraryFilterBlock.categoryFilterSelect.click();
        log.info("Применен фильтр Category - ["+ searchText +"]");
    }

    /**
     * Найти мероприятия по фильтру Language
     * @param searchText критерий поиска
     */
    public void filterByLanguage(String searchText){
        WebElement language = new WebDriverWait(driver,200)
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.languageFilterSelect));
        language.click();
        (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.visibilityOf(talksLibraryFilterBlock.languageFilterMenu));
        talksLibraryFilterBlock.languageSearchTextInput.sendKeys(searchText);
        WebElement checkbox = (new WebDriverWait(driver, 100))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//label[@data-value='"+searchText+"']")));
        checkbox.click();
        WebDriverWait wait = (new WebDriverWait(driver, 500));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        talksLibraryFilterBlock.languageFilterSelect.click();
        log.info("Применен фильтр Language - ["+ searchText +"]");
    }

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
        WebDriverWait wait = (new WebDriverWait(driver, 200));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(GLOBAL_LOADER)));
        talksLibraryFilterBlock.eventToggleFiltersButton.click();
    }


}
