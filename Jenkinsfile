

node {

    try {
        
        notify('warning', "STARTED")

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
        notify('danger', 'FAILED')
        throw e 
    }
}
