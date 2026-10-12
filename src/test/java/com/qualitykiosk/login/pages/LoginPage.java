package com.qualitykiosk.login.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object for the demo login form.
 * Keeps all locators and user actions for the login screen in one place.
 */
public class LoginPage {

  private final WebDriver driver;
  private final WebDriverWait wait;

  private final By usernameField = By.id("username");
  private final By passwordField = By.id("password");
  private final By loginButton = By.id("login-btn");
  private final By message = By.id("form-message");
  private final By loginView = By.id("login-view");

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  public LoginPage open(String url) {
    driver.get(url);
    wait.until(ExpectedConditions.visibilityOfElementLocated(loginView));
    return this;
  }

  public void enterUsername(String username) {
    WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
    field.clear();
    field.sendKeys(username);
  }

  public void enterPassword(String password) {
    WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
    field.clear();
    field.sendKeys(password);
  }

  public void clickLogin() {
    wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
  }

  /** Convenience method: fills the form and clicks Login. */
  public void login(String username, String password) {
    if (!username.isEmpty()) {
      enterUsername(username);
    }
    if (!password.isEmpty()) {
      enterPassword(password);
    }
    clickLogin();
  }

  public String getMessageText() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(message)).getText().trim();
  }

  public boolean isMessageDisplayed() {
    return !getMessageText().isEmpty();
  }

  public boolean isErrorDisplayed() {
    WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(message));
    return box.getAttribute("class").contains("error");
  }

  public boolean isLoginViewVisible() {
    try {
      wait.until(ExpectedConditions.visibilityOfElementLocated(loginView));
      return true;
    } catch (RuntimeException e) {
      return false;
    }
  }
}
