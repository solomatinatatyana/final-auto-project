package ru.otus.epam.finalautoproject.pagesandblocks.blocks;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.AbstractPage;

@Component
public class TalksLibraryFilterBlock extends AbstractPage {
    public TalksLibraryFilterBlock(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = ".//input[@placeholder='Search by Talk Name']")
    public WebElement searchTextInput;

    @FindBy(id = "filter_category")
    public WebElement categoryFilterSelect;

    @FindBy(id = "filter_media")
    public WebElement mediaFilterSelect;

    @FindBy(id = "filter_location")
    public WebElement locationFilterSelect;

    @FindBy(id = "filter_speaker")
    public WebElement speakerFilterSelect;

    @FindBy(id = "filter_language")
    public WebElement languageFilterSelect;

    @FindBy(id = "filter_talk_level")
    public WebElement talksLevelFilterSelect;

    @FindBy(css = ".evnt-toggle-filters-button")
    public WebElement eventToggleFiltersButton;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_category']/div/input")
    public WebElement categorySearchTextInput;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_location']/div/input")
    public WebElement locationSearchTextInput;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_speaker']/div/input")
    public WebElement speakerSearchTextInput;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_language']/div/input")
    public WebElement languageSearchTextInput;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_category']")
    public WebElement categoryFilterMenu;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_location']")
    public WebElement locationFilterMenu;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_speaker']")
    public WebElement speakerFilterMenu;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_language']")
    public WebElement languageFilterMenu;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_media']")
    public WebElement mediaFilterMenu;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_talk_level']")
    public WebElement talkLevelFilterMenu;

    @FindBy(css = ".evnt-results-cell>p>span")
    public WebElement resultsCount;

}
