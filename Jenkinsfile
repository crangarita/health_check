#!groovy
pipeline {
	agent none
      stages {
        stage('Maven Clean') {
            agent any
          steps {
            sh 'mvn clean'
          }
        }
        stage('Maven Package'){
            agent any
            steps {
                sh 'mvn package'
            }
        }
        stage('Load Properties'){
            agent any
            steps {
                script {
                    properties = readProperties file: 'application.properties'
                    env.SERVER_PORT = properties['server.port']
                }
            }
        }
        stage('Jar Run') {
            agent any
            steps {
                withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                    sh 'fuser -k $SERVER_PORT/tcp || true'
                    sh 'nohup java -jar target/config-0.0.1-SNAPSHOT.jar > log-greetings.log 2>&1 &'
                }
            }
        }
    }
}