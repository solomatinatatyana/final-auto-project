package ru.otus.epam.finalautoproject.config;

import com.epam.healenium.SelfHealingDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.otus.epam.finalautoproject.config.ui.BrowserType;

import java.net.MalformedURLException;

/**
 * Класс для запуска тестов с использованием браузера
 */
public class BaseWebDrivingTest extends BaseTest {
    protected SelfHealingDriver driver;
    @Autowired
    public Config config;

    public void setDriver(SelfHealingDriver driver) {
        this.driver = driver;
    }

    protected static String browser = System.getProperty("browser").toUpperCase();
    
    @BeforeClass(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        super.setUp();
        log.info("Test: [{}]", this.getClass().asSubclass(this.getClass()).getSimpleName());
        log.info("Browser: [{}]", BrowserType.valueOf(browser));
        this.setDriver(config.getDriver());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        log.info("END of TEST - {}", getClass().asSubclass(getClass().getSuperclass()).getSimpleName());
        try {
            if (this.driver != null) {
                this.driver.quit();
            }
            log.info("Browser and Driver Killed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
