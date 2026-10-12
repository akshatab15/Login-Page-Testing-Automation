package com.qualitykiosk.login.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Reads test configuration from {@code config.properties} on the test classpath.
 * Also resolves the local demo application URL when it is not set explicitly.
 */
public final class ConfigReader {

  private static final Properties PROPERTIES = new Properties();

  static {
    try (InputStream input =
        ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
        throw new IllegalStateException("config.properties not found on the classpath");
      }
      PROPERTIES.load(input);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load config.properties", e);
    }
  }

  private ConfigReader() {
  }

  public static String get(String key, String defaultValue) {
    String override = System.getProperty(key);
    if (override != null && !override.trim().isEmpty()) {
      return override.trim();
    }
    String value = PROPERTIES.getProperty(key);
    return (value == null || value.trim().isEmpty()) ? defaultValue : value.trim();
  }

  public static String getBrowser() {
    return get("browser", "chrome");
  }

  public static boolean isHeadless() {
    return Boolean.parseBoolean(get("headless", "false"));
  }

  public static String getValidUsername() {
    return get("valid.username", "admin");
  }

  public static String getValidPassword() {
    return get("valid.password", "Admin@123");
  }

  /**
   * Returns the application URL. If {@code app.url} is provided it is used as-is,
   * otherwise the local {@code app/index.html} file (relative to the project root)
   * is converted to a {@code file://} URL.
   */
  public static String getAppUrl() {
    String configured = get("app.url", "");
    if (!configured.isEmpty()) {
      return configured;
    }

    Path appIndex = Paths.get(System.getProperty("user.dir"), "app", "index.html");
    if (!Files.exists(appIndex)) {
      throw new IllegalStateException(
          "Demo application not found at " + appIndex.toAbsolutePath()
              + ". Set 'app.url' in config.properties or run from the project root.");
    }
    return appIndex.toUri().toString();
  }
}
