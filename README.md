# Selenium Test Automation Framework


## Overview

This is a Selenium test automation framework built with Java, implementing the Page Object Model (POM) design pattern. The framework provides a robust structure for web application testing with comprehensive reporting and logging capabilities.


## Framework Features


### Core Components

- **Page Object Model (POM)**: Clean separation of test logic and page elements

- **TestNG Integration**: Powerful test execution and management

- **Extent Reports**: Beautiful HTML test reports with screenshots

- **WebDriverManager**: Automatic driver management

- **Log4j2**: Comprehensive logging framework

- **Maven**: Dependency management and build automation

- **Excel Integration**: Data-driven testing support

- **Screenshot Capture**: Automatic screenshots on test failures

- **Multi-browser Support**: Chrome, Firefox, and Edge

- **Parallel Execution**: Support for parallel test execution



### Project Structure

```

src/

├── main/

│   ├── java/

│   │   └── com.automation/

│   │       ├── config/           # Configuration management

│   │       ├── driver/           # WebDriver factory and management

│   │       ├── pages/            # Page Object Model classes

│   │       ├── reporting/        # Extent Reports configuration

│   │       └── utils/            # Utility classes

│   └── resources/

│       ├── config.properties     # Application configuration

│       └── log4j2.xml           # Logging configuration

└── test/

    ├── java/

    │   └── com.automation.tests/ # Test classes

    └── resources/

        ├── testng.xml           # Main TestNG suite

        ├── smoke-suite.xml      # Smoke test suite

        └── regression-suite.xml # Regression test suite

```



## Getting Started



### Prerequisites

- Java 11 or higher

- Maven 3.6 or higher

- Chrome/Firefox/Edge browser installed



### Installation

1. Clone or download this project

2. Open command prompt/terminal in project root directory

3. Run: `mvn clean install`



### Configuration

Update `src/main/resources/config.properties` with your application details:

```properties

app.url=https://your-application-url.com

browser=chrome

headless=false

environment=qa

```



## Running Tests



### Command Line Execution



#### Run all tests:

```bash

mvn clean test

```



#### Run smoke tests only:

```bash

mvn clean test -Psmoke

```



#### Run regression tests only:

```bash

mvn clean test -Pregression

```



#### Run with specific browser:

```bash

mvn clean test -Dbrowser=firefox

```



#### Run in headless mode:

```bash

mvn clean test -Dheadless=true

```



### IDE Execution

- Right-click on any TestNG XML file in `src/test/resources/` and run

- Right-click on any test class and run

- Right-click on any test method and run
## Test Reports



### Extent Reports

- Location: `test-output/extent-reports/`

- Features: Test execution summary, step-by-step details, screenshots on failures

- Automatically generated after test execution



### Screenshots

- Location: `test-output/screenshots/`

- Automatically captured on test failures

- Linked to Extent Reports



### Logs

- Location: `logs/automation.log`

- Comprehensive logging of test execution

- Configurable log levels



## Writing Tests



### Creating a Page Object

```java

public class YourPage extends BasePage {

    @FindBy(id = "elementId")

    private WebElement element;

    

    public YourPage(WebDriver driver) {

        super(driver);

    }

    

    public void performAction() {

        click(element);

    }

    

    @Override

    public boolean isPageLoaded() {

        return isElementDisplayed(element);

    }

}

```



### Creating a Test Class

```java

public class YourTest extends BaseTest {

    @Test(description = "Test description")

    public void testMethod() {

        YourPage page = new YourPage(driver);

        Assert.assertTrue(page.isPageLoaded());

        page.performAction();

    }

}

```



## Data-Driven Testing

The framework supports Excel-based data-driven testing:

```java

@Test(dataProvider = "excelData")

public void dataTrivenTest(Map<String, String> testData) {

    String username = testData.get("username");

    String password = testData.get("password");

    // Use test data

}



@DataProvider

public Object[][] excelData() {

    return ExcelUtils.readExcelData("testdata.xlsx", "Sheet1")

        .toArray(new Object[0][]);

}

```



## Best Practices

1. **Page Objects**: Create separate page classes for each page/component

2. **Wait Strategies**: Use explicit waits instead of Thread.sleep()

3. **Test Independence**: Each test should be independent and reusable

4. **Meaningful Names**: Use descriptive names for tests and methods

5. **Comments**: Add JavaDoc comments for public methods

6. **Error Handling**: Implement proper exception handling

7. **Clean Code**: Follow SOLID principles and clean code