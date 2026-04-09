# Selenium Test Automation Framework - Build and Run Instructions

# Build the project
mvn clean compile

# Run all tests
mvn clean test

# Run smoke test only
mvn clean test -Psmoke

# Run regression test only
mvn clean test -Pregression

# Run tests with specific browser
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge

# Run tests in headless mode
mvn clean test -Dheadless=true

# Run specific test class
mvn clean test -Dtest=HomePageTest

# Run specific test method
mvn clean test -Dtest=LoginPageTest#VerifySuccessfullLogin

# Package the project 
mvn clean package

# Install dependencies
mvn clean install