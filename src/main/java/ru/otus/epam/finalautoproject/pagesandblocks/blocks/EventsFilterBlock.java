package ru.otus.epam.finalautoproject.pagesandblocks.blocks;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.AbstractPage;

@Component
public class EventsFilterBlock extends AbstractPage {
    public EventsFilterBlock(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = ".evnt-filters-content-table")
    public WebElement filterContentTable;

    @FindBy(xpath = ".//div[@id='filter_location']")
    private WebElement locationFilterSelect;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_location']/div/input")
    public WebElement locationSearchTextInput;

    @FindBy(xpath = ".//div[@aria-labelledby='filter_location']")
    public WebElement locationFilterMenu;

    public WebElement getLocationFilterSelect(){
        return new WebDriverWait(driver,500)
                .until(ExpectedConditions.visibilityOf(locationFilterSelect));
    }
}
