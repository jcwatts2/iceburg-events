

node {

    try {
        
        stage ("Checkout") {
            checkout scm
        }

        if (env.BRANCH_NAME == "master") {

            stage ("Build") {
                sh "mvn"
            }
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e 
    }
}
