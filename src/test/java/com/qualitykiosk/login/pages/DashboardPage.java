package com.qualitykiosk.login.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Page Object for the authenticated success (dashboard) view. */
public class DashboardPage {

  private final WebDriver driver;
  private final WebDriverWait wait;

  private final By dashboardView = By.id("dashboard-view");
  private final By welcomeMessage = By.id("welcome-message");
  private final By logoutButton = By.id("logout-btn");

  public DashboardPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  public boolean isDisplayed() {
    try {
      wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardView));
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }

  public String getWelcomeText() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).getText().trim();
  }

  public void clickLogout() {
    wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
  }
}
