package ru.otus.epam.finalautoproject.pagesandblocks.blocks;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.AbstractPage;

@Component
public class EventNavigationNavBarBlock extends AbstractPage {

    public EventNavigationNavBarBlock(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = ".//ul[@class='evnt-navigation navbar-nav']//a[@href='/calendar']")
    public WebElement calendarButton;

    @FindBy(xpath = ".//ul[@class='evnt-navigation navbar-nav']//a[@href='/events']")
    public WebElement eventsButton;

    @FindBy(xpath = ".//ul[@class='evnt-navigation navbar-nav']//a[@href='/talks']")
    public WebElement talksLibraryButton;

    @FindBy(xpath = ".//ul[@class='evnt-navigation navbar-nav']//a[@href='/speakers']")
    public WebElement speakersButton;


}
