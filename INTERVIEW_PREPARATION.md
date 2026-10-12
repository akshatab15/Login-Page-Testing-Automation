# Interview Preparation — Login Page Testing Project

Everything here is based **only** on what this project actually contains. Do not claim
professional QA experience or skills you have not demonstrated. Present it honestly as a
self-built, learning/portfolio project.

---

## 1. 60-Second Project Introduction

> "I built a small end-to-end QA project around a login page. First I created a safe local
> demo login page in HTML, CSS and JavaScript with two dummy users and validation for empty
> and invalid input. Then I wrote a manual testing package: a test plan, 15 manual test
> cases in CSV, a bug report template and a requirements traceability matrix. After that I
> automated the key scenarios — valid login, wrong password, wrong username, empty fields,
> length boundaries and logout — using Java 17, Selenium WebDriver 4, TestNG and Maven,
> following a simple Page Object Model. I configured TestNG reports and screenshots on
> failure. I executed the manual cases on the local page: all 15 passed. In the automated
> run all 8 tests passed.
> It shows the full flow from requirements to test cases, execution and reporting."

## 2. Project Workflow and Architecture

**Workflow**

1. Understand the login feature → define requirements (REQ-01 … REQ-10).
2. Write the **test plan** (scope, approach, environment, risks).
3. Design **15 manual test cases** using equivalence partitioning and boundary value analysis.
4. Build the **demo app** so there is something real to test.
5. **Automate** the highest-value scenarios with Selenium + TestNG.
6. **Execute** with Maven, capture results and screenshots.
7. **Report** in `TEST_EXECUTION_REPORT.md` and map tests to requirements in the RTM.

**Architecture (automation) — a light Page Object Model**

```
Test classes        (LoginTest, LogoutTest)      -> define WHAT to test
        │
BaseTest            (setup / teardown)            -> opens browser + app URL
        │
Page Objects        (LoginPage, DashboardPage)    -> locators + actions (HOW)
        │
DriverFactory       -> creates the WebDriver
ConfigReader        -> reads config.properties (browser, headless, URL, users)
ScreenshotUtil + TestListener -> screenshots on failure
```

The demo app is static and served from the local filesystem (`file://`), so no server is
required.

## 3. Manual Testing Fundamentals

- **Manual testing** is testing performed by a human without automation tools. The tester
  follows steps and compares the actual result with the expected result.
- **Why it still matters:** useful for exploratory testing, usability, and one-off cases
  where automation costs more than it saves.
- **Verification vs. validation:** verification checks we built it right (against specs);
  validation checks we built the right thing (against user needs).
- **Test case:** a documented set of steps, data and expected result for one check.
- **Defect/bug:** a mismatch between the expected and actual result.
- In this project, manual assets live in `MANUAL_TEST_CASES.csv`, `BUG_REPORT_TEMPLATE.csv`,
  `TEST_PLAN.md` and `TEST_EXECUTION_REPORT.md`.

## 4. Test Scenarios vs. Test Cases

| | Test Scenario | Test Case |
|--|----------------|-----------|
| What | A high-level feature to test | Detailed steps for one specific check |
| Count | Fewer | More (many per scenario) |
| Example | "Validate login functionality" | "Login with valid username and wrong password shows 'Invalid password.'" |

In this project "login validation" is a **scenario**; `TC_LOGIN_02` is a **test case**.

## 5. Expected vs. Actual Results

- **Expected result:** what *should* happen if the software is correct (from requirements).
- **Actual result:** what *actually* happened when executed.
- If they differ → it's a potential **defect**. Example from this project: expected
  "Invalid password." but actual "Invalid username." would be a bug.
- Honesty rule used here: manual cases keep an **empty actual result** until they are really run.

## 6. Positive and Negative Testing

- **Positive testing:** valid data expected to succeed. Example: `admin / Admin@123`
  opens the dashboard (`TC_LOGIN_01`).
- **Negative testing:** invalid data expected to be handled gracefully. Example: empty
  fields show "Username and password are required." (`TC_LOGIN_04`).
- **Boundary value analysis** looks at the edges: username min length is 3, so 2 chars
  fail and 3 chars pass length check (`TC_LOGIN_07`, `TC_LOGIN_08`).

## 7. Defect Life Cycle

```
New → Assigned → Open (In Progress) → Fixed → Retest → Verified → Closed
                              │
                              └→ Reopened (if still failing)
                              └→ Deferred / Rejected (if not a bug / postponed)
```

- **New:** a tester logs it.
- **Assigned/Open:** a developer picks it up.
- **Fixed:** the developer provides a fix.
- **Retest/Verified:** the tester re-runs and confirms.
- **Closed:** confirmed resolved. **Reopened** if it fails again.

## 8. Severity vs. Priority

| | Severity | Priority |
|--|----------|----------|
| Meaning | How bad the impact is on the system | How soon it should be fixed |
| Set by | QA / tester | Product owner / manager |
| Example | Login broken for all users = High severity | Cosmetic typo on error text = Low severity, but could be High priority if customers see it |

