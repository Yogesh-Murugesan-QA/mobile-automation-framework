# Framework Architecture

```
                         TestNG suite (testng.xml / testng-smoke.xml)
                                        |
                                        v
      +------------------------------ Tests ------------------------------+
      |  CounterTest  extends  BaseTest                                   |
      |     - @BeforeMethod  -> DriverFactory.create() -> DriverManager   |
      |     - @AfterMethod   -> screenshot on failure -> quit            |
      +---------------------------------+---------------------------------+
                                        |  uses
                                        v
      +------------------------------ Pages ------------------------------+
      |  CounterPage extends BasePage                                     |
      |     - intent methods (tapIncrement, counterValue)                 |
      |     - accessibility-id locators (work on Android + iOS)           |
      |     - explicit waits via BasePage                                 |
      +---------------------------------+---------------------------------+
                                        |  drives
                                        v
      +------------------------------ Core -------------------------------+
      |  DriverFactory  -> builds AndroidDriver / IOSDriver (Flutter)     |
      |  DriverManager  -> ThreadLocal<RemoteWebDriver> (parallel-safe)   |
      +---------------------------------+---------------------------------+
                                        |  reads
                                        v
      +---------------------------- Config/Utils -------------------------+
      |  ConfigReader (config.properties + -D overrides)                  |
      |  TestDataReader (JSON -> DataProvider rows)                       |
      +-------------------------------------------------------------------+
                                        |
                                        v
                        Allure results  ->  Allure HTML report
```

## Design choices

- **Page Object Model** — locators and interactions live in page classes; tests read as
  behaviour, not UI plumbing.
- **Thread-local driver** — `DriverManager` holds one driver per thread, so TestNG can run
  the suite in parallel without drivers colliding.
- **Config over code** — `ConfigReader` merges `config.properties` with `-D` system
  properties, so one build runs Android locally and iOS in CI with no source changes.
- **Data-driven** — `TestDataReader` turns a JSON file into TestNG `DataProvider` rows, so
  coverage grows by editing data, not code.
- **Accessibility-id locators** — the stable, fast strategy for Flutter; the same ids resolve
  on both platforms, which is why one page object serves Android and iOS.
