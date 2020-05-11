package ru.otus.epam.finalautoproject.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

/**
 * Безовый класс для запуска тестов. Содержит настройки логирования и другие общие настройки
 */
@DirtiesContext
public class BaseTest extends AbstractTestNGSpringContextTests {
    protected static Logger log;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        log = LogManager.getLogger("TestRunner");
    }
}
