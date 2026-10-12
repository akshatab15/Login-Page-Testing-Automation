# Test Execution Report — Login Page

**Project:** Login Page Manual Testing and Automation Framework
**Manual execution:** Performed by the student tester on the local demo page (`app/index.html`)
**Automation execution:** Windows 10, Google Chrome 154 (headless), Java 17, Maven 3.9.9
**Automation stack:** Selenium WebDriver 4.33.0, TestNG 7.11.0

---

## 1. Overall Summary

| Category | Planned | Executed | Passed | Failed | Skipped | Not Executed |
|----------|---------|----------|--------|--------|---------|--------------|
| Manual test cases | 15 | 15 | 15 | 0 | 0 | 0 |
| Automated test methods | 8 | 8 | 8 | 0 | 0 | 0 |

- **Manual:** all 15 cases confirmed **PASS** (including `TC_LOGIN_10`, executed last).
- **Automated:** `Tests run: 8, Failures: 0, Errors: 0, Skipped: 0` — **BUILD SUCCESS**.
- **No defects were observed** in either pass.
- **No backend/security testing is claimed** — this is a client-side-only demo with no server.

---

## 2. Manual Test Results (executed by the tester)

| ID | Scenario | Data | Actual Result | Status |
|----|----------|------|---------------|--------|
| TC_LOGIN_01 | Valid login | admin / Admin@123 | Welcome, admin! displayed | PASS |
| TC_LOGIN_02 | Wrong password | admin / WrongPass123 | Invalid password. | PASS |
| TC_LOGIN_03 | Unknown username | unknownuser / Admin@123 | Invalid username. | PASS |
| TC_LOGIN_04 | Both fields blank | (empty) / (empty) | Username and password are required. | PASS |
| TC_LOGIN_05 | Username blank | (empty) / Admin@123 | Username is required. | PASS |
| TC_LOGIN_06 | Password blank | admin / (empty) | Password is required. | PASS |
| TC_LOGIN_07 | Username 2 chars (boundary) | ab / Admin@123 | Username must be at least 3 characters. | PASS |
| TC_LOGIN_08 | Username 3 chars (boundary) | xyz / Admin@123 | Invalid username. | PASS |
| TC_LOGIN_09 | Password 5 chars (boundary) | admin / abcd1 | Password must be at least 6 characters. | PASS |
| TC_LOGIN_10 | Password 6 chars (boundary) | admin / abc123 | Invalid password. | PASS |
| TC_LOGIN_11 | Injection-style username | `' OR '1'='1` / Admin@123 | Invalid username.; login did not succeed | PASS |
| TC_LOGIN_12 | Username with spaces | " admin " / Admin@123 | Login succeeded; Welcome, admin! displayed | PASS |
| TC_LOGIN_13 | Password masking | Admin@123 | Dots displayed instead of characters | PASS |
| TC_LOGIN_14 | Logout | (none) | Login Page displayed after logout | PASS |
| TC_LOGIN_15 | Submit with Enter key | admin / Admin@123 | Form submitted; login succeeded | PASS |

**Manual totals: 15 PASS / 0 FAIL / 0 Not Executed.**

> Note: TC_LOGIN_11 is a safe-handling check on the client side. Because there is no backend
> or database, it is **not** a real SQL-injection or security test. TC_LOGIN_13 checks the
> `type=password` masking only. No server-side security is claimed.

---

## 3. Automated Test Results (executed with `mvn clean test -Dheadless=true`)

| # | Test method | Class | Description | Status |
|---|-------------|-------|-------------|--------|
| 1 | validLoginShouldOpenDashboard | LoginTest | Valid credentials should log the user in | PASS |
| 2 | invalidPasswordShouldShowError | LoginTest | Valid username + wrong password is rejected | PASS |
| 3 | invalidUsernameShouldShowError | LoginTest | Unknown username is rejected | PASS |
| 4 | emptyCredentialsShouldShowError | LoginTest | Empty username and password is rejected | PASS |
| 5 | emptyPasswordShouldShowError | LoginTest | Empty password is rejected | PASS |
| 6 | shortPasswordShouldShowBoundaryError | LoginTest | Password shorter than 6 characters is rejected | PASS |
| 7 | shortUsernameShouldShowBoundaryError | LoginTest | Username shorter than 3 characters is rejected | PASS |
| 8 | successfulLogoutShouldReturnToLoginPage | LogoutTest | Logout returns the user to the login form | PASS |

**Automation totals: `Tests run: 8, Failures: 0, Errors: 0, Skipped: 0` (BUILD SUCCESS, ~29 s).**

Working tests were **not** modified to force a passing report.

### Assertions used
`assertTrue`, `assertFalse`, and `assertEquals` verify visible elements, exact error
messages, and that the dashboard does not open on invalid input.

### Screenshots on failure
`TestListener` captures a screenshot to `target/screenshots/` whenever a test fails and
prints the path. No failure screenshots were produced in the final run because all tests passed.

### Environment notes observed during the run
- Selenium Manager automatically provisioned the matching ChromeDriver.
- `Unable to find CDP implementation matching 154` — Chrome 154 is newer than Selenium's
  bundled DevTools protocol; harmless, does not affect results.
- `No SLF4J providers were found` — Selenium logging notice; harmless.

---

## 4. Defects

No defects were observed in the manual or automated passes, so none are logged. The sample
row in `BUG_REPORT_TEMPLATE.csv` is clearly labelled as an example and is **not** a real defect.

---

## 5. Reports and Artifacts

| Artifact | Location |
|----------|----------|
| TestNG XML result | `target/surefire-reports/testng-results.xml` |
| Surefire text summary | `target/surefire-reports/TestSuite.txt` |
| TestNG HTML report | `target/surefire-reports/index.html` |
| Emailable HTML report | `target/surefire-reports/emailable-report.html` |
| Failure screenshots | `target/screenshots/` |
| Manual test cases | `MANUAL_TEST_CASES.csv` |
