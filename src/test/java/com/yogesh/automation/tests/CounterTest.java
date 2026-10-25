package com.yogesh.automation.tests;

import com.yogesh.automation.pages.CounterPage;
import com.yogesh.automation.utils.TestDataReader;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Sample cross-platform tests for the Flutter counter screen. The same page object and
 * assertions run on Android and iOS; the platform is chosen by configuration, not by code.
 */
@Epic("Flutter Sample App")
@Feature("Counter")
public class CounterTest extends BaseTest {

    @Test(description = "Counter starts at zero")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies the counter displays 0 on a fresh app launch.")
    public void counterStartsAtZero() {
        CounterPage page = new CounterPage(driver());
        Assert.assertEquals(page.counterValue(), 0, "Counter should start at 0");
    }

    @Test(description = "Single increment advances the counter by one")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Taps the increment button once and asserts the counter reads 1.")
    public void singleIncrement() {
        CounterPage page = new CounterPage(driver());
        page.tapIncrement();
        Assert.assertEquals(page.counterValue(), 1, "Counter should read 1 after one tap");
    }

    @DataProvider(name = "incrementData")
    public Object[][] incrementData() {
        return TestDataReader.readArray("testdata/counter.json", "taps", "expected");
    }

    @Test(dataProvider = "incrementData",
          description = "Counter equals the number of taps")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Data-driven: taps the button N times and asserts the counter equals N.")
    public void dataDrivenIncrement(int taps, int expected) {
        CounterPage page = new CounterPage(driver());
        page.tapIncrement(taps);
        Assert.assertEquals(page.counterValue(), expected,
                "Counter should equal the number of taps");
    }
}
