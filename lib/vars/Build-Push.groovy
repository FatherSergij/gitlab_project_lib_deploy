#!/usr/bin/env groovy
import com.Constants

def call(String branch, String tag, String service) {
    def REPOSITORY_URI="${Constants.AWS_ACCOUNT_ID}.dkr.ecr.${Constants.AWS_REGION}.amazonaws.com/ \
        ${Constants.IMAGE_REPO_NAME}_${service}_${branch}"
    sh "docker build src/ -t ${Constants.REPOSITORY_URI}:${tag}"
    sh "docker push ${Constants.REPOSITORY_URI}:${tag}"
}