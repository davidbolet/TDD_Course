# CI/CD Pipeline Tutorial for Bookstore Project with Jenkins and SonarQube

This guide provides detailed step-by-step instructions to configure a Jenkins-based CI/CD pipeline for the Bookstore Spring Boot project, with SonarQube integration.

---

## 🧱 Prerequisites

- Docker and Docker Compose installed
- Java 17 installed
- GitHub account and access to [TDD\_Course repository](https://github.com/davidbolet/TDD_Course)
- Basic familiarity with Jenkins and Spring Boot

---

## 🐳 Step 1: Setup Jenkins and SonarQube with Docker Compose

Create a `docker-compose.yml` file:

```yaml
version: '3'

services:
  jenkins:
    image: jenkins/jenkins:lts
    ports:
      - 8080:8080
      - 50000:50000
    volumes:
      - jenkins_home:/var/jenkins_home
    restart: always

  sonarqube:
    image: sonarqube:lts
    ports:
      - 9000:9000
    environment:
      - SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
    volumes:
      - sonar_data:/opt/sonarqube/data
      - sonar_extensions:/opt/sonarqube/extensions
      - sonar_logs:/opt/sonarqube/logs
    restart: always

  sonarqube-db:
    image: postgres:13
    environment:
      - POSTGRES_USER=sonar
      - POSTGRES_PASSWORD=sonar
      - POSTGRES_DB=sonarqube
    volumes:
      - sonar_db:/var/lib/postgresql/data

volumes:
  jenkins_home:
  sonar_data:
  sonar_extensions:
  sonar_logs:
  sonar_db:
```

Then run:

```bash
docker-compose up -d
```

---

## 🔐 Step 2: Access Jenkins and SonarQube

- Jenkins: `http://localhost:8080`
- SonarQube: `http://localhost:9000`

### Initial Credentials

- **Jenkins**: Get the initial password from:

  ```bash
  docker exec -it <jenkins-container-id> cat /var/jenkins_home/secrets/initialAdminPassword
  ```

- **SonarQube**: Default login: `admin` / `admin`

---

## 🔧 Step 3: Configure Jenkins

1. Install recommended plugins during first setup.
2. Go to `Manage Jenkins → Global Tool Configuration`:
   - Add **JDK 17**
   - Add **Maven 3.8.1**
3. Install additional plugins:
   - Git
   - Pipeline
   - Blue Ocean
   - SonarQube Scanner
4. Configure SonarQube under `Manage Jenkins → Configure System`:
   - Add SonarQube installation:
     - Name: `SonarQube`
     - Server URL: `http://sonarqube:9000`
     - Add credentials using the token created below.

---

## 🔑 Step 4: Create SonarQube Token

1. Log into SonarQube.
2. Go to `My Account → Security`.
3. Generate a token named `jenkins-token`.
4. Use it to configure Jenkins credentials:
   - `Manage Jenkins → Credentials → Global`
   - Add `Secret Text`:
     - ID: `sonarqube-token`
     - Secret: *the generated token*

---

## 📁 Step 5: Clone and Structure Project

From GitHub:\
[https://github.com/davidbolet/TDD\_Course/tree/main/Sessions/Sesion05\_BookService](https://github.com/davidbolet/TDD_Course/tree/main/Sessions/Sesion05_BookService)

Ensure the project includes:

- A working `mvnw` (Maven Wrapper)
- `pom.xml` with `jacoco-maven-plugin` and SonarQube configuration

---

## ⚙️ Step 6: Create Jenkins Pipeline

### Jenkinsfile in `Sessions/Sesion05_BookService/Jenkinsfile`:

```groovy
pipeline {
  agent any

  tools {
    maven 'Maven 3.8.1'
    jdk 'JDK 17'
  }

  environment {
    SONARQUBE = 'SonarQube'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build and Test') {
      steps {
        dir('Sessions/Sesion05_BookService') {
          sh 'chmod +x mvnw'
          sh './mvnw clean verify'
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv("${SONARQUBE}") {
          dir('Sessions/Sesion05_BookService') {
            sh './mvnw sonar:sonar -Dsonar.projectKey=bookservice -Dsonar.login=${SONAR_TOKEN}'
          }
        }
      }
    }

    stage('Results') {
      steps {
        junit 'Sessions/Sesion05_BookService/target/surefire-reports/*.xml'
      }
    }
  }
}
```

---

## 🥺 Step 7: Trigger and Monitor Pipeline

1. Create a new Pipeline job in Jenkins.
2. Point to your GitHub repository.
3. Jenkins will pull the `Jenkinsfile` and execute the pipeline.
4. Monitor results in:
   - Jenkins console
   - SonarQube dashboard: [http://localhost:9000](http://localhost:9000)

---

## ✅ Final Notes

- You can extend the pipeline with deployment steps.
- JaCoCo coverage is visible in `target/site/jacoco/index.html`.
- Integration tests can be added as a separate stage.

This setup helps students understand:

- Jenkins Pipeline basics
- SonarQube analysis
- Maven automation
- CI/CD best practices in Java

