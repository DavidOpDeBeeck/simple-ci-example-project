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
                            openshift.newbuild(
                                "--strategy=docker",
                                "--binary=true",
                                "--to=${templateName}:1.2.3")
                            openshift.startBuild(
                                "${templateName}",
                                '--from-dir=.',
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