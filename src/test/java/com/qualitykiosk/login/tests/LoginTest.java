package com.qualitykiosk.login.tests;

import com.qualitykiosk.login.base.BaseTest;
import com.qualitykiosk.login.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Automated login scenarios covering positive, negative, boundary and
 * input-validation cases for the demo login page.
 */
public class LoginTest extends BaseTest {

  @Test(priority = 1, description = "Valid credentials should log the user in")
  public void validLoginShouldOpenDashboard() {
    loginPage.login(validUsername(), validPassword());

    DashboardPage dashboard = new DashboardPage(driver);
    Assert.assertTrue(dashboard.isDisplayed(), "Dashboard should be visible after valid login");
    Assert.assertTrue(
        dashboard.getWelcomeText().contains(validUsername()),
        "Welcome message should contain the username");
  }

  @Test(priority = 2, description = "A valid username with a wrong password should be rejected")
  public void invalidPasswordShouldShowError() {
    loginPage.login(validUsername(), "WrongPass123");

    Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be shown");
    Assert.assertEquals(loginPage.getMessageText(), "Invalid password.");
    Assert.assertFalse(new DashboardPage(driver).isDisplayed(), "Dashboard must not open");
  }

  @Test(priority = 3, description = "An unknown username should be rejected")
  public void invalidUsernameShouldShowError() {
    loginPage.login("unknownuser", validPassword());

    Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be shown");
    Assert.assertEquals(loginPage.getMessageText(), "Invalid username.");
  }

  @Test(priority = 4, description = "Empty username and password should show a validation error")
  public void emptyCredentialsShouldShowError() {
    loginPage.clickLogin();

    Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be shown");
    Assert.assertEquals(loginPage.getMessageText(), "Username and password are required.");
  }

  @Test(priority = 5, description = "Only a username filled in should ask for the password")
  public void emptyPasswordShouldShowError() {
    loginPage.login(validUsername(), "");

    Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be shown");
    Assert.assertEquals(loginPage.getMessageText(), "Password is required.");
  }

  @Test(priority = 6, description = "A password shorter than the minimum length should be rejected")
  public void shortPasswordShouldShowBoundaryError() {
    loginPage.login(validUsername(), "ab");

    Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be shown");
    Assert.assertEquals(loginPage.getMessageText(), "Password must be at least 6 characters.");
  }

  @Test(priority = 7, description = "A username shorter than the minimum length should be rejected")
  public void shortUsernameShouldShowBoundaryError() {
    loginPage.login("ab", validPassword());

    Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be shown");
    Assert.assertEquals(loginPage.getMessageText(), "Username must be at least 3 characters.");
  }
}
