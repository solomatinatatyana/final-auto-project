package ru.otus.epam.finalautoproject.tests;


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
import ru.otus.epam.finalautoproject.helpers.DateHelper;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
import static ru.otus.epam.finalautoproject.pagesandblocks.blocks.EventCardBlock.CARD_DATE;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Epic("Spring Tests")
@Feature("Работа с событиями")
@Story("Валидация дат предстоящих мероприятий")
@Test(groups = "smoke")
public class ValidationUpcomingEventsDateTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(ValidationUpcomingEventsDateTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private EventsPage eventsPage;
    @Autowired
    private DateHelper dateHelper;

    @BeforeClass(alwaysRun = true)
    public void init(){
        log.info("currentThread is " + Thread.currentThread().getId());
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.EVENTS);
    }

    @Description("Перейти на Upcoming Events.Проверить, что отображаются карточки предстоящих мероприятий")
    @Test()
    public void checkUpcomingEvents() throws MalformedURLException {
        eventsPage.goToEventsView(Events.UPCOMING_EVENTS);
        /*Проверить, что отображаются карточки предстоящих мероприятий*/
        int currentUpcomingEventsCount= eventsPage.getEventsCount();
        log.info("Всего на странице карточек " + currentUpcomingEventsCount);
        Assert.assertTrue(currentUpcomingEventsCount!=0,"Предстоящих мероприятий нет!");
    }

    @Description("Проверить, что В блоке This week даты проведения мероприятий больше или равны текущей дате" +
            " и даты находятся в пределах текущей недели")
    @Test(dependsOnMethods = "checkUpcomingEvents")
    public void ValidateDates(){
        List<WebElement> cardElements = eventsPage.getEventCardThisWeekList();
        log.info("Всего на странице карточек в блоке [This Week]: " + cardElements.size());
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(previousOrSame(MONDAY));
        LocalDate sunday = today.with(nextOrSame(SUNDAY));
        log.info("\n"+"Текущая дата: " + today.toString() + "\n" +
                    "Дата начала недели: " + monday + "\n" +
                    "Дата окончания недели: " + sunday);
        Assert.assertTrue(cardElements.size()!=0,"Карточек в блоке [This Week] нету!");
        cardElements.forEach(card->{
            LocalDate date = dateHelper.getDate(card.findElement(By.cssSelector(CARD_DATE)).getText());
            log.info("Дата проведения мероприятия на карточке: " + date.toString());
            softAssert.assertTrue(date.isAfter(today)||date.isEqual(today),"Текущая дата меньше или не равна дате проведения мероприятия");
            softAssert.assertTrue(((date.isAfter(monday)||date.isEqual(monday)) && (date.isBefore(sunday)||date.isEqual(sunday))),
                    "Дата мероприятия "+ date.toString() +" выходит за пределы недели");
        });
        softAssert.assertAll();
    }
}
