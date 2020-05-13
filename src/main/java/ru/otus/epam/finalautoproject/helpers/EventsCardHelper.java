package ru.otus.epam.finalautoproject.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.epam.finalautoproject.config.Config;
import ru.otus.epam.finalautoproject.models.EventCard;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventCardBlock.*;
import static ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage.EVENT_CARD;

@Component
public class EventsCardHelper {
    private Logger log = LogManager.getLogger(EventsCardHelper.class);
    @Autowired
    private WebElementsHelper elementsHelper;
    @Autowired
    private DateHelper dateHelper;
    @Autowired
    private EventsPage eventsPage;
    @Autowired
    private Config config;

    /**
     * Получить информацию по каждому мероприятию из списка мероприятий
     * @param cardList список мероприятий
     */
    public void setEventCardList(List<EventCard> cardList) {
        String eventsCardsParsed = config.getDriver().getPageSource();
        Document html = Jsoup.parse(eventsCardsParsed);
        Elements cards = html.select(EVENT_CARD);
        cards.forEach(card -> {
            String location = getLocation(card);
            String date = card.select(CARD_DATE).text();
            String name = card.select(CARD_TITLE).text();
            String status = card.select(CARD_STATUS).text();
            String language = card.select(CARD_LANGUAGE).text();
            List<String> speakerList = setSpeakerList(card);
            cardList.add(new EventCard(EventCard.anCard()
                    .withLocation(location)
                    .withLanguage(language)
                    .withName(name)
                    .withDate(dateHelper.getDate(date))
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
    private String getLocation(Element element){
        String location = "";
        if(elementsHelper.isElementPresent(element,CARD_LOCATION)){
            location = element.select(CARD_LOCATION).text();
            log.info("Место проведения мероприятия: " + location);
        }else if(elementsHelper.isElementPresent(element,CARD_LOCATION_ONLINE)){
            location = element.select(CARD_LOCATION_ONLINE).text();
            log.info("Место проведения мероприятия: online");
        }else{
            log.info("Блок с местом проведения мероприятия остуствует");
        }
        return location;
    }

    /**
     * Получить список спикеров на карточен мероприятия
     * @param element карточка
     * @return speakerList
     */
    private List<String> setSpeakerList(Element element) {
        List<String> speakerList = new ArrayList<>();
        Elements speakersElements = element.select(CARD_SPEAKER);
        if(!speakersElements.isEmpty()) {
            speakersElements.forEach(el -> {
                String spName = el.attr("data-name");
                if (spName != null) speakerList.add(spName);
            });
        }
        return speakerList;
    }
}
