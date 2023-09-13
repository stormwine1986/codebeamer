// gerrit.groovy

pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scmGit(branches: [[name: 'FETCH_HEAD']], extensions: [], userRemoteConfigs: [[credentialsId: 'jenkins', refspec: '$GERRIT_REFSPEC', url: 'ssh://jenkins@docker-gerrit-1:29418/codebeamer']])
            }
        }
        stage('Build') {
            steps {
                echo 'do something for build'
                sleep 15
            }
        }
        stage('Test') {
            steps {
                sh 'pytest --junitxml=testresult.xml'
            }
        }
    }
}
