def call ( Map param ){
def hostname = param.hostname
pipeline {
    agent {
        node {
        label 'jenkins'
      }
    }
    stages {
        stage('Build') {
            steps {
                echo hostname
		sh 'mvn -B -DskipTests clean package'
		}
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deliver') {
            steps {
                sh "./jenkins/scripts/deliver.sh ${hostname}"
            }
        }
    }
}

}
