package com.company.ruzaliia_yakunina.automation.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Рузалия on 25.06.2016.
 */
public class CurrentDayTabFragment extends BasePage {

    private By nextDayTabLocator = By.id("bd2");

    public CurrentDayTabFragment(WebDriver driver) {
        super(driver);
    }

    public NextDayTabFragment clickOnNextDayTab() {
        wait.until(ExpectedConditions.presenceOfElementLocated(nextDayTabLocator)).click();
        return new NextDayTabFragment(driver);
    }
}
