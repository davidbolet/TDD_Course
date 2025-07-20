# Jenkins + SonarQube + Spring Boot CI Workshop

## 🧰 Prerequisites
- Docker & Docker Compose
- Git
- Maven project (Java 17)

## 🧪 How to Run Locally

### 1. Start Jenkins and SonarQube
```bash
docker-compose up
```

### 2. Jenkins Setup
- Go to: http://localhost:8080
- Retrieve the admin password:
```bash
docker exec -it <jenkins-container-id> cat /var/jenkins_home/secrets/initialAdminPassword
```
- Install suggested plugins and create admin user

### 3. Configure Tools
- Add JDK 17 and Maven in Jenkins (Manage Jenkins → Global Tool Configuration)
- Add GitHub credentials if needed
- Add SonarQube server in Jenkins (Manage Jenkins → Configure System → SonarQube)

### 4. Create a Pipeline Job
- Create new item → Pipeline
- Use the `Jenkinsfile` from this folder
- Point to your GitHub repo

## 🧪 What This Pipeline Does
- Checks out Spring Boot project
- Builds and tests it using Maven
- Sends analysis to SonarQube

### 🔍 Access SonarQube:
http://localhost:9000 (default credentials: admin / admin)

## ✅ Student Challenges
1. Modify the pipeline to deploy to a local server.
2. Add code coverage report using JaCoCo.
3. Add integration test stage.
4. Trigger the job on GitHub push.