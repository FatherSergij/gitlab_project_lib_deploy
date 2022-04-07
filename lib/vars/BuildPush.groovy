#!/usr/bin/env groovy
import com.Constants

def call(String branch, String tag, String service, String build_num) {
    def REPOSITORY_URI="${Constants.AWS_ACCOUNT_ID}.dkr.ecr.${Constants.AWS_REGION}.amazonaws.com/\
        ${Constants.IMAGE_REPO_NAME}_${service}_${branch}"
    echo "${REPOSITORY_URI}"
    def REPO_NAME="${Constants.IMAGE_REPO_NAME}_${service}_${branch}"
    echo "${REPO_NAME}"
    MANIFEST=sh(script: "aws ecr batch-get-image --repository-name ${REPO_NAME} --image-ids imageTag=latest --region \
        ${Constants.AWS_REGION} --query 'images[].imageManifest' --output text", returnStdout: true).trim()
    echo "${MANIFEST}"
    echo "$MANIFEST"
    if ("${MANIFEST}" != "") {
        sh(script: "aws ecr put-image --repository-name ${REPO_NAME} --image-tag ${build_num} --image-manifest \
            ${MANIFEST} --region ${Constants.AWS_REGION}")
        sh(script: "aws ecr batch-delete-image --repository-name ${REPO_NAME} --image-ids imageTag=latest")
    }
    sh "docker build src/ -t ${REPOSITORY_URI}:${tag}"
    sh "docker push ${REPOSITORY_URI}:${tag}"
}