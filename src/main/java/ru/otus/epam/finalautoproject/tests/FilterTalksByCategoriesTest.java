package ru.otus.epam.finalautoproject.tests;

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
import ru.otus.epam.finalautoproject.pagesandblocks.pages.TalksCardPage;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.TalksLibraryPage;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Test(groups = "smoke")
public class FilterTalksByCategoriesTest extends BaseWebDrivingTest {
    private Logger log = LogManager.getLogger(FilterTalksByCategoriesTest.class);
    @Autowired
    private MainPage mainPage;
    @Autowired
    private TalksLibraryPage talksLibraryPage;
    @Autowired
    private TalksCardPage talksCardPage;

    /*
     * Тестовые данные
     */
    private static final String SEARCH_CATEGORY = "Design";
    private static final String SEARCH_LOCATION = "Belarus";
    private static final String SEARCH_LANGUAGE = "ENGLISH";

    @BeforeClass(alwaysRun = true)
    public void init(){
        mainPage.open(config.getUrl());
        mainPage.goToNavView(NavigationBar.TALKS_LIBRARY);
    }

    @Test(description = "Проверить Фильтрацию докладов по категориям: Category, Location, Language")
    public void filterByCategories() throws InterruptedException {
        talksLibraryPage.moreFilterButtonClick();
        /*Отфильтровать по категории*/
        talksLibraryPage.filterByCategory(SEARCH_CATEGORY);
        /*Отфильтровать по месту*/
        talksLibraryPage.filterByLocation(SEARCH_LOCATION);
        /*Отфильтровать по языку*/
        talksLibraryPage.filterByLanguage(SEARCH_LANGUAGE);
        int currentCountCards = talksLibraryPage.getEventsCount();
        log.info("Всего карточек на странице: " + currentCountCards);
        /*Проверить, что количество карточек на странице совпадает с числом результов по фильтрам*/
        int expectedCountResultCards = Integer.parseInt(talksLibraryPage.talksLibraryFilterBlock.resultsCount.getText());
        softAssert.assertEquals(currentCountCards,expectedCountResultCards,
                "Количество Каточек на странице не совпадает с числом найденных по фильтрам");
        /*Зайти в любую карточку и проверить наличие информации, указанной в фильтре*/
        talksLibraryPage.goToCard(0);
        String location = talksCardPage.location.getText();
        String language = talksCardPage.language.getText();
        log.info("\nМесто проведения: " + location + "\n"+
                    "Язык: " + language + "\n");
        softAssert.assertTrue(location.contains(SEARCH_LOCATION),"В месте проведения отуствует " + SEARCH_LOCATION);
        softAssert.assertTrue(language.contains(SEARCH_LANGUAGE),"Язык доклада не " + SEARCH_LANGUAGE);
        softAssert.assertTrue(talksCardPage.categoryIsPresent(By.xpath(".//label[contains(text(),'"+SEARCH_CATEGORY+"')]")),
                "В списке категорий нет " + SEARCH_CATEGORY);
        softAssert.assertAll();
    }
}
