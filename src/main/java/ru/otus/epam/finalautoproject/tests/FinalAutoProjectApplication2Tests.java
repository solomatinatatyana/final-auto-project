package ru.otus.epam.finalautoproject.tests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;
import ru.otus.epam.finalautoproject.FinalAutoProjectApplication;
import ru.otus.epam.finalautoproject.config.BaseWebDrivingTest;
import ru.otus.epam.finalautoproject.config.Config;

@SpringBootTest(classes = FinalAutoProjectApplication.class)
@ContextConfiguration(classes = Config.class)
@Test(groups = "smoke")
class FinalAutoProjectApplication2Tests extends BaseWebDrivingTest {

    @Test
    void contextLoads() {
        System.out.println("HelloWorld");
        driver.get(config.getUrl());
    }

}
