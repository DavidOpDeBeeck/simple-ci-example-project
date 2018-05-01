node {
    stage("test") {
        checkout scm
        sh "ls -lh"
    }
    stage("Test input") {
        input message: "Test deployment. Approve?", id: "approval"
    }
}
