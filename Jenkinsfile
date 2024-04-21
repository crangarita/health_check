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
                	sh 'cat src/main/resources/application.properties'
                	
                    properties = readProperties file: 'src/main/resources/application.properties'
                    env.SERVER_PORT = properties['server.port']
                    env.NAME_APP = properties['spring.application.name']
                }
            }
        }
        stage('Transfer JAR') {
            agent { label 'remote-java' } // Replace with your remote server label
            steps {
                sshagent(['ac0b1b39-b6e7-49fb-b6a4-fbfaa327d14c']) { // Replace with your SSH key ID
                    sh "scp target/$NAME_APP-0.0.1-SNAPSHOT.jar jenkins@192.168.3.212:/home/jenkins"
                }
            }
        }
        stage('Jar Run') {
        	agent { label 'remote-java' }
            steps {
                withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                    sh 'fuser -k $SERVER_PORT/tcp || true'
                    sh 'nohup java -jar /home/jenkins/$NAME_APP-0.0.1-SNAPSHOT.jar > log-$NAME_APP.log 2>&1 &'
                }
            }

        }
    }
}