# Course: Test-Driven Development (TDD) and Clean Code

**Dates:** July 14 to 25 (Monday to Friday)  
**Total Duration:** 25 hours (2.5 hours per session)  
**Schedule:** 08:15 to 10:45  
**Sessions:** 10 
**Format:** Theory and practice with exercises and a final mini-project

## Course Objectives

1. Master testing techniques using **JUnit 5** in **Spring Boot** applications.  
2. Design maintainable and expressive test suites for **REST APIs**.  
3. Apply **mocking** to isolate business logic and external dependencies.  
4. Integrate tests into **CI/CD pipelines** and quality tools like **SonarQube**.  
5. Validate data and behavior through test coverage, edge case validation, and transactional tests.  
6. Apply **Contract Testing** with **Spring Cloud Contract** and **Pact**.  
7. Test **Kafka** message flows and **Reactive Programming** pipelines.

## Syllabus and Session Plan

### Session 1 (July 14): Introduction to TDD and Clean Code, Basic TDD + Best Practices
- Principles of TDD (Red-Green-Refactor)
- Clean Code philosophy (Robert C. Martin)
- Environment setup: Spring Boot, JUnit 5, Maven/Gradle
- Unit tests with JUnit 5: architecture (JUnit Platform, Jupiter, Vintage), naming, assertions
- Best practices for writing clean test code
- Refactor code while keeping tests green
- *Exercise:* TDD katas (FizzBuzz, Roman Numerals)

### Session 2 (July 15): Test Architecture in Spring Boot
- Testing services and components
- Dependency injection and testability
- Arrange-Act-Assert pattern
- *Exercise:* Business service test with simulated repository

### Session 3 (July 16): REST API Testing
- Controller tests with MockMvc
- Payload and status code validation
- *Exercise:* GET, POST, PUT, DELETE tests for “Customer” resource

### Session 4 (July 17): Mocking with Mockito
- Introduction to Mockito: `@Mock`, `@InjectMocks`
- Stubbing, verify, argument matchers
- *Exercise:* Service tests with mocked dependencies

### Session 5 (July 18): Integration and Data Tests
- Using `@SpringBootTest`, `@DataJpaTest`
- Real access to PostgreSQL or embedded H2 database
- *Exercise:* Integration between service layer and database
- Validation of edge cases and invalid inputs
- Tests with transaction rollback
- *Exercise:* Test suite for validations and expected errors

### Session 6 (July 21): CI/CD Integration and SonarQube
- Automating tests with **GitHub Actions** / **Jenkins**
- Introduction to SonarQube: quality metrics and coverage
- *Exercise:* Basic pipeline with build, test, and static analysis

### Session 7 (July 22): Refactoring and Advanced Clean Code
- SOLID principles applied to testability
- Refactoring code with tests as safety net
- *Exercise:* Improve architecture while keeping tests green

### Session 8 (July 23): Final TDD Workshop
- Full development of a functionality using TDD
- Design, coverage, and quality discussion
- *Exercise:* Complete CRUD using TDD + validations + mocks

### Session 9 (July 24): Contract Testing with Spring Cloud Contract and Pact
- What is Contract Testing and Consumer-Driven Contracts
- Difference between integration tests and contract tests
- Spring Cloud Contract: stubs, verification
- Pact: consumer tests, pact files, broker concept
- *Exercise:* Build a producer REST service, write Pact consumer test, generate pact.json, verify producer, integrate with CI/CD

### Session 10 (July 25): Kafka Testing and Reactive Programming
- Introduction to **Spring WebFlux** and **Reactor**
- Testing `Flux` and `Mono` with `StepVerifier`
- Kafka flow testing with `EmbeddedKafka`
- *Exercise:* Build a `Flux` pipeline consuming Kafka messages, test with embedded Kafka and verify with `StepVerifier`

## Materials per Session

Each session will include:
- PDF presentation (theory and examples)
- Spring Boot base project for practice
- Step-by-step exercise instructions
- Suggested solutions and recommendations
- Scripts for integration with **Jenkins** / **GitHub Actions**