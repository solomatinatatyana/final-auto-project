package ru.otus.epam.finalautoproject.config.driver;

import com.epam.healenium.SelfHealingDriver;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.stereotype.Service;
import ru.otus.epam.finalautoproject.config.ui.BrowserType;

import java.util.Arrays;

@Service
public class WebApplicationServiceImpl implements WebApplicationService {
    @Override
    public SelfHealingDriver initDriver(BrowserType browser, MutableCapabilities options) {
        SelfHealingDriver driver = null;
        WebDriver delegate;
        Config config = ConfigFactory.load("properties/healenium.properties");
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(Arrays.asList("--start-maximized",
                        "--allow-file-access-from-files",
                        "--allow-running-insecure-content",
                        "--disable-notifications",
                        "--disable-infobars",
                        "--disable-file-cookies",
                        "--disable-web-security",
                        "--disable-extensions",
                        "--disable-feature=VizDisplayCompositor",
                        "--incognito"));
                options.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                delegate = new ChromeDriver();
                driver = SelfHealingDriver.create(delegate, config);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                options.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
                delegate = new FirefoxDriver();
                driver = SelfHealingDriver.create(delegate, config);
                break;
        }
        return driver;
    }
}
