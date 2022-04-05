#!/usr/bin/env groovy
import com.Constants

def call() {
    sh "docker build src/ -t ${Constants.REPOSITORY_URI}:${Constants.IMAGE_TAG}"
    sh "docker push ${Constants.REPOSITORY_URI}:${Constants.IMAGE_TAG}"
}