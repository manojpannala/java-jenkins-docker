#!/usr/bin/env groovy


def buildJar() {
    echo "building the application..."
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh 'docker build -t manojpannala/java-maven-app:2.1 .'
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh 'docker push manojpannala/java-maven-app:2.1'
    }
}

def deployApp() {
    echo 'deploying the application...'
    sh 'kubectl create deployment nginx-deployment --image=nginx'
}

return this
