def templateName = 'application-build'
pipeline {
    agent any
    stages {
        stage('Git Clone') {
            steps {
                checkout scm
                sh 'chmod +x gradlew'
            }
        }
        stage('Run Tests') {
            steps {
                gradlew('test')
            }
        }
        stage('Build Artifact') {
            steps {
                gradlew('bootJar')
            }
        }
        stage('Build Docker Image') {
            steps {
                oc({
                    openshift.startBuild("${templateName}", '--from-dir=.', '--wait=true')
                })
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

def gradlew(task) {
    sh "./gradlew ${task}"
}

def oc(Closure closure) {
    script {
        openshift.withCluster {
            openshift.withProject() {
                closure.call()
            }
        }
    }
}