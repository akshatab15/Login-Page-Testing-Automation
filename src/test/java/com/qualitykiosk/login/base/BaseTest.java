package com.qualitykiosk.login.base;

import com.qualitykiosk.login.config.ConfigReader;
import com.qualitykiosk.login.pages.LoginPage;
import com.qualitykiosk.login.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Common setup and teardown for all UI tests.
 * Each test method starts a clean browser and closes it afterwards.
 */
public class BaseTest {

  protected WebDriver driver;
  protected LoginPage loginPage;

  @BeforeMethod(alwaysRun = true)
  public void setUp() {
    DriverFactory.initDriver();
    driver = DriverFactory.getDriver();
    loginPage = new LoginPage(driver).open(ConfigReader.getAppUrl());
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    DriverFactory.quitDriver();
  }

  protected String validUsername() {
    return ConfigReader.getValidUsername();
  }

  protected String validPassword() {
    return ConfigReader.getValidPassword();
  }
}
