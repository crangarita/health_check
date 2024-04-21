#!groovy
pipeline {
	agent none
      stages {
        stage('Maven Clean') {
            agent { label 'master' }
          steps {
            sh 'mvn clean'
          }
        }
        stage('Maven Package'){
            agent { label 'master' }
            steps {
                sh 'mvn package'
            }
        }
        stage('Load Properties'){
            agent { label 'master' }
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
                sshagent(['3f37cc3d-9234-4297-813c-173003248230']) { // Replace with your SSH key ID
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