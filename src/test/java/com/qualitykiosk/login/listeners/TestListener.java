package com.qualitykiosk.login.listeners;

import com.qualitykiosk.login.utils.DriverFactory;
import com.qualitykiosk.login.utils.ScreenshotUtil;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener that captures a screenshot whenever a test fails
 * and prints the location to the console.
 */
public class TestListener implements ITestListener {

  @Override
  public void onTestFailure(ITestResult result) {
    String testName = result.getMethod().getMethodName();
    String path = ScreenshotUtil.capture(DriverFactory.getDriver(), testName);
    if (path != null) {
      System.out.println("[FAILURE SCREENSHOT] " + testName + " -> " + path);
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    System.out.println("[SKIPPED] " + result.getMethod().getMethodName());
  }
}
