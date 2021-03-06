def buildTag = 'UNKNOWN'
def artifactId = 'esports-service'
pipeline {
    environment {
         ROOM_NAME = '中控制-Motion平台'
         IMAGE_NAME = "registry.5252play.com/evatarco/motion-${artifactId}"
    }
    agent any
    options {
       disableConcurrentBuilds()
       buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    stages {

        stage('Notify: started') {
            steps {
                hipchatSend (color: 'YELLOW', notify: false, room: "${ROOM_NAME}",
                    message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
                )
            }
        }

        stage('Build') {
            steps {
                script {
                    def gitMyCount = sh(script:'git rev-list --count HEAD', returnStdout: true).trim()
                    def gitMyHash = sh(script:'git rev-parse --short HEAD', returnStdout: true).trim()
                    buildTag = env.BRANCH_NAME + "." + gitMyCount + "." + gitMyHash
                    sh "./gradlew clean build -x integrationTest"
                }
            }
        }

        stage('Package') {
            steps {
                script {
                  echo "Building image: ${IMAGE_NAME}:${buildTag} ..."
                  sh "docker build -f Dockerfile -t ${IMAGE_NAME}:latest -t ${IMAGE_NAME}:${buildTag} ."
                }
            }
        }

        stage('Integration Test') {
            steps {
                script {
                    try {
                        sh 'docker-compose -f docker-compose.ci.yaml up -d --build'
                        sh 'sleep 15s'
                        sh 'docker-compose -f docker-compose.ci.yaml exec -T app gradle --no-daemon clean integrationTest'
                    } finally {
                        sh 'docker-compose -f docker-compose.ci.yaml down'
                    }
                }
            }
        }

        stage('Publish') {
            steps {
                echo "Publishing artifacts..."
                script {
                  sh "docker push ${IMAGE_NAME}:${buildTag}"
                  sh "docker push ${IMAGE_NAME}:latest"
                }
            }
        }

        stage('Deploy_master') {
            when { branch 'master' }
            steps {
                script {
                    build job: 'Devops/motion-aliyun-test-deployer', parameters: [string(name: 'artifactId', value: artifactId), string(name: 'build', value: buildTag)], wait: false
                }
            }
        }
        
        stage('Deploy_develop') {
            when { branch 'develop' }
            steps {
                script {
                    build job: 'Devops/motion-dev-deployer', parameters: [string(name: 'artifactId', value: artifactId), string(name: 'build', value: buildTag)], wait: false
                }
            }
        }
    }

    post {
        success {
            hipchatSend (color: 'GREEN', notify: true, room: "${ROOM_NAME}",
                message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
            )
        }
        failure {
            hipchatSend (color: 'RED', notify: true, room: "${ROOM_NAME}",
                message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
            )
        }
    }
}
