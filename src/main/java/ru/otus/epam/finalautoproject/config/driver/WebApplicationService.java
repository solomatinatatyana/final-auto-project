package ru.otus.epam.finalautoproject.config.driver;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.MutableCapabilities;
import ru.otus.epam.finalautoproject.config.ui.BrowserType;

import java.net.MalformedURLException;

public interface WebApplicationService {
    SelfHealingDriver initDriver(BrowserType browser, MutableCapabilities options) throws MalformedURLException;
}
