package com.yogesh.automation.pages;

import com.yogesh.automation.config.ConfigReader;
import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Shared behaviour for all page objects: a driver reference and explicit-wait helpers.
 * Page objects extend this and expose intent-revealing methods, never raw locators.
 */
public abstract class BasePage {

    protected final RemoteWebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(RemoteWebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait.seconds")));
    }

    protected WebElement waitVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void tap(WebElement element) {
        waitClickable(element).click();
    }

    protected String textOf(WebElement element) {
        return waitVisible(element).getText();
    }
}
