pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                dir('selenium-tests-java') {
                    script {
                        if (isUnix()) {
                            sh 'mvn -B test -Dheadless=true'
                        } else {
                            bat 'mvn -B test -Dheadless=true'
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            junit 'selenium-tests-java/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'selenium-tests-java/target/surefire-reports/**', allowEmptyArchive: true
        }
    }
}