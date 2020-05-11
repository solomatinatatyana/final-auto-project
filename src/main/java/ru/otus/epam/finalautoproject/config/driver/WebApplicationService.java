package ru.otus.epam.finalautoproject.config.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import ru.otus.epam.finalautoproject.config.ui.BrowserType;

public interface WebApplicationService {
    WebDriver initDriver(BrowserType browser, MutableCapabilities options);
}
