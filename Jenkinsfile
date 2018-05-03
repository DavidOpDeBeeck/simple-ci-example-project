def appName = 'application-build'

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
        stage('Build Docker Image [OC]') {
            when {
                expression {
                    doInOpenshift {
                        !openshift.selector("bc", appName).exists()
                    }
                }
            }
            steps {
                script {
                    doInOpenshift {
                        openshift.newBuild("--name=${appName}", "--strategy=docker", "--binary=true")
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    doInOpenshift {
                        openshift.selector("bc", appName).startBuild('--from-dir=.', '--wait=true')
                    }
                }
            }
        }
    }
}

def gradlew(task) {
    sh "./gradlew ${task}"
}

def doInOpenshift(Closure closure) {
    openshift.withCluster {
        openshift.withProject {
            return closure.call()
        }
    }
}