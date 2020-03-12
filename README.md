Before test execution you should add a personal Token to

`src/main/resources/application.properties`

You can create a Personal Token here:
https://github.com/settings/tokens

To execute tests run this command from terminal:

`./gradlew clean test allureReport`

or

`./gradlew clean test`

and then

`./gradlew allureReport`

Allure Test report is here:

`build/reports/allure-report/index.html`

A simple test report is here:

`build/reports/tests/test/index.html`
