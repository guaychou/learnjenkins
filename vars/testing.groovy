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
                sh './jenkins/scripts/deliver.sh'
                sh "scp target/${NAME}-${VERSION}.jar root@${hostname}:/opt/ "
                sh "ssh root@${hostname} "java -jar /opt/${NAME}-${VERSION}.jar" "
            }
        }
    }
}

}
