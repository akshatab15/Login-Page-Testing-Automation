# Test Plan — Login Page Manual Testing and Automation Framework

## 1. Introduction

This document describes the test strategy for the **Demo Login Page**, a small local
web application built for a Quality Assurance portfolio project. It covers manual
testing assets and a Selenium WebDriver + TestNG automation suite.

The application is a safe, offline demo. It has no backend, no database, and no real
user accounts. All validation happens in the browser using JavaScript.

## 2. Objectives

- Verify that valid credentials log the user in and open the dashboard.
- Verify that invalid, empty, and boundary inputs are rejected with clear messages.
- Verify that the password field masks input and that logout returns to the login form.
- Demonstrate a complete, repeatable QA workflow: plan → cases → execution → defects → report.

## 3. Scope

### In scope
- The login form (username, password, Login button).
- Client-side input validation (required fields, minimum lengths).
- Credential checking against the documented dummy users.
- The authenticated dashboard view and the Logout button.
- Basic responsive layout (manual visual check).
- Automated regression tests for the above.

### Out of scope
- Real authentication, servers, databases, or third-party identity providers.
- Security penetration testing, penetration load/performance testing.
- Cross-browser matrix beyond Chrome and Edge.
- Accessibility certification.

## 4. Test Approach

| Area | Approach |
|------|----------|
| Manual testing | Exploratory and scripted tests executed by a human against `app/index.html`. |
| Automated testing | Selenium WebDriver 4 + TestNG, Page Object Model, run with Maven. |
| Test design techniques | Equivalence partitioning, boundary value analysis, positive/negative testing, error guessing. |
| Test data | Documented dummy users only (see section 7). |

## 5. Test Environment

| Item | Value |
|------|-------|
| OS | Windows 10 (64-bit) |
| Java | Temurin OpenJDK 17 |
| Build tool | Apache Maven 3.9.9 |
| Browsers | Google Chrome 154 (primary), Microsoft Edge 155 |
| Test framework | TestNG 7.11.0 |
| Automation library | Selenium WebDriver 4.33.0 (driver auto-managed by Selenium Manager) |
| Application | `app/index.html` opened as a local `file://` URL |

## 6. Test Items and Coverage

- **Manual:** 15 test cases in `MANUAL_TEST_CASES.csv` covering positive, negative,
  boundary, input-validation, client-side security and usability scenarios.
- **Automated:** 8 scenarios across `LoginTest` and `LogoutTest`.
- **Traceability:** requirement-to-test mapping in `REQUIREMENTS_TRACEABILITY_MATRIX.csv`.

> **Execution status (see `TEST_EXECUTION_REPORT.md`):** all 15 manual cases were
> confirmed PASS; all 8 automated tests pass. No backend or server-side security testing is in scope.

### Responsive layout check (manual, not in the CSV)
1. Open the page and resize the browser to 320 px, 768 px, and 1366 px widths.
2. Expected: the card stays centered, does not overflow horizontally, and text remains readable.

## 7. Test Data (Dummy Credentials)

| Username | Password | Expected outcome |
|----------|----------|------------------|
| admin | Admin@123 | Successful login |
| tester | Test@1234 | Successful login |
| (any unknown) | (any) | Invalid username |
| admin | (wrong) | Invalid password |

> These credentials are dummy values committed on purpose for the demo. They are not
> associated with any real account or system.

## 8. Entry Criteria

- The demo application files exist and open in a supported browser.
- Java 17 and Maven are installed; internet access is available for first-time dependency download.

## 9. Exit Criteria

- All planned manual cases executed and results recorded, **or** explicitly marked Not Executed.
- Automated suite compiles and all enabled tests pass, or failures are documented as defects.
- Test execution report and traceability matrix are updated.

## 10. Risks and Mitigations

| Risk | Impact | Mitigation |
|------|--------|------------|
| Selenium Manager cannot download a driver (offline) | Automation cannot run | Documented as an environment limitation; manual testing is unaffected |
| Chromium/CDP version warning on very new browsers | Console warnings only | Warnings do not affect test results in this project |
| Browser not installed | Automation blocked for that browser | Support both Chrome and Edge via `config.properties` |
| Client-side-only validation | Not a security-grade login | Clearly documented as a known limitation; out of scope for real security testing |

## 11. Deliverables

- `MANUAL_TEST_CASES.csv`, `BUG_REPORT_TEMPLATE.csv`, `TEST_PLAN.md`
- `TEST_EXECUTION_REPORT.md`, `REQUIREMENTS_TRACEABILITY_MATRIX.csv`
- Selenium/TestNG Maven project (`pom.xml`, `src/test/java/...`)
- `README.md`, `INTERVIEW_PREPARATION.md`
