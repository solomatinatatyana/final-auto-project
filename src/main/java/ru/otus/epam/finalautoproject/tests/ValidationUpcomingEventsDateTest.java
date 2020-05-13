package ru.otus.epam.finalautoproject.tests;


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
import ru.otus.epam.finalautoproject.helpers.DateHelper;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;

import java.time.LocalDate;
import java.util.List;

import static ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventCardBlock.CARD_DATE;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Test(groups = "smoke")
public class ValidationUpcomingEventsDateTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(ValidationUpcomingEventsDateTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private EventsPage eventsPage;
    @Autowired
    private DateHelper dateHelper;

    /*
     * Тестовые данные
     */

    @BeforeClass(alwaysRun = true)
    public void init(){
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.EVENTS);
    }

    @Test(description = "Перейти на Upcoming Events. " +
            "Проверить, что отображаются карточки предстоящих мероприятий")
    public void checkUpcomingEvents(){
        eventsPage.goToEventsView(Events.UPCOMING_EVENTS);
        /*Проверить, что отображаются карточки предстоящих мероприятий*/
        int currentUpcomingEventsCount= eventsPage.getEventsCount();
        log.info("Всего на странице карточек " + currentUpcomingEventsCount);
        Assert.assertTrue(currentUpcomingEventsCount!=0,"Предстоящих мероприятий нет!");
    }

    @Test(description = "Проверить, что В блоке This week даты проведения мероприятий больше или равны текущей дате",
            dependsOnMethods = "checkUpcomingEvents")
    public void ValidateDates(){
        List<WebElement> cardElements = eventsPage.getEventCardThisWeekList();
        LocalDate nowDate = LocalDate.now();
        cardElements.forEach(card->{
            LocalDate date = dateHelper.getDate(card.findElement(By.cssSelector(CARD_DATE)).getText());
            softAssert.assertTrue((date.isAfter(nowDate))||(date.isEqual(nowDate)),"Текущая дата меньше или не равна дате проведения мероприятия");
        });
        softAssert.assertAll();
    }

    @Test(description = "Проверить, что даты находятся в пределах текущей недели",
            dependsOnMethods = "ValidateDates", alwaysRun = true)
    public void ValidateDatesInIntervalWeek(){
        //todo
    }
}
