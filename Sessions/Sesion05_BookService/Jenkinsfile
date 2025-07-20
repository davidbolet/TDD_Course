pipeline {
    agent any
    environment {
        SONAR_SCANNER_OPTS = "-Xmx512m"
    }
    stages {
        stage('Build and Test') {
            steps {
                dir('Sessions/Sesion05_BookService') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean install'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                dir('Sessions/Sesion05_BookService') {
					withCredentials([string(credentialsId: 'sonar_token', variable: 'SONAR_TOKEN')]) {
						withSonarQubeEnv("SonarQube") {
							sh './mvnw -e sonar:sonar -Dsonar.projectKey=bookservice -Dsonar.login=${SONAR_TOKEN}'		
						}				
					}
				}
            }
        }
    }
    post {
        always {
            dir('Sessions/Sesion05_BookService') {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }
}