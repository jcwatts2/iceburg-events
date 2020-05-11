

node {

    try {
        
        stage ("Checkout") {
            checkout scm
        }

        if (env.BRANCH_NAME == "master") {

            stage ("Build") {
                sh "I'm building ${env.JOB_NAME}"
            }
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e 
    }
}
