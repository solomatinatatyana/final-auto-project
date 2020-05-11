package ru.otus.epam.finalautoproject.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.models.EventCard;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventsCardHelper {
    private Logger log = LogManager.getLogger(EventsCardHelper.class);
    @Autowired
    private WebElementsHelper elementsHelper;
    @Autowired
    private EventsPage eventsPage;

    /**
     * Получить информацию по каждому мероприятию из списка мероприятий
     * @param cardList список мероприятий
     */
    public void setEventCardList(List<EventCard> cardList){
        eventsPage.eventCardList.forEach(card -> {
            String location = eventsPage.getGlobalLocation(card).getText();
            String date = card.findElement(eventsPage.eventCardBlock.CARD_DATE_LOCATOR).getText();
            String name = card.findElement(eventsPage.eventCardBlock.CARD_NAME_LOCATOR).getText();
            String status = card.findElement(eventsPage.eventCardBlock.CARD_STATUS_LOCATOR).getText();
            String language = card.findElement(eventsPage.eventCardBlock.CARD_LANGUAGE_LOCATOR).getText();
            List<String> speakerList = setSpeakerList(card);
            cardList.add(new EventCard(EventCard.anCard()
                    .withLocation(location)
                    .withLanguage(language)
                    .withName(name)
                    .withDate(eventsPage.eventCardBlock.getLocalDate(date))
                    .withRegistrationInfo(status)
                    .withSpeakerList(speakerList)
                    .build()
            ));
        });
    }

    /**
     * Получить место проведения мероприятия
     * @param element карточка события
     * @return location
     */
    private String getLocation(WebElement element){
        String location = "";
        try{
            if(elementsHelper.isElementPresent(element,eventsPage.eventCardBlock.CARD_LOCATION_LOCATOR)){
                location = eventsPage.eventCardBlock.getLocation().getText();
                log.info("Место проведения мероприятия: " + location);
            }else if(elementsHelper.isElementPresent(element,eventsPage.eventCardBlock.CARD_LOCATION_ONLINE_LOCATOR)){
                location = eventsPage.eventCardBlock.getOnlineLocation().getText();
                log.info("Место проведения мероприятия: online");
            }else {
                log.info("Блок с местом проведения мероприятия остуствует");
            }
        }catch (NoSuchElementException e){
            log.info("Блок с местом проведения мероприятия остуствует");
        }
        return location;
    }

    /**
     * Получить список спикеров на карточен мероприятия
     * @param element карточка
     * @return speakerList
     */
    private List<String> setSpeakerList(WebElement element){
        List<String> speakerList = new ArrayList<>();
        List<WebElement> speakersElements = element.findElements(eventsPage.eventCardBlock.CARD_SPEAKER_LOCATOR);
        speakersElements.forEach(el->{
            String spName = el.getAttribute("data-name");
            if (spName!=null) speakerList.add(spName);
        });
        return speakerList;
    }
}
