pipeline {
    agent any
    stages {
        stage('Git Clone') {
            steps {
                checkout scm
            }
        }
        stage('Run Tests') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew test'
            }
        }
        stage('Build Artifact') {
            steps {
                sh './gradlew bootJar'
            }
        }
        stage('Build Docker Image') {
            steps {
                openshift.withCluster {
                    openshift.startBuild(
                        'application-build',
                        '--from-file=build/libs/simple-ci-example-project-1.0.jar',
                        '--wait=true')
                }
            }
        }
    }
}