package com.company.ruzaliia_yakunina.automation.selenium.tests;

import com.company.ruzaliia_yakunina.automation.selenium.pages.CurrentDayTabFragment;
import com.company.ruzaliia_yakunina.automation.selenium.pages.NextDayTabFragment;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

/**
 * Created by Рузалия on 25.06.2016.
 */
@Test
public class SinoptikTest {

    private static final String DRIVER_NAME = "webdriver.chrome.driver";
    private static final String PATH_TO_DRIVER = "src/main/resources/drivers/chromedriver.exe";
    private final String URL_SITE = "https://ua.sinoptik.ua/погода-харків";
    private CurrentDayTabFragment currentDayTabFragment;
    private NextDayTabFragment nextDayTabFragment;
    private final String LOCALHOST_URL = "http://localhost:4444/wd/hub";
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ThreadLocal<Capabilities> capabilities = new ThreadLocal<Capabilities>() {
        @Override
        protected Capabilities initialValue() {
            return DesiredCapabilities.firefox();
        }
    };

    @BeforeClass
    @Parameters("browser")
    public void openSiteAndTabForTesting(String browser) {
        URL hubUrl = null;
        try {
            hubUrl = new URL(LOCALHOST_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        switch (browser) {
            case "chrome":
                System.setProperty(DRIVER_NAME, PATH_TO_DRIVER);
                capabilities.set(DesiredCapabilities.chrome());
                driver.set(new RemoteWebDriver(hubUrl, capabilities.get()));
                break;
            case "firefox":
                capabilities.set(DesiredCapabilities.firefox());
                driver.set(new RemoteWebDriver(hubUrl, capabilities.get()));
                break;
        }
        currentDayTabFragment = new CurrentDayTabFragment(driver.get());
        driver.get().get(URL_SITE);
        currentDayTabFragment.clickOnNextDayTab();
        nextDayTabFragment = new NextDayTabFragment(driver.get());
    }

    @Test
    public void verifyThatMinAndMaxTemperaturesForDayMatchesMinAndMaxInDetailedList() {
        int minTemperatureForDay = nextDayTabFragment.getMinTemperature();
        int maxTemperatureForDay = nextDayTabFragment.getMaxTemperature();
        int minTemperatureInDetiledList = nextDayTabFragment.findMinTemperatureInDetailedListOfTemperatures();
        int maxTemperatureInDetiledList = nextDayTabFragment.findMaxTemperatureInDetailedListOfTemperatures();
        assertEquals(minTemperatureForDay, minTemperatureInDetiledList, "Minimum temperatures do not match.");
        assertEquals(maxTemperatureForDay, maxTemperatureInDetiledList, "Maximum temperatures do not match.");
    }

    @AfterClass
    public void tearDown() {
        driver.get().close();
        driver.get().quit();
    }
}
