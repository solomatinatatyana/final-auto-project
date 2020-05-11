package ru.otus.epam.finalautoproject.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.otus.epam.finalautoproject.FinalAutoProjectApplication;
import ru.otus.epam.finalautoproject.config.BaseWebDrivingTest;
import ru.otus.epam.finalautoproject.config.Config;
import ru.otus.epam.finalautoproject.enums.Events;
import ru.otus.epam.finalautoproject.enums.NavigationBar;
import ru.otus.epam.finalautoproject.helpers.EventsCardHelper;
import ru.otus.epam.finalautoproject.helpers.WebElementsHelper;
import ru.otus.epam.finalautoproject.models.EventCard;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Test(groups = "smoke")
public class ViewCardsEventTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(ViewCardsEventTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private EventsPage eventsPage;
    @Autowired
    private EventsCardHelper eventsCardHelper;
    @Autowired
    private WebElementsHelper elementsHelper;
    /*
    * Тестовые данные
    * */
    private List<EventCard> eventCardList = new ArrayList<>();
    private List<WebElement> elementsBlockList = new ArrayList<>();

    @BeforeClass(alwaysRun = true)
    public void init(){
        elementsBlockList.addAll(Arrays.asList(
                eventsPage.eventCardBlock.getOnlineLocation(),
                eventsPage.eventCardBlock.getLanguage(),
                eventsPage.eventCardBlock.getName(),
                eventsPage.eventCardBlock.getStatus(),
                eventsPage.eventCardBlock.getSpeakersBlock()
        ));
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.EVENTS);
    }

    @Test(description = "Перейти на Upcoming Events. " +
            "Проверить, что отображаются карточки предстоящих мероприятий")
    public void checkUpcomingEvents(){
        eventsPage.goToEventsView(Events.UPCOMING_EVENTS);
        /*Проверить, что отображаются карточки предстоящих мероприятий*/
        int currentUpcomingEventsCount= eventsPage.getEventsCount();
        Assert.assertTrue(currentUpcomingEventsCount!=0,"Предстоящих мероприятий нет!");
    }

    @Test(description = "Проверить, что все блоки с информацией присутствуют на карточке",
            dependsOnMethods = "checkUpcomingEvents")
    public void isPresetElements(){
        eventsPage.eventCardList.forEach(card->{
            softAssert.assertTrue(
                    (elementsHelper.isElementPresent(card, eventsPage.eventCardBlock.CARD_LANGUAGE_LOCATOR))
                            ||(elementsHelper.isElementPresent(card, eventsPage.eventCardBlock.CARD_LOCATION_ONLINE_LOCATOR)));
            softAssert.assertTrue(elementsHelper.isElementPresent(card, eventsPage.eventCardBlock.CARD_LANGUAGE_LOCATOR));
            softAssert.assertTrue(elementsHelper.isElementPresent(card, eventsPage.eventCardBlock.CARD_NAME_LOCATOR));
            softAssert.assertTrue(elementsHelper.isElementPresent(card, eventsPage.eventCardBlock.CARD_DATE_LOCATOR));
            softAssert.assertTrue(elementsHelper.isElementPresent(card, eventsPage.eventCardBlock.CARD_STATUS_LOCATOR));
            softAssert.assertTrue(elementsHelper.isElementPresent(card, eventsPage.eventCardBlock.CARD_SPEAKER_LOCATOR));
        });
        softAssert.assertAll();
    }

    @Test(description = "Проверить, что указанная информаци на карточке мероприятия не пустая",
    dependsOnMethods = "isPresetElements",alwaysRun = true)
    public void checkCardInfo(){
        int countCard = eventsPage.getEventsCount();
        log.info("Всего карточек на странице: " + countCard);
        eventsCardHelper.setEventCardList(eventCardList);
        eventCardList.forEach(card->{
            softAssert.assertFalse(card.getLocation().isEmpty(),"Не указано место для проведения мероприятия");
            softAssert.assertFalse(card.getName().isEmpty(),"Не указано название мероприятия на карточке");
            softAssert.assertFalse(card.getLanguage().isEmpty(),"Не указан язык мероприятия");
            softAssert.assertFalse(card.getDate().toString().isEmpty(),"Не указана дата мероприятия");
            softAssert.assertFalse(card.getRegistrationInfo().isEmpty(),"Не указана информации о регистрации мероприятия");
            softAssert.assertTrue(card.getSpeakerList().size()!=0,"Не указаны спикеры мероприятия");
        });
        softAssert.assertAll();
    }

    @Test(description = "Проверить порядок отображаемых блоков с информацией в карточке мероприятия",
    dependsOnMethods = "checkCardInfo", alwaysRun = true)
    public void checkOrderInfo(){
        List<WebElement> currentElementsBlockList = new ArrayList<>();
        eventsPage.eventCardList.forEach(card->{
            //todo
        });
    }
}
