pipeline {
    agent any

    environment {
        MAVEN_VERSION = '3.9.9'
        MAVEN_DIR = '.jenkins-tools/maven'
    }

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare Maven') {
            steps {
                script {
                    if (isUnix()) {
                        sh '''#!/bin/sh
if command -v mvn >/dev/null 2>&1; then
  exit 0
fi
mkdir -p "$MAVEN_DIR"
if [ ! -d "$MAVEN_DIR/apache-maven-$MAVEN_VERSION" ]; then
  curl -fsSL -o maven.tar.gz "https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz"
  tar -xzf maven.tar.gz -C "$MAVEN_DIR"
  rm -f maven.tar.gz
fi
echo "$MAVEN_DIR/apache-maven-$MAVEN_VERSION/bin" > .maven-home
'''
                        env.PATH = "${pwd()}/${env.MAVEN_DIR}/apache-maven-${env.MAVEN_VERSION}/bin:${env.PATH}"
                    } else {
                        powershell '''
$ErrorActionPreference = 'Stop'
if (Get-Command mvn -ErrorAction SilentlyContinue) {
    exit 0
}
$mavenDir = Join-Path $env:WORKSPACE $env:MAVEN_DIR
$mavenHome = Join-Path $mavenDir "apache-maven-$env:MAVEN_VERSION"
if (-not (Test-Path $mavenHome)) {
    New-Item -ItemType Directory -Force -Path $mavenDir | Out-Null
    $archive = Join-Path $env:WORKSPACE "maven.zip"
    Invoke-WebRequest -Uri "https://archive.apache.org/dist/maven/maven-3/$env:MAVEN_VERSION/binaries/apache-maven-$env:MAVEN_VERSION-bin.zip" -OutFile $archive
    Expand-Archive -Path $archive -DestinationPath $mavenDir -Force
    Remove-Item $archive -Force
}
'''
                        env.PATH = "${pwd()}\\${env.MAVEN_DIR}\\apache-maven-${env.MAVEN_VERSION}\\bin;${env.PATH}"
                    }
                }
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
            junit allowEmptyResults: true, testResults: 'selenium-tests-java/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'selenium-tests-java/target/surefire-reports/**', allowEmptyArchive: true
        }
    }
}