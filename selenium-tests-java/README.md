# Selenium Java Tests (Eclipse)

This project contains Selenium UI tests for the student form web app.

## Prerequisites

- Java 17+
- Maven 3.9+
- Google Chrome installed
- Eclipse IDE for Enterprise Java and Web Developers (or Eclipse with Maven support)

## Project Structure

- `pom.xml` - dependencies and test plugins
- `src/test/java/com/example/tests/StudentFormTest.java` - Selenium JUnit test

## Import In Eclipse

1. Open Eclipse.
2. Go to **File -> Import -> Maven -> Existing Maven Projects**.
3. Browse to `selenium-tests-java` and finish import.
4. Wait for Maven dependencies to download.

## Run From Eclipse

1. Open `StudentFormTest.java`.
2. Right-click -> **Run As -> JUnit Test**.

## Run From Terminal

From `selenium-tests-java`:

```powershell
mvn test
```

## Dockerized Run

From the repository root, build the test image:

```powershell
docker build -t student-form-tests .
```

Run the tests inside the container:

```powershell
docker run --rm student-form-tests
```

The container includes Maven, Java 17, and Chromium, and it runs the Selenium test suite in headless mode.

## Jenkins

The workspace root includes a `Jenkinsfile` that runs the Selenium tests in headless mode and publishes Surefire reports.

Pipeline behavior:

```powershell
cd selenium-tests-java
mvn -B test -Dheadless=true
```

## Useful Options

Run against a hosted URL:

```powershell
mvn -Dapp.url="http://localhost:5500" test
```

Run headless mode:

```powershell
mvn -Dheadless=true test
```