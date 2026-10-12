/*
 * Demo Login Application logic.
 *
 * This is a SAFE, LOCAL demo only. Credentials are hard-coded dummy values
 * and no real account or backend is used.
 *
 * Documented dummy users:
 *   admin  / Admin@123
 *   tester / Test@1234
 */

"use strict";

var VALID_USERS = {
  admin: "Admin@123",
  tester: "Test@1234"
};

var RULES = {
  usernameMinLength: 3,
  passwordMinLength: 6
};

(function () {
  var loginForm = document.getElementById("login-form");
  var usernameInput = document.getElementById("username");
  var passwordInput = document.getElementById("password");
  var messageBox = document.getElementById("form-message");
  var loginView = document.getElementById("login-view");
  var dashboardView = document.getElementById("dashboard-view");
  var welcomeMessage = document.getElementById("welcome-message");
  var logoutBtn = document.getElementById("logout-btn");

  function showMessage(text, type) {
    messageBox.textContent = text;
    messageBox.className = "message" + (type ? " " + type : "");
  }

  function clearMessage() {
    messageBox.textContent = "";
    messageBox.className = "message";
  }

  function login(username, password) {
    clearMessage();

    // ----- Empty field validation -----
    if (username === "" && password === "") {
      showMessage("Username and password are required.", "error");
      return;
    }
    if (username === "") {
      showMessage("Username is required.", "error");
      return;
    }
    if (password === "") {
      showMessage("Password is required.", "error");
      return;
    }

    // ----- Boundary / format validation -----
    if (username.length < RULES.usernameMinLength) {
      showMessage(
        "Username must be at least " + RULES.usernameMinLength + " characters.",
        "error"
      );
      return;
    }
    if (password.length < RULES.passwordMinLength) {
      showMessage(
        "Password must be at least " + RULES.passwordMinLength + " characters.",
        "error"
      );
      return;
    }

    // ----- Credential validation (distinct messages for practice/testing) -----
    if (!Object.prototype.hasOwnProperty.call(VALID_USERS, username)) {
      showMessage("Invalid username.", "error");
      return;
    }
    if (VALID_USERS[username] !== password) {
      showMessage("Invalid password.", "error");
      return;
    }

    // ----- Success -----
    showMessage("Login successful. Redirecting...", "success");
    goToDashboard(username);
  }

  function goToDashboard(username) {
    welcomeMessage.textContent = "Welcome, " + username + "!";
    loginView.classList.add("hidden");
    dashboardView.classList.remove("hidden");
    clearMessage();
  }

  function logout() {
    dashboardView.classList.add("hidden");
    loginView.classList.remove("hidden");
    usernameInput.value = "";
    passwordInput.value = "";
    clearMessage();
    showMessage("You have been logged out.", "success");
    usernameInput.focus();
  }

  loginForm.addEventListener("submit", function (event) {
    event.preventDefault();
    login(usernameInput.value.trim(), passwordInput.value);
  });

  logoutBtn.addEventListener("click", logout);

  // Expose a tiny API so automated tests / console can check app state.
  window.demoApp = {
    isValidUser: function (username, password) {
      return (
        Object.prototype.hasOwnProperty.call(VALID_USERS, username) &&
        VALID_USERS[username] === password
      );
    },
    isDashboardVisible: function () {
      return !dashboardView.classList.contains("hidden");
    }
  };
})();
