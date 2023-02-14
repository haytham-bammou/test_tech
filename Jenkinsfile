pipeline {
    agent any
    tools {
        jdk 'jdk_17'
        maven 'maven-3.8.5'
    }
    stages {
        stage("build jar"){
            steps {
                script {
                    sh "mvn clean package"
                }
            }
        }
        stage("build and push to docker hub docker image"){
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh "docker build -t haythambammou/test_tech:chatgp ."
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                        sh "docker push haythambammou/test_tech:chatgp"
                    }
                }
            }
        }

        stage("deploy to server") {
                steps {
                    script {
                        withCredentials([string(credentialsId: 'API_KEY', variable: 'SECRET')]) { 
                            env.API_KEY = "${SECRET}"
                        }
                        def cmd = "bash ./server.sh ${API_KEY}"
                        sshagent(['server']){
                            sh "scp -o stricthostkeychecking=no docker-compose.yaml haytham@146.190.116.32:./"
                            sh "scp -o stricthostkeychecking=no server.sh haytham@146.190.116.32:./"
                            sh "ssh -o stricthostkeychecking=no haytham@146.190.116.32 ${cmd}"
                        }
                    }
                }
            }
    }
}