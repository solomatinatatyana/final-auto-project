package ru.otus.epam.finalautoproject.config;


import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.epam.finalautoproject.config.driver.WebApplicationService;
import ru.otus.epam.finalautoproject.config.ui.BrowserType;

import java.util.concurrent.TimeUnit;

@PropertySource("classpath:properties/application.properties")
@Configuration
@ComponentScan
public class Config {
    protected WebDriver driver;
    protected MutableCapabilities options;
    protected static String browser = System.getProperty("browser").toUpperCase();

    @Value("${sut.url}")
    private String url;

    public String getUrl() {
        return url;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    private WebApplicationService webApplicationService;

    @Bean
    public WebDriver getDriver() {
        this.options = new MutableCapabilities();
        this.driver = webApplicationService.initDriver(BrowserType.valueOf(browser), options);
        this.driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        this.driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
        return this.driver;
    }
}
