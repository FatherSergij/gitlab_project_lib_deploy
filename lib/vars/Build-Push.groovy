#!/usr/bin/env groovy
import com.Constants

def call() {
    sh "docker build src/ -t ${REPOSITORY_URI}:${IMAGE_TAG}"
    sh "docker push ${REPOSITORY_URI}:${IMAGE_TAG}"
}