package com.yogesh.automation.core;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Thread-local holder for the active driver, so suites can run tests in parallel
 * (one driver per thread) without cross-talk.
 */
public final class DriverManager {

    private static final ThreadLocal<RemoteWebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static RemoteWebDriver getDriver() {
        RemoteWebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialised for this thread. Did BaseTest.setUp run?");
        }
        return driver;
    }

    public static void setDriver(RemoteWebDriver driver) {
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        RemoteWebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }
}
