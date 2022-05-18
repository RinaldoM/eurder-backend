pipeline {
    agent any

    stages {
        stage ('Build') {
            steps {
                bat 'mvn install -DskipTests'
            }
        }
        stage ('Unit Tests') {
            steps {
                bat 'mvn verify -DskipITs'
            }
        }
        stage ('Integration Tests') {
            steps {
                bat 'mvn verify -Dskip.surefire.tests'
            }
        }
    }
}