package com.yogesh.automation.config;

/** Supported mobile platforms. */
public enum Platform {
    ANDROID,
    IOS;

    public static Platform from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("platform is null");
        }
        return switch (value.trim().toLowerCase()) {
            case "android" -> ANDROID;
            case "ios" -> IOS;
            default -> throw new IllegalArgumentException("Unsupported platform: " + value);
        };
    }
}
