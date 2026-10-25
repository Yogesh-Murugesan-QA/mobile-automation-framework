package com.yogesh.automation.tests;

import com.yogesh.automation.config.ConfigReader;
import com.yogesh.automation.core.DriverFactory;
import com.yogesh.automation.core.DriverManager;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Lifecycle base for every test: starts a driver before each test method and quits it after,
 * attaching a screenshot to the Allure report on failure. One driver per thread (see
 * {@link DriverManager}) so suites can run in parallel.
 */
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.setDriver(DriverFactory.create());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot();
        }
        DriverManager.quitDriver();
    }

    private void attachScreenshot() {
        try {
            RemoteWebDriver driver = DriverManager.getDriver();
            byte[] png = driver.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failure screenshot", new ByteArrayInputStream(png));
        } catch (Exception ignored) {
            // A screenshot is best-effort; never mask the real test failure.
        }
    }

    protected RemoteWebDriver driver() {
        return DriverManager.getDriver();
    }
}
