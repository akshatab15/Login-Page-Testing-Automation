package com.qualitykiosk.login.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/** Saves browser screenshots to {@code target/screenshots} (used on failures). */
public final class ScreenshotUtil {

  private static final DateTimeFormatter STAMP =
      DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

  private ScreenshotUtil() {
  }

  public static String capture(WebDriver driver, String testName) {
    if (driver == null) {
      return null;
    }
    try {
      File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      Path dir = Paths.get(System.getProperty("user.dir"), "target", "screenshots");
      Files.createDirectories(dir);
      String safeName = testName.replaceAll("[^a-zA-Z0-9-_]", "_");
      Path destination = dir.resolve(safeName + "-" + LocalDateTime.now().format(STAMP) + ".png");
      Files.copy(source.toPath(), destination);
      return destination.toAbsolutePath().toString();
    } catch (IOException | RuntimeException e) {
      System.out.println("Could not capture screenshot for " + testName + ": " + e.getMessage());
      return null;
    }
  }
}
