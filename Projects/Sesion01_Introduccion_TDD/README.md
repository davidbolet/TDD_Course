# Introduction to TDD and Clean Code, Basic TDD + Best Practices

## Objetive

Base project for introductory session

## 1. What is TDD?

Test-Driven Development (TDD) is a development practice where you write tests before writing the actual implementation code. The standard cycle is known as Red-Green-Refactor:\
\- Red: Write a test that fails because the feature doesn't exist yet.\
\- Green: Write the minimal code required to make the test pass. \
\- Refactor: Improve the code structure while keeping the tests passing.\
TDD encourages small, incremental changes and fast feedback. It helps developers design better APIs and keep code clean and maintainable.

## 2. Why use TDD?

TDD is not just about testing; it’s about design. By writing tests first, developers focus on what the code must do, leading to clearer requirements and better architecture.\
Benefits:\
\- Reduces bugs and regression issues.\
\- Encourages small, testable units of code.\
\- Simplifies integration with CI/CD pipelines.\
Common pitfalls include writing large tests too soon or skipping the refactor phase.

## 3. Clean Code Philosophy

Based on Robert C. Martin's (Uncle Bob) principles, clean code is easy to read, simple, and expressive.\
Key points:\
\- Use meaningful names for classes, methods, and variables.\
\- Keep functions small and focused on one task.\
\- Avoid duplication.\
\- Write tests that are also clean and expressive.\
Bad example: ambiguous test names or long, hard-to-read methods.

## 4. Environment Setup

To practice TDD effectively, use a reliable development environment:\
\- \*\*Spring Boot\*\* for building REST APIs.\
\- \*\*JUnit 5\*\* as the testing framework.\
\- \*\*Maven\*\* or \*\*Gradle\*\* for build and dependency management.\
\- IDEs like IntelliJ IDEA, Eclipse, or VS Code.\
\- Use Git and GitHub for version control.\
Best practice: Keep the project structure clear, with separate folders for main code and tests.

## 5. JUnit 5 Architecture

JUnit 5 introduces a modular architecture:\
\- \*\*JUnit Platform:\*\* Launches test engines.\
\- \*\*Jupiter Engine:\*\* Runs new JUnit 5 tests.\
\- \*\*Vintage Engine:\*\* Allows backward compatibility with JUnit 3 and 4.\
This structure makes it easier to migrate legacy tests and adopt modern features like parameterized tests and extensions.

## 6. Writing Good Tests

Good tests follow the \*\*AAA\*\* pattern: Arrange, Act, Assert.\
\- Arrange: Set up the context.\
\- Act: Execute the action.\
\- Assert: Check the result.\
Use clear, descriptive test names that explain the expected behavior. Keep tests isolated and independent to run fast. Aim for one logical assertion per test for clarity.

## 7. Refactoring with Confidence

With a solid test suite, you can refactor code confidently. The tests act as a safety net to ensure the behavior remains correct. When refactoring:\
\- Keep tests green at all times.\
\- Refactor only implementation details, not the tests.\
\- Commit small changes frequently.

## 8. Exercise: TDD Kata FizzBuzz

Use simple katas to practice TDD. Examples:\
\- \*\*FizzBuzz:\*\* Write a function that returns 'Fizz' for multiples of 3, 'Buzz' for multiples of 5, and 'FizzBuzz' for multiples of both.\
Steps:\
1\) Write a failing test for the simplest case.\
2\) Write minimal code to pass the test.\
3\) Refactor and add more tests for edge cases.\
Repeat until the implementation is complete.

FizzBuzz is a classic kata for practicing TDD:\
\- For numbers divisible by 3, return 'Fizz'.\
\- For numbers divisible by 5, return 'Buzz'.\
\- For numbers divisible by both, return 'FizzBuzz'.\
Process:\
1\) Write a test for \`3\` ➜ expect 'Fizz'.\
2\) Write code to pass the test.\
3\) Add a test for \`5\` ➜ expect 'Buzz'.\
4\) Add a test for \`15\` ➜ expect 'FizzBuzz'.\
5\) Refactor: handle general cases and clean up conditions.



## 9. Key Takeaways

\- TDD helps you design first and code second.\
\- Clean code principles make tests and implementation easier to maintain.\
\- Practice is key: use katas regularly.\
\- Use version control to track progress and revert changes if needed.
