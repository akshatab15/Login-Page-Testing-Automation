package com.qualitykiosk.login.utils;

import com.qualitykiosk.login.config.ConfigReader;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

/**
 * Creates and stores WebDriver instances.
 *
 * <p>Selenium 4.6+ includes Selenium Manager, which downloads the matching
 * browser driver automatically, so no manual driver setup is required.
 */
public final class DriverFactory {

  private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

  private DriverFactory() {
  }

  public static WebDriver getDriver() {
    return DRIVER.get();
  }

  public static void initDriver() {
    String browser = ConfigReader.getBrowser().toLowerCase();
    boolean headless = ConfigReader.isHeadless();
    WebDriver driver;

    switch (browser) {
      case "edge":
        EdgeOptions edgeOptions = new EdgeOptions();
        if (headless) {
          edgeOptions.addArguments("--headless=new");
        }
        edgeOptions.addArguments("--window-size=1366,900");
        driver = new EdgeDriver(edgeOptions);
        break;

      case "chrome":
      default:
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless) {
          chromeOptions.addArguments("--headless=new");
        }
        chromeOptions.addArguments("--window-size=1366,900");
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        break;
    }

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    DRIVER.set(driver);
  }

  public static void quitDriver() {
    WebDriver driver = DRIVER.get();
    if (driver != null) {
      driver.quit();
      DRIVER.remove();
    }
  }
}
