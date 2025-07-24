# Session 6: CI/CD and SonarQube Integration

## Objectives

- Automate tests using CI/CD pipelines with GitHub Actions or Jenkins.
- Integrate static code analysis using SonarQube.
- Generate test coverage reports.
- Maintain high code quality in automated workflows.

---

## 1. CI/CD Overview for Spring Boot Projects

CI/CD automates:
- Building the application
- Running tests
- Checking code quality
- Deploying to environments

Tools:
- **GitHub Actions**: built-in CI/CD for GitHub
- **Jenkins**: extensible open-source automation server

---

## 2. GitHub Actions Setup

### `.github/workflows/ci.yml`

```yaml
name: Java CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Build with Maven
        run: mvn clean install

      - name: Run Tests
        run: mvn test
```

---

## 3. Jenkins Pipeline Example

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
```

---

## 4. SonarQube Integration

SonarQube provides:
- Code quality metrics
- Coverage analysis
- Duplication detection
- Security and bug insights

### Maven plugin config (`pom.xml`):

```xml
<plugin>
  <groupId>org.sonarsource.scanner.maven</groupId>
  <artifactId>sonar-maven-plugin</artifactId>
  <version>3.9.1.2184</version>
</plugin>
```

### Run analysis:

```bash
mvn clean verify sonar:sonar   -Dsonar.projectKey=my-app   -Dsonar.host.url=http://localhost:9000   -Dsonar.login=<your_token>
```

---

## 5. Code Coverage with JaCoCo

Add plugin to `pom.xml`:

```xml
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
  <version>0.8.8</version>
  <executions>
    <execution>
      <goals>
        <goal>prepare-agent</goal>
      </goals>
    </execution>
    <execution>
      <id>report</id>
      <phase>verify</phase>
      <goals>
        <goal>report</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

Run:

```bash
mvn clean verify
```

Coverage report will be in: `target/site/jacoco/index.html`

---

## 6. Exercise: Setup CI/CD and Static Analysis

### Task

1. Create a GitHub Actions pipeline for your Spring Boot app.
2. Add Maven commands to run tests and generate coverage.
3. Configure JaCoCo and SonarQube integration.
4. Push to GitHub and view results in GitHub Actions.

---

## Summary

You now know how to:
- Build and test your app in CI/CD environments
- Use GitHub Actions or Jenkins to automate workflows
- Analyze code quality with SonarQube and JaCoCo

In Session 7, you’ll use Clean Code principles to refactor with tests as a safety net.
