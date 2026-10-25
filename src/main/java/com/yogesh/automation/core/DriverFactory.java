package com.yogesh.automation.core;

import com.yogesh.automation.config.ConfigReader;
import com.yogesh.automation.config.Platform;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Builds a platform-specific Appium driver using the Flutter automation name
 * (Appium Flutter Driver / Flutter Integration Driver).
 *
 * <p>Capabilities are assembled from {@link ConfigReader} so the same code path
 * serves Android and iOS; the only difference is the capability set selected by
 * {@code platform}.
 */
public final class DriverFactory {

    private DriverFactory() {
    }

    public static RemoteWebDriver create() {
        Platform platform = ConfigReader.platform();
        URL server = serverUrl();
        return switch (platform) {
            case ANDROID -> new AndroidDriver(server, androidCaps());
            case IOS -> new IOSDriver(server, iosCaps());
        };
    }

    private static URL serverUrl() {
        try {
            return URI.create(ConfigReader.get("appium.server.url")).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid appium.server.url", e);
        }
    }

    private static DesiredCapabilities androidCaps() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:automationName", ConfigReader.get("android.automationName"));
        caps.setCapability("appium:deviceName", ConfigReader.get("android.deviceName"));
        caps.setCapability("appium:platformVersion", ConfigReader.get("android.platformVersion"));
        String app = ConfigReader.get("android.app", "");
        if (!app.isBlank()) {
            caps.setCapability("appium:app", app);
        } else {
            caps.setCapability("appium:appPackage", ConfigReader.get("android.appPackage"));
            caps.setCapability("appium:appActivity", ConfigReader.get("android.appActivity"));
        }
        return caps;
    }

    private static DesiredCapabilities iosCaps() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("appium:automationName", ConfigReader.get("ios.automationName"));
        caps.setCapability("appium:deviceName", ConfigReader.get("ios.deviceName"));
        caps.setCapability("appium:platformVersion", ConfigReader.get("ios.platformVersion"));
        String app = ConfigReader.get("ios.app", "");
        if (!app.isBlank()) {
            caps.setCapability("appium:app", app);
        } else {
            caps.setCapability("appium:bundleId", ConfigReader.get("ios.bundleId"));
        }
        return caps;
    }
}
