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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;
import ru.otus.epam.finalautoproject.config.ui.BrowserType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Service
public class WebApplicationServiceImpl implements WebApplicationService {
    @Override
    public SelfHealingDriver initDriver(BrowserType browser, MutableCapabilities options) throws MalformedURLException {
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
                chromeOptions.setCapability(CapabilityType.BROWSER_NAME,"chrome");
                chromeOptions.setCapability(CapabilityType.BROWSER_VERSION,"81.0");
                chromeOptions.setCapability("enableVNC", true);
                chromeOptions.setCapability("enableVideo", false);
                options.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                //delegate = new RemoteWebDriver(new URL("http://0.0.0.0:4444/wd/hub"),options);
                delegate = new ChromeDriver();
                driver = SelfHealingDriver.create(delegate, config);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                options.setCapability(CapabilityType.BROWSER_NAME,"firefox");
                options.setCapability(CapabilityType.BROWSER_VERSION,"73.0");
                options.setCapability("enableVNC", true);
                options.setCapability("enableVideo", false);
                options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                options.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
                //delegate = new RemoteWebDriver(new URL("http://0.0.0.0:4444/wd/hub"),options);
                delegate = new FirefoxDriver();
                driver = SelfHealingDriver.create(delegate, config);
                break;
        }
        return driver;
    }
}
