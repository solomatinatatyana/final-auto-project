package ru.otus.epam.finalautoproject.pagesandblocks.pages;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.helpers.WebElementsHelper;

import java.util.List;

@Component
public class TalksCardPage extends AbstractPage{
    public TalksCardPage(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @Autowired
    private WebElementsHelper elementsHelper;

    private static final String TITLE = ".evnt-talk-title";
    private static final String DATE = ".date";
    private static final String LOCATION = ".location>span";
    private static final String LANGUAGE = ".language>span";
    private static final String CATEGORIES = ".//div[contains(@class,'evnt-talk-topic')]/label";
    //   .//div[contains(@class,'evnt-talk-topic') and .//label[contains(text(),'Design')]]

    @FindBy(css = TITLE)
    public WebElement title;

    @FindBy(css = DATE)
    public WebElement date;

    @FindBy(css = LOCATION)
    public WebElement location;

    @FindBy(css = LANGUAGE)
    public WebElement language;

    @FindBy(css = CATEGORIES)
    public List<WebElement> categoriesList;

    public boolean categoryIsPresent(By by){
        if(elementsHelper.isElementPresent(by)) return true;
        return false;
    }
}