They are related but independent: high severity can be low priority and vice versa.

## 9. Core Java Concepts Actually Used in This Project

- **Classes and objects** — `LoginPage`, `DashboardPage`, `DriverFactory`.
- **Methods and parameters** — `login(username, password)`, `enterPassword(String)`.
- **Strings** — `.isEmpty()`, `.trim()`, `.contains()`, `.toLowerCase()`.
- **Inheritance** — `LoginTest extends BaseTest` to reuse setup/teardown.
- **Interfaces** — `TestListener implements ITestListener`; Selenium's `WebDriver`,
  `TakesScreenshot` are interfaces.
- **Collections** — `Properties` (key/value config), `ThreadLocal<WebDriver>`.
- **Encapsulation** — private `By` locators exposed through public methods.
- **Enums/constants** — `ExpectedConditions`, `OutputType` from Selenium.
- **Exception handling** — `try/catch` in `isDisplayed()` and screenshot capture.
- **Static members** — `ConfigReader.get(...)`, `DriverFactory.getDriver()`.
- **switch** — choosing the browser in `DriverFactory`.

## 10. Selenium WebDriver Basics (as used here)

- `WebDriver` is the interface to the browser; `ChromeDriver`/`EdgeDriver` implement it.
- `driver.get(url)` opens the application.
- **Locators:** this project uses `By.id(...)` because ids are stable and readable.
- `sendKeys`, `clear`, `click`, `getText`, `getAttribute`, `isDisplayed` are the web-element actions used.
- **Waits:** explicit `WebDriverWait` with `ExpectedConditions` (e.g. `visibilityOfElementLocated`,
  `elementToBeClickable`) wait only as long as needed — more reliable than fixed sleeps.
- **Implicit wait** is set to 0 in favour of explicit waits to avoid unpredictable timing.
- `TakesScreenshot` + `OutputType.FILE` saves a PNG on failure.
- **Selenium Manager** (built into Selenium 4.6+) downloads the matching browser driver automatically.
- `driver.quit()` in `@AfterMethod` closes the browser and frees resources.

## 11. TestNG Annotations and Assertions (as used here)

| Annotation | Where | Purpose |
|------------|-------|---------|
| `@Test` | test methods | Marks a method as a test; supports `priority` and `description` |
| `@BeforeMethod` | `BaseTest` | Runs before every test — starts the browser |
| `@AfterMethod` | `BaseTest` | Runs after every test — quits the browser |
| `listeners` (in testng.xml) | `TestListener` | Reacts to test results (screenshot on failure) |

**Assertions:** `Assert.assertTrue`, `Assert.assertFalse`, `Assert.assertEquals` compare
expected vs actual; a failed assertion fails the test with a readable message.

## 12. Twenty Interview Questions with Short Answers

1. **What is this project about?**
   A QA project with a demo login page, 15 manual test cases, and an 8-test Selenium +
   TestNG Maven suite following a light Page Object Model.

2. **What is the difference between manual and automation testing?**
   Manual is executed by a human; automation uses tools/scripts to run repeatable checks
   faster and more consistently.

3. **What is the Page Object Model?**
   A design pattern where each page has a class holding its locators and actions, so tests
   stay readable and maintenance is easier. Used in `LoginPage` and `DashboardPage`.

4. **Which locators did you use and why?**
   `By.id` — ids are unique, stable and the fastest locators.

5. **Why explicit waits instead of `Thread.sleep`?**
   Explicit waits poll until a condition is true (up to a timeout). `Thread.sleep` always
   waits the full time and makes tests slow and flaky.

6. **What is the difference between implicit and explicit wait?**
   Implicit is a global default for finding elements; explicit targets a specific condition.
   This project relies on explicit waits.

7. **How would you handle a dropdown, alert or new browser window?**
   `Select` for dropdowns, `switchTo().alert()` for alerts, and `switchTo().window(...)` for
   windows. (Not needed in this login page.)

8. **What is TestNG and why use it over JUnit here?**
   TestNG is a testing framework with annotations, priorities, data providers, listeners
   and flexible suite XML. I used it for `@BeforeMethod/@AfterMethod`, `@Test priority`, and a listener.

9. **What annotations did you use?**
   `@Test`, `@BeforeMethod`, `@AfterMethod`, plus an `ITestListener` in `testng.xml`.

10. **How do you capture screenshots on failure?**
    A TestNG `ITestListener.onTestFailure` calls `ScreenshotUtil.capture(...)`, which uses
    `TakesScreenshot` to save a PNG to `target/screenshots/`.

11. **What is a test case vs. a test scenario?**
    A scenario is a high-level thing to test; a test case is the detailed steps, data and
    expected result for one specific check.

12. **What is positive vs. negative testing?**
    Positive uses valid data to confirm success; negative uses invalid data to confirm
    graceful failure. Both are in `MANUAL_TEST_CASES.csv`.

13. **What is boundary value analysis? Give an example.**
    Testing values at the edges of valid ranges. Username min length is 3, so I tested 2
    (fail) and 3 (pass). `TC_LOGIN_07/08`.

