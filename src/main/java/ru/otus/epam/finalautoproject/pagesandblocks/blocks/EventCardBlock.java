package ru.otus.epam.finalautoproject.pagesandblocks.blocks;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.AbstractPage;

@Component
public class EventCardBlock extends AbstractPage {
    public EventCardBlock(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }
    public static final String CARD_DATE = ".date";
    public static final String CARD_TITLE = ".evnt-event-name>h1>span";
    public static final String CARD_STATUS = ".status";
    public static final String CARD_LOCATION = ".location>span";
    public static final String CARD_LOCATION_ONLINE = ".online>span";
    public static final String CARD_SPEAKER = "div[data-name]";
    public static final String CARD_LANGUAGE = ".language>span";
    public static final String CARD_SPEAKER_BLOCK = ".speakers";
    public static final String ALL_ELEMENTS_ON_CARD = ".//div[contains(@class,'language-cell') or contains(@class,'online-cell') " +
                                                            "or contains(@class,'evnt-event-name') " +
                                                            "or contains(@class,'evnt-event-dates-table')]";

    @FindBy(css = CARD_DATE)
    public WebElement date;

    @FindBy(css = CARD_TITLE)
    public WebElement title;

    @FindBy(css = CARD_STATUS)
    public WebElement status;

    @FindBy(css = CARD_LOCATION)
    public WebElement location;

    @FindBy(css = CARD_LOCATION_ONLINE)
    public WebElement locationOnline;

    @FindBy(css = CARD_SPEAKER)
    public WebElement speaker;

    @FindBy(css = CARD_LANGUAGE)
    public WebElement language;

    @FindBy(css = CARD_SPEAKER_BLOCK)
    public WebElement speakerBlock;

}
