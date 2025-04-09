Testing framework written in Java.

It uses Maven for build and dependency management.
JUnit 5 is used for Test execution

# To run
`mvn clean test`

To generate surefire reports: `mvn surefire-report:report-only`

# Settings
- `-DtestEnv` sets the environment where tests will run, the default is https://www.saucedemo.com

-  `-Dheadless` set to *false* if you want to see the browser running.

- `-Dbrowser` used to set in which browser the tests will run

  Available options:
  1. only *firefox* for now
  2. TODO: Add more browsers

These properties are defined in the pom.xml

# Structure

This project follows the Page Object Model. All pages extend from a BasePage that contains shared actions among all pages.

All tests extend from a base test that contains common actions like launching and closing the driver.

## Pages folder

`pages` folder contains all the classes that model our web pages.

## Utils folder
`utils` folder is used to store utilities like the base page, setting cookies, taking screenshots, etc.

## Resources
- `test-data` folder contains data used by the respective tests, in CSV format.
- `screenshots` contains captures taken when a test fails.

## Reports
At the moment it uses Surefire for creating reports.
- `mvn surefire-report:report` to run tests and generate reports
- `mvn surefire-report:report-only` to only generate reports

## Logging
TODO: add
