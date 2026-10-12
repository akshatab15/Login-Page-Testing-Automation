package com.qualitykiosk.login.tests;

import com.qualitykiosk.login.base.BaseTest;
import com.qualitykiosk.login.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Automated scenarios for the logout / session-exit flow. */
public class LogoutTest extends BaseTest {

  @Test(description = "A logged-in user should be able to log out and return to the login page")
  public void successfulLogoutShouldReturnToLoginPage() {
    loginPage.login(validUsername(), validPassword());

    DashboardPage dashboard = new DashboardPage(driver);
    Assert.assertTrue(dashboard.isDisplayed(), "Dashboard should be visible before logout");

    dashboard.clickLogout();

    Assert.assertTrue(loginPage.isLoginViewVisible(), "Login form should be visible after logout");
    Assert.assertEquals(loginPage.getMessageText(), "You have been logged out.");
  }
}
