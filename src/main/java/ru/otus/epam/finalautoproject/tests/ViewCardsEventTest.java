package ru.otus.epam.finalautoproject.tests;

import com.google.common.base.Strings;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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
import ru.otus.epam.finalautoproject.helpers.EventsAndTalksCardHelper;
import ru.otus.epam.finalautoproject.helpers.WebElementsHelper;
import ru.otus.epam.finalautoproject.models.EventCard;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventCardBlock.*;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Epic("Spring Tests")
@Feature("Работа с событиями")
@Story("Просмотр карточек мероприятий")
@Test(groups = "smoke")
public class ViewCardsEventTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(ViewCardsEventTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private EventsPage eventsPage;
    @Autowired
    private EventsAndTalksCardHelper eventsAndTalksCardHelper;
    @Autowired
    private WebElementsHelper elementsHelper;
    /*
    * Тестовые данные
    * */
    private List<EventCard> eventCardList = new ArrayList<>();
    private List<WebElement> elementsBlockList = new ArrayList<>();

    @BeforeClass(alwaysRun = true)
    public void init(){
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.EVENTS);
    }

    @Description("Перейти на Upcoming Events. Проверить, что отображаются карточки предстоящих мероприятий")
    @Test()
    public void checkUpcomingEvents() throws MalformedURLException {
        eventsPage.goToEventsView(Events.UPCOMING_EVENTS);
        /*Проверить, что отображаются карточки предстоящих мероприятий*/
        int currentUpcomingEventsCount= eventsPage.getEventsCount();
        Assert.assertTrue(currentUpcomingEventsCount!=0,"Предстоящих мероприятий нет!");
    }

    @Description("Проверить, что все блоки с информацией присутствуют на карточке")
    @Test(dependsOnMethods = "checkUpcomingEvents")
    public void isPresetElements(){
        eventsPage.eventCardList.forEach(card->{
            softAssert.assertTrue(
                    (elementsHelper.isElementPresent(card, By.cssSelector(CARD_LOCATION_ONLINE)))
                            ||(elementsHelper.isElementPresent(card, By.cssSelector(CARD_LOCATION))),
                    "Блок места проведения мероприятия не найден");
            softAssert.assertTrue(elementsHelper.isElementPresent(card, By.cssSelector(CARD_LANGUAGE)),
                    "Блок языка мероприятия не найден");
            softAssert.assertTrue(elementsHelper.isElementPresent(card, By.cssSelector(CARD_TITLE)),
                    "Блок Названия мероприятия не найден");
            softAssert.assertTrue(elementsHelper.isElementPresent(card, By.cssSelector(CARD_DATE)),
                    "Блок даты проведения мероприятия не найден");
            softAssert.assertTrue(elementsHelper.isElementPresent(card, By.cssSelector(CARD_SPEAKER_BLOCK)),
                    "Блок спикеров мероприятия не найден");
        });
        softAssert.assertAll();
    }

    @Description("Проверить, что указанная информаци на карточке мероприятия не пустая")
    @Test(dependsOnMethods = "isPresetElements",alwaysRun = true)
    public void checkCardInfo() throws MalformedURLException {
        int countCard = eventsPage.getEventsCount();
        log.info("Всего карточек на странице: " + countCard);
        eventsAndTalksCardHelper.setEventCardList(eventCardList);
        eventCardList.forEach(card->{
            softAssert.assertFalse(Strings.isNullOrEmpty(card.getLocation()),"Не указано место проведения мероприятия");
            softAssert.assertFalse(Strings.isNullOrEmpty(card.getName()),"Не указано название мероприятия на карточке");
            softAssert.assertFalse(Strings.isNullOrEmpty(card.getLanguage()),"Не указан язык мероприятия");
            softAssert.assertFalse(Strings.isNullOrEmpty(card.getDate().toString()),"Не указана дата мероприятия");
            softAssert.assertTrue(card.getSpeakerList().size()!=0,"Не указаны спикеры мероприятия");
        });
        softAssert.assertAll();
    }

    @Description("Проверить порядок отображаемых блоков с информацией в карточке мероприятия")
    @Test(dependsOnMethods = "checkCardInfo", alwaysRun = true)
    public void checkOrderInfo(){
        /*eventsPage.eventCardBlock.cardElementStructureList.size();
        List<WebElement> currentElementsBlockList = new ArrayList<>();
        eventsPage.eventCardList.forEach(card->{
            //card.findElements()
        });*/
    }
}
