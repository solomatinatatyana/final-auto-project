package ru.otus.epam.finalautoproject.tests;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.otus.epam.finalautoproject.FinalAutoProjectApplication;
import ru.otus.epam.finalautoproject.config.BaseWebDrivingTest;
import ru.otus.epam.finalautoproject.config.Config;
import ru.otus.epam.finalautoproject.enums.NavigationBar;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.TalksLibraryPage;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Test(groups = "smoke")
public class SearchArticlesByKeyWordTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(SearchArticlesByKeyWordTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private TalksLibraryPage talksLibraryPage;

    /*
     * Тестовые данные
     */
    private static final String searchText = "Azure";

    @BeforeClass(alwaysRun = true)
    public void init(){
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.TALKS_LIBRARY);
    }

    @Test(description = "Проверка фильтрации докладов по ключевому слову - Azure")
    public void checkFilterTalks() throws InterruptedException {
        talksLibraryPage.filterByKeyWord(searchText);
        int countCards = talksLibraryPage.getEventsCount();
        log.info("Всего на странице карточек " + countCards);
        talksLibraryPage.eventTalkCardList.forEach(card->{
            String title = card.findElement(By.cssSelector(".evnt-talk-name>h1>span")).getText();
            softAssert.assertTrue(StringUtils.containsIgnoreCase(title,searchText),
                    "В названии карточки ["+ title +"] не содержится поисковое слово Azure");
        });
        softAssert.assertAll();
    }
}
