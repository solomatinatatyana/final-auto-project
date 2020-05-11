package ru.otus.epam.finalautoproject.pagesandblocks.blocks;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.AbstractPage;

@Component
public class EventsTabsNavBlock extends AbstractPage {
    public EventsTabsNavBlock(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }
    private static final String UPCOMING_EVENTS_LOCATOR = ".//a[span[contains(text(),'Upcoming Events')]]";
    private static final String PAST_EVENTS_LOCATOR = ".//a[span[contains(text(),'Past Events')]]";

    @FindBy(xpath = UPCOMING_EVENTS_LOCATOR)
    public WebElement upcomingEventsButton;

    @FindBy(xpath = PAST_EVENTS_LOCATOR)
    public WebElement pastEventsButton;

    @FindBy(xpath = UPCOMING_EVENTS_LOCATOR+"/span[contains(@class,'evnt-tab-counter')]")
    public WebElement upcomingEventsCounter;

    @FindBy(xpath = PAST_EVENTS_LOCATOR+"/span[contains(@class,'evnt-tab-counter')]")
    public WebElement pastEventsCounter;

}
