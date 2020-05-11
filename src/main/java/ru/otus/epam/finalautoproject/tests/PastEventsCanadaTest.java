package ru.otus.epam.finalautoproject.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.otus.epam.finalautoproject.FinalAutoProjectApplication;
import ru.otus.epam.finalautoproject.config.BaseWebDrivingTest;
import ru.otus.epam.finalautoproject.config.Config;
import ru.otus.epam.finalautoproject.enums.Events;
import ru.otus.epam.finalautoproject.enums.NavigationBar;
import ru.otus.epam.finalautoproject.models.EventCard;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventCardPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Test(groups = "smoke")
public class PastEventsCanadaTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(PastEventsCanadaTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private EventsPage eventsPage;
    @Autowired
    private EventCardPage eventCardPage;

    @BeforeClass(alwaysRun = true)
    public void init(){
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.EVENTS);
    }

    private List<EventCard> eventCardList = new ArrayList<>();

    @Test(description = "Перейти на Past Events. Отфильтровать события по блоку Location = Canada" +
            "Проверить, что отображаются карточки прошедших мероприятий. " +
            "Количество карточек равно счетчику на кнопке Past Events")
    public void checkPastEvents(){
        eventsPage.goToEventsView(Events.PAST_EVENTS);
        /*Отфильтровать метроприятия по блоку Location = Canada*/
        eventsPage.filterByLocation("Czechia");
        /*Проверить, что отображаются карточки предстоящих мероприятий*/
        int currentUpcomingEventsCount= eventsPage.getEventsCount();
        softAssert.assertTrue(currentUpcomingEventsCount!=0,"Прошедших мероприятий нет!");
        //Проверить, что количество карточек соотвествует счетчику
        int eventsExpectedCount = Integer.parseInt(eventsPage.eventsTabsNavBlock.pastEventsCounter.getText());
        softAssert.assertEquals(currentUpcomingEventsCount,eventsExpectedCount,
                "Количество отображаемых карточек НЕ совпадает с количеством указанном на счетчике событий. Ошибка!");
        softAssert.assertAll();
    }

    @Test(description = "Проверить, что Даты проведенных мероприятий меньше текущей даты",
            dependsOnMethods = "checkPastEvents")
    public void checkDateEvents(){
        eventsPage.setEventCardList(eventCardList);
        eventCardList.forEach(card->{
            LocalDate nowDate = LocalDate.now();
            softAssert.assertTrue(card.getDate().isBefore(nowDate),"Дата мероприятия ["+ card.getDate() +"] больше или равна текущей");
        });
        softAssert.assertAll();
    }
}
