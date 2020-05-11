

node {

    try {
        
        stage ("Checkout") {
            checkout scm
        }

        if (env.BRANCH_NAME == "master") {

            stage ("Build") {
                sh "echo Building ${env.JOB_NAME}"
            }
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e 
    }
}
