def appName = 'application-build'

pipeline {
    agent any
    stages {
        stage('Git Clone') {
            steps {
                gitClone()
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
        stage('Build Docker Image [OC-JOB]') {
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
                        openshift.newBuild("--name=${appName}", '--strategy=docker', '--binary=true')
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    doInOpenshift {
                        openshift.startBuild(appName, '--from-dir=.', '--wait=true')
                    }
                }
            }
        }
        stage('Deploy to TEST?') {
            steps {
                timeout(time:15, unit:'MINUTES') {
                    input message: "Deploy to TEST?", ok: "Deploy"
                }
            }
        }
        stage('Deploy to TEST?') {
            steps {
                script {
                    doInOpenshift {
                        openshift.tag("${appName}:latest", "${appName}-test:latest")
                    }
                }
            }
        }
    }
}

def gitClone() {
    checkout scm
    sh 'chmod +x gradlew'
}

def gradlew(task) {
    sh "./gradlew ${task}"
}

def doInOpenshift(Closure command) {
    openshift.withCluster {
        openshift.withProject {
            return command.call()
        }
    }
}