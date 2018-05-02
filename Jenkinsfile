def templateName = 'application-build'
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
                script {
                    openshift.withCluster {
                        openshift.withProject() {
                            openshift.startBuild(
                                'application-build',
                                '--from-file=build/libs/simple-ci-example-project-1.0.jar',
                                '--wait=true')
                        }
                    }
                }
            }
        }
        stage('Tag Docker Image') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            openshift.tag("${templateName}:latest", "${templateName}-staging:latest")
                        }
                    }
                }
            }
        }
    }
}