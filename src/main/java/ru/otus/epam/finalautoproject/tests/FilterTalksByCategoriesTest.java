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
import ru.otus.epam.finalautoproject.enums.NavigationBar;
import ru.otus.epam.finalautoproject.pagesandblocks.pages.MainPage;
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
    public void filterByCategories(){
        talksLibraryPage.moreFilterButtonClick();
        talksLibraryPage.filterByCategory(SEARCH_CATEGORY);
        talksLibraryPage.filterByLocation(SEARCH_LOCATION);
        talksLibraryPage.filterByLanguage(SEARCH_LANGUAGE);
    }
}
