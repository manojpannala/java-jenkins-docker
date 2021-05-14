#!/usr/bin/env groovy

def gv

pipeline {
    agent any
    tools {
        maven 'mvn-3.8'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage("deploy") {
//            environment {
//                AWS_ACCESS_KEY_ID = credentials('jenkins_aws_access_key_id')
//                AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
//            }
            steps {
                script {
                    echo 'deploying docker image'
                    gv.deployApp()
                    withKubeConfig([credentialsId: 'lke-credentials', serverUrl: 'https://aba36707-580d-4944-88e8-101811e302b8.eu-central-2.linodelke.net'])
                        sh 'kubectl create deployment nginx-deployment --image=nginx'
                }
            }
        }
    }
}
