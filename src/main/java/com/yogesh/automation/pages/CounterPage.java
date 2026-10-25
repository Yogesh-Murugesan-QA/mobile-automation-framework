package com.yogesh.automation.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object for the classic Flutter "counter" screen.
 *
 * <p>Locators use accessibility ids (Flutter {@code Semantics} labels / keys), which are the
 * fast, stable strategy for Flutter apps. The same id is declared for both platforms via
 * {@link AndroidFindBy} and {@link iOSXCUITFindBy}, so one page object drives Android and iOS;
 * {@link AppiumFieldDecorator} resolves the right one for the active driver at runtime.
 */
public class CounterPage extends BasePage {

    @AndroidFindBy(accessibility = "counter")
    @iOSXCUITFindBy(accessibility = "counter")
    private WebElement counterText;

    @AndroidFindBy(accessibility = "increment")
    @iOSXCUITFindBy(accessibility = "increment")
    private WebElement incrementButton;

    public CounterPage(RemoteWebDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public CounterPage tapIncrement() {
        tap(incrementButton);
        return this;
    }

    public CounterPage tapIncrement(int times) {
        for (int i = 0; i < times; i++) {
            tapIncrement();
        }
        return this;
    }

    public int counterValue() {
        return Integer.parseInt(textOf(counterText).trim());
    }
}
