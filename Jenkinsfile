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
                script {
                    doInOpenshift {
                        openshift.startBuild("${templateName}", '--from-dir=.', '--wait=true')
                    }
                }
            }
        }
        stage('Tag Docker Image') {
            steps {
                script {
                    doInOpenshift {
                        openshift.tag("${templateName}:latest", "${templateName}-staging:latest")
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
        openshift.withProject() {
            closure.call()
        }
    }
}