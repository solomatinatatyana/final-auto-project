package ru.otus.epam.finalautoproject.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import ru.otus.epam.finalautoproject.helpers.WebElementsHelper;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventCardPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.EventsPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Test(groups = "smoke")
public class ViewDetailEventInfoTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(ViewDetailEventInfoTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private EventsPage eventsPage;
    @Autowired
    private EventCardPage eventCardPage;
    @Autowired
    private WebElementsHelper webElementsHelper;
    @BeforeClass(alwaysRun = true)
    public void init(){
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.EVENTS);
    }

    @Test(description = "Перейти на Upcoming Events. ")
    public void goToUpcomingEventsView(){
        eventsPage.goToEventsView(Events.UPCOMING_EVENTS);
        /*Проверить, что отображаются карточки предстоящих мероприятий*/
        int currentUpcomingEventsCount= eventsPage.getEventsCount();
        log.info("Всего на странице карточек " + currentUpcomingEventsCount);
        Assert.assertTrue(currentUpcomingEventsCount!=0,"Предстоящих мероприятий нет!");
    }

    @Test(description = "Проверить, что на странице с информацией о мероприятии " +
            "отображается шапка с кнопкой для регистрации и " +
            "основная часть с программой мероприятия, датой, временем, местом проведения",
            dependsOnMethods = "goToUpcomingEventsView")
    public void checkBlocksInfoIsPresent(){
        /*Перейти в первую карточку*/
        log.info("Переходим в карточку");
        eventsPage.goToCard(0);
       /*Проверить присутвие шапки с кнопкной регистрации*/
        log.info("Проверка присутсвия шапки...");
        softAssert.assertTrue(webElementsHelper.isElementPresent(eventCardPage.headerSection),"Шапка не отображается");
        log.info("Проверка присутсвия кнопки регистрации...");
        softAssert.assertTrue(webElementsHelper.isElementPresent(eventCardPage.registrationButton),
                "Кнопка регистрации не отображается");
        /*Проверить присутвие основной части*/
        log.info("Проверка присутсвия блока с информацией...");
        softAssert.assertTrue(webElementsHelper.isElementPresent(eventCardPage.eventInfoBlock),
                "Блок с информацией о месте проведения и датой не отображается");
        log.info("Проверка присутсвия информации о дате мероприятия ...");
        softAssert.assertTrue(webElementsHelper.isElementPresent(eventCardPage.dateBlock),
                "Информация с датой проведения мероприятия не отображается");
        log.info("Проверка присутсвия информации о месте проведения мероприятия ...");
        softAssert.assertTrue(webElementsHelper.isElementPresent(eventCardPage.locationBlock),
                "Информация о месте проведения мероприятия не отображается");
        log.info("Проверка присутсвия информации о программе мероприятия");
        softAssert.assertTrue(webElementsHelper.isElementPresent(eventCardPage.agendaBlock),
                "Блок с программой мероприятия не отображается");
        softAssert.assertAll();
    }
}
