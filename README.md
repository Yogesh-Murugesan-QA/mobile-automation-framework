<h1 align="center">Mobile Automation Framework</h1>

<p align="center">
  Cross-platform (Android + iOS) UI automation for <b>Flutter</b> apps using
  <b>Appium Flutter Driver</b>, <b>TestNG</b> and the <b>Page Object Model</b>.
</p>

<p align="center">
  <a href="../../actions/workflows/ci.yml"><img src="../../actions/workflows/ci.yml/badge.svg" alt="CI" /></a>
  <img src="https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Appium-9.x-662D91?logo=appium&logoColor=white" alt="Appium" />
  <img src="https://img.shields.io/badge/TestNG-7.x-FF6F00" alt="TestNG" />
  <img src="https://img.shields.io/badge/Allure-report-00C4B4" alt="Allure" />
</p>

---

## Why this framework

A compact, production-shaped reference for automating **Flutter** apps on both platforms from
a single codebase. It demonstrates the patterns I use day to day: a thread-local driver for
parallel runs, config-over-code so the same build targets Android or iOS, data-driven tests,
and Allure reporting with failure screenshots.

> The sample tests run against a public **Flutter demo app** (the Appium Flutter integration
> sample). No employer code or proprietary application is included.

## Tech stack

| Layer | Choice |
|-------|--------|
| Language | Java 17 |
| Mobile automation | Appium `java-client` 9.x, **Flutter** automation name |
| Test runner | TestNG 7.x |
| Reporting | Allure |
| Build | Maven |
| Data | JSON via Jackson |
| CI | GitHub Actions (compile/validate) + Jenkinsfile (device run) |

## Project structure

```
src/
  main/java/com/yogesh/automation/
    config/   ConfigReader, Platform          # config.properties + -D overrides
    core/     DriverFactory, DriverManager     # builds + holds the driver (thread-local)
    pages/    BasePage, CounterPage            # Page Object Model
    utils/    TestDataReader                    # JSON -> DataProvider rows
  test/java/com/yogesh/automation/tests/
    BaseTest, CounterTest                       # lifecycle + sample scenarios
  test/resources/
    config.properties                           # defaults (override with -D)
    testng.xml, testng-smoke.xml                # suites
    testdata/counter.json                       # data-driven inputs
docs/architecture.md                            # diagram + design notes
Jenkinsfile                                     # device/emulator pipeline
```

See [`docs/architecture.md`](docs/architecture.md) for the architecture diagram and the
rationale behind each design choice.

## Prerequisites

- JDK 17 and Maven 3.9+
- Node.js, then Appium 2: `npm i -g appium`
- Flutter driver plugin: `appium driver install --source=npm appium-flutter-driver`
- A running Android emulator or iOS simulator, and the sample Flutter app installed
- Appium server running: `appium`

## Running

```bash
# Android (defaults from config.properties)
mvn clean test

# iOS
mvn clean test -Dplatform=ios

# Smoke subset only
mvn clean test -Dsuite=testng-smoke.xml

# Point at a remote Appium server
mvn clean test -Dappium.server.url=http://127.0.0.1:4723
```

Every `config.properties` key can be overridden with `-D`, so CI and local runs share one build.

## Allure report

```bash
mvn test
mvn allure:serve      # opens the interactive HTML report
```

Failures attach a screenshot automatically (see `BaseTest`).

## What this demonstrates

- **Page Object Model** — tests read as behaviour; locators live in page classes.
- **Cross-platform from one codebase** — accessibility-id locators resolve on Android and iOS;
  the platform is a config value, not a code branch.
- **Parallel-ready** — `DriverManager` is thread-local, so TestNG can fan out across threads.
- **Data-driven** — add rows to `testdata/counter.json` to grow coverage without touching code.
- **CI wired** — GitHub Actions compiles and validates every push/PR; the Jenkinsfile runs the
  suite against a real device/emulator.

## License

MIT — sample/portfolio project.
