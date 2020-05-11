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
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;


@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Test(groups = "smoke")
public class UpcomingEventsTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(UpcomingEventsTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private EventsPage eventsPage;

    @BeforeClass(alwaysRun = true)
    public void init(){
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.EVENTS);
    }

    @Test(description = "Перейти на Upcoming Events. " +
            "Проверить, что отображаются карточки предстоящих мероприятий. " +
            "Количество карточек равно счетчику на кнопке Upcoming Events")
    public void checkUpcomingEvents(){
        eventsPage.goToEventsView(Events.UPCOMING_EVENTS);
        /*Проверить, что отображаются карточки предстоящих мероприятий*/
            int currentUpcomingEventsCount= eventsPage.getEventsCount();
        softAssert.assertTrue(currentUpcomingEventsCount!=0,"Предстоящих мероприятий нет!");
        //Проверить, что количество карточек соотвествует счетчику
        int eventsExpectedCount = Integer.parseInt(eventsPage.eventsTabsNavBlock.upcomingEventsCounter.getText());
        softAssert.assertEquals(currentUpcomingEventsCount,eventsExpectedCount,
                "Количество отображаемых карточек НЕ совпадает с количеством указанном на счетчике событий. Ошибка!");
        softAssert.assertAll();
    }
}
