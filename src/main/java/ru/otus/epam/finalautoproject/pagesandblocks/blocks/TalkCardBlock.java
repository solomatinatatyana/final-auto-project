package ru.otus.epam.finalautoproject.pagesandblocks.blocks;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.AbstractPage;

@Component
public class TalkCardBlock extends AbstractPage {
    public TalkCardBlock(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public final String TALK_TITLE = ".evnt-talk-name>h1>span";
    public final String LANGUAGE = ".language";

    @FindBy(css = TALK_TITLE)
    public WebElement talkTitle;

    @FindBy(css = LANGUAGE)
    public WebElement language;

}