14. **What is the defect life cycle?**
    New → Assigned → Open → Fixed → Retest → Verified → Closed (or Reopened/Deferred).

15. **Difference between severity and priority?**
    Severity is impact; priority is fix order. They are set by different people and are
    independent.

16. **What is a requirements traceability matrix?**
    A table mapping each requirement to the tests covering it. I keep one in
    `REQUIREMENTS_TRACEABILITY_MATRIX.csv`.

17. **How do you decide what to automate?**
    Automate high-value, repeatable, stable, regression-critical flows — here: valid login,
    invalid login, empty fields, boundaries and logout.

18. **How did Maven help?**
    Maven manages dependencies, compiles the code and runs TestNG tests through the
    Surefire plugin with `mvn test`.

19. **What is a known limitation of this project?**
    Validation is client-side only, so it is not secure authentication; the app also
    reveals whether the username exists (user enumeration).

20. **How do you know your tests passed?**
    The Surefire/TestNG console output reported `Tests run: 8, Failures: 0, Errors: 0,
    Skipped: 0` and `BUILD SUCCESS`, and results are in `target/surefire-reports/`.

21. **What does `driver.quit()` do and why is it important?**
    It closes all browser windows and ends the session, preventing leftover processes and
    memory leaks. It runs in `@AfterMethod`.

22. **How would you run tests without opening a browser window?**
    `mvn test -Dheadless=true`, which adds the headless Chrome/Edge option in `DriverFactory`.

## 13. Marathi Explanation — हा प्रोजेक्ट कसा काम करतो

> या प्रोजेक्टमध्ये मी एक **लॉगिन पेज** तयार केले आहे — HTML, CSS आणि JavaScript वापरून.
> यात युजरनेम आणि पासवर्ड टाकायचे असतात. दोन **डमी युजर** आहेत: `admin / Admin@123` आणि
> `tester / Test@1234`.
>
> लॉगिन करताना अनेक तपासण्या होतात:
> - दोन्ही फील्ड रिकामे असल्यास — "Username and password are required." हा मेसेज येतो.
> - फक्त युजरनेम रिकामे असल्यास — "Username is required."
> - युजरनेम 3 अक्षरांपेक्षा लहान असल्यास — त्रुटी येते.
> - पासवर्ड 6 अक्षरांपेक्षा लहान असल्यास — त्रुटी येते.
> - चुकीचे युजरनेम असेल तर — "Invalid username."
> - चुकीचा पासवर्ड असेल तर — "Invalid password."
> - बरोबर माहिती असेल तर — **डॅशबोर्ड** दिसतो आणि "Welcome, admin!" असे दिसते.
> - **Logout** बटण दाबल्यास पुन्हा लॉगिन पेजवर परत येतो.
>
> **मॅन्युअल टेस्टिंग:** मी 15 टेस्ट केसेस CSV मध्ये लिहिल्या आहेत (पॉझिटिव्ह, निगेटिव्ह,
> बाउंड्री) आणि लोकल पेजवर त्या चालवल्या. **सर्व 15 टेस्ट पास** झाल्या — खोटे निकाल लिहिलेले नाहीत.
>
> **ऑटोमेशन:** Java 17 + Selenium WebDriver + TestNG + Maven वापरून मी 8 टेस्ट स्वयंचलित केल्या.
> Page Object Model मध्ये `LoginPage` आणि `DashboardPage` हे वेगळे क्लास आहेत. `mvn test` चालवल्यावर
> सर्व 8 टेस्ट **पास** झाल्या (`Failures: 0, Errors: 0`) आणि रिपोर्ट `target/surefire-reports/`
> मध्ये तयार होतो. टेस्ट फेल झाल्यास स्क्रीनशॉट `target/screenshots/` मध्ये सेव्ह होतो.

## 14. Interview Demonstration Checklist

- [ ] Open `app/index.html` and show a real valid login and a real invalid login.
- [ ] Show an empty-field error and a boundary error (2-character username / 5-character password).
- [ ] Open `MANUAL_TEST_CASES.csv` and explain 2–3 cases and how the actual results were recorded (all 15 PASS).
- [ ] Open `REQUIREMENTS_TRACEABILITY_MATRIX.csv` and explain the requirement → test mapping.
- [ ] Walk through `LoginPage.java` and explain one locator and one action.
- [ ] Walk through `BaseTest.java` and explain `@BeforeMethod` / `@AfterMethod`.
- [ ] Run `mvn test -Dheadless=true` and show `BUILD SUCCESS` with 8 tests passing.
- [ ] Show `target/surefire-reports/` and the failure-screenshot mechanism.
- [ ] Explain one known limitation (client-side-only validation) and how you'd improve it.

## 15. Honesty Note

This is a **self-built learning project**. Do not claim commercial QA experience, real
defects raised in a company, or tools you have not used. The strengths to highlight are:
clear manual test design, a working automation framework, honest reporting, and the ability
to explain every part of your own work.
