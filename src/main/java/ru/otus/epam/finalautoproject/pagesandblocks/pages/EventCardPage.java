package ru.otus.epam.finalautoproject.pagesandblocks.pages;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

@Component
public class EventCardPage extends AbstractPage{

    public EventCardPage(SelfHealingDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public By headerSection = By.cssSelector("section[id = 'home'] .evnt-panel-wrapper");
    public By registrationButton = By.cssSelector(".reg-button");
    public By headerDate = By.cssSelector(".date");
    public By agendaBlock = By.cssSelector("div[id = 'agenda']");
    public By eventInfoBlock = By.cssSelector(".evnt-icon-points-wrapper");
    public By dateBlock = By.xpath(".//div[@class = 'evnt-icon-point'][1]");
    public By locationBlock = By.xpath(".//div[@class = 'evnt-icon-point'][2]");
    ///div[@class = 'evnt-icon-info']/h4

}
